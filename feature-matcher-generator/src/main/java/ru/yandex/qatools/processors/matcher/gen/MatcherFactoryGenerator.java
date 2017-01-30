package ru.yandex.qatools.processors.matcher.gen;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import org.apache.velocity.app.VelocityEngine;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import ru.yandex.qatools.processors.matcher.gen.annotations.DoNotGenerateMatcher;
import ru.yandex.qatools.processors.matcher.gen.bean.ClassSpecDescription;
import ru.yandex.qatools.processors.matcher.gen.bean.MethodSpecDescr;
import ru.yandex.qatools.processors.matcher.gen.processing.FillMapWithFieldsProcess;
import ru.yandex.qatools.processors.matcher.gen.util.GenUtils;
import ru.yandex.qatools.processors.matcher.gen.util.helpers.GeneratorHelper;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.apache.velocity.util.ClassUtils.getResourceAsStream;
import static ru.qatools.properties.utils.PropertiesUtils.readProperties;
import static ru.yandex.qatools.processors.matcher.gen.processing.ClassDescriptionProcessing.processClassDescriptionsWith;
import static ru.yandex.qatools.processors.matcher.gen.processing.FillMapWithFieldsProcess.fillMapOfClassDescriptionsProcess;

/**
 * User: lanwen
 * Date: 17.09.14
 * Time: 20:32
 */
@SupportedAnnotationTypes({
        MatcherFactoryGenerator.ANY
})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class MatcherFactoryGenerator extends AbstractProcessor {


    public static final String ANY = "*";
    public static final Logger LOGGER = Logger.getLogger(MatcherFactoryGenerator.class.toString());

    private GeneratorHelper helper;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
        this.helper = new GeneratorHelper(
                processingEnv.getElementUtils(),
                processingEnv.getTypeUtils()
        );
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {

            if (roundEnv.processingOver()) {
                return false;
            }

            Set<TypeElement> toProcess = helper.filter(annotations);
            if (toProcess.isEmpty()) {
                LOGGER.info("No any annotation found...");
                return false;
            }

            VelocityEngine engine = engine();

            FillMapWithFieldsProcess fillMapWithFields = fillMapOfClassDescriptionsProcess(helper);

            List<Element> fields = new LinkedList<>();


            for (TypeElement annotation : toProcess) {
                LOGGER.info(format("Work with %s...", annotation.getQualifiedName()));
                List<Element> annotated = roundEnv.getElementsAnnotatedWith(annotation)
                        .stream()
                        .filter(element -> isNull(element.getAnnotation(DoNotGenerateMatcher.class)))
                        .flatMap(MatcherFactoryGenerator::asFields)
                        .filter(element -> isNull(element.getAnnotation(DoNotGenerateMatcher.class)))
                        .collect(toList());

                fields.addAll(annotated);
            }


            Map<Element, ClassSpecDescription> classSpecs = fields.stream().map(field -> {
                        TypeName fType = TypeName.get(helper.getWrappedType(field).asType());
                        TypeName ownerType = TypeName.get(field.getEnclosingElement().asType());
                        ParameterizedTypeName returnType = ParameterizedTypeName.get(ClassName.get(Matcher.class), ownerType);

                        ParameterSpec parameter = ParameterSpec.builder(ParameterizedTypeName.get(ClassName.get(Matcher.class), fType), "matcher").build();
                        return new MethodSpecDescr(
                                field,
                                MethodSpec.methodBuilder("with" + GenUtils.normalize(field.getSimpleName().toString()))
                                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                        .addParameter(parameter)
                                        .returns(returnType)
                                        .addStatement("return $L",
                                                TypeSpec.anonymousClassBuilder("$N, $S, $S", parameter, field.getSimpleName(), field.getSimpleName())
                                                        .addSuperinterface(ParameterizedTypeName.get(
                                                                ClassName.get(FeatureMatcher.class),
                                                                ownerType,
                                                                fType
                                                        ))
                                                        .addMethod(
                                                                MethodSpec.methodBuilder("featureValueOf")
                                                                        .addAnnotation(Override.class)
                                                                        .addModifiers(Modifier.PUBLIC)
                                                                        .addParameter(ownerType, "actual")
                                                                        .returns(fType)
                                                                        .addStatement("return $L.get$L()", "actual", GenUtils.normalize(field.getSimpleName().toString()))
                                                                        .build()
                                                        ).build()
                                        )
                                        .build());
                    }
            )
                    .collect(groupingBy(spec -> spec.getField().getEnclosingElement())).entrySet()
                    .stream()
                    .collect(toMap(Map.Entry::getKey, entry -> {
                        TypeSpec.Builder builder = TypeSpec.classBuilder(entry.getKey().getSimpleName() + "Matchers")
                                .addModifiers(Modifier.PUBLIC);
                        entry.getValue().stream().map(MethodSpecDescr::getSpec).forEach(builder::addMethod);

                        return new ClassSpecDescription(builder.build());
                    }));

            //TODO если нестед внутри пустого класса без полей
            Comparator<Map.Entry<Element, ClassSpecDescription>> comparing = Comparator.comparing(entry -> depth(entry.getKey()));
            classSpecs.entrySet()
                    .stream()
                    .filter(entry -> !(entry.getKey().getEnclosingElement() instanceof PackageElement))
                    .sorted(comparing.reversed())
                    .forEach(entry -> {


                        ClassSpecDescription orDefault = classSpecs.computeIfAbsent(
                                entry.getKey().getEnclosingElement(),
                                key -> new ClassSpecDescription(TypeSpec.classBuilder(key.getSimpleName() + "Matchers")
                                        .addModifiers(Modifier.PUBLIC)
                                        .build())
                        );


                        orDefault.setSpec(orDefault.getSpec().toBuilder().addType(entry.getValue().getSpec().toBuilder().addModifiers(Modifier.STATIC).build()).build());

                    });

            classSpecs.entrySet()
                    .stream()
                    .filter(entry -> (entry.getKey().getEnclosingElement() instanceof PackageElement))
                    .map(entry -> JavaFile.builder(((PackageElement) entry.getKey().getEnclosingElement()).getQualifiedName().toString(), entry.getValue().getSpec()))
                    .forEach(file -> {
                        try {
                            file.build().writeTo(processingEnv.getFiler());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });


            LOGGER.info(format("Got %s classes to generate matchers. Writing them...",
                    fillMapWithFields.collectedClasses().size()));

            fillMapWithFields.collectedClasses()
                    .forEach(processClassDescriptionsWith(processingEnv.getFiler(), engine));
            LOGGER.info("All classes were successfully processed!");

        } catch (Throwable t) {
            LOGGER.log(Level.SEVERE, format("Can't generate matchers, because of: %s", t.getMessage()), t);
        }

        return false;
    }

    private static int depth(Element element) {
        int depth = 0;
        Element enclosing = element.getEnclosingElement();
        while (!(enclosing instanceof PackageElement)) {
            depth++;
            enclosing = enclosing.getEnclosingElement();
        }
        return depth;
    }

    private static Stream<Element> asFields(Element element) {
        switch (element.getKind()) {
            case FIELD:
                return Stream.of(element);
            case CLASS:
                return element.getEnclosedElements().stream().flatMap(MatcherFactoryGenerator::asFields);
        }
        return Stream.empty();
    }


    private VelocityEngine engine() {
        Properties props = readProperties(getResourceAsStream(this.getClass(), "velocity.matchers.gen.properties"));
        VelocityEngine ve = new VelocityEngine(props);
        ve.init();

        return ve;
    }

}
