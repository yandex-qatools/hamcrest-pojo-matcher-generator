package ru.yandex.qatools.processors.matcher.gen.processing;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import ru.yandex.qatools.processors.matcher.gen.MatcherFactoryGenerator;
import ru.yandex.qatools.processors.matcher.gen.bean.ClassSpecDescription;

import javax.annotation.Generated;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static ru.yandex.qatools.processors.matcher.gen.processing.Naming.withGeneratedSuffix;
import static ru.yandex.qatools.processors.matcher.gen.processing.ProcessingPredicates.hasParentPackageElement;

/**
 * @author lanwen (Merkushev Kirill)
 */
public class MethodsCollector implements Collector<Element, LinkedList<Element>, ClassSpecDescription> {
    private MethodsCollector() {
        // for readability use factory
    }

    public static MethodsCollector collectingMethods() {
        return new MethodsCollector();
    }

    @Override
    public Supplier<LinkedList<Element>> supplier() {
        return LinkedList::new;
    }

    @Override
    public BiConsumer<LinkedList<Element>, Element> accumulator() {
        return LinkedList::add;
    }

    @Override
    public BinaryOperator<LinkedList<Element>> combiner() {
        return (l1, l2) -> {
            l1.addAll(l2);
            return l1;
        };
    }

    @Override
    public Function<LinkedList<Element>, ClassSpecDescription> finisher() {
        return (LinkedList<Element> collected) -> {
            Element classOfField = collected.getFirst().getEnclosingElement();

            TypeSpec.Builder builder = commonClassPart(classOfField);
            collected.stream().map(asMethodSpec()).forEach(builder::addMethod);

            return new ClassSpecDescription(classOfField, builder.build());
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(Collections.singleton(Characteristics.UNORDERED));
    }


    public static TypeSpec.Builder commonClassPart(Element classOfField) {
        TypeSpec.Builder builder = TypeSpec
                .classBuilder(withGeneratedSuffix(classOfField.getSimpleName()))
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(
                        MethodSpec.constructorBuilder()
                                .addModifiers(Modifier.PRIVATE)
                                .addJavadoc("You should not instantiate this class\n")
                                .addStatement(
                                        "throw new $T($S)",
                                        UnsupportedOperationException.class,
                                        "This class has only static methods"
                                )
                                .build()
                );

        if (hasParentPackageElement().test(classOfField)) {
            builder.addAnnotation(
                    AnnotationSpec.builder(Generated.class)
                            .addMember("value", "$S", MatcherFactoryGenerator.class.getCanonicalName())
                            .addMember("date", "$S", ZonedDateTime.now()).build()
            );
        } else {
            builder.addModifiers(Modifier.STATIC);
        }
        return builder;
    }


    public static Function<Element, MethodSpec> asMethodSpec() {
        return field -> {
            TypeName fieldType = TypeName.get(field.asType()).box();
            TypeName ownerType = TypeName.get(field.getEnclosingElement().asType());
            ParameterizedTypeName returnType = ParameterizedTypeName.get(ClassName.get(Matcher.class), ownerType);
            ParameterSpec matcher = ParameterSpec.builder(
                    ParameterizedTypeName.get(ClassName.get(Matcher.class), fieldType),
                    "matcher"
            ).build();

            return MethodSpec.methodBuilder("with" + Naming.normalize(field.getSimpleName()))
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .addParameter(matcher)
                    .returns(returnType)
                    .addJavadoc("Matcher for {@link $T#$L}\n", ownerType, field.getSimpleName())
                    .addStatement("return $L",
                            TypeSpec.anonymousClassBuilder(
                                    "$N, $S, $S",
                                    matcher,
                                    field.getSimpleName(),
                                    field.getSimpleName()
                            )
                                    .addSuperinterface(
                                            ParameterizedTypeName.get(
                                                    ClassName.get(FeatureMatcher.class),
                                                    ownerType,
                                                    fieldType
                                            )
                                    )
                                    .addMethod(
                                            MethodSpec.methodBuilder("featureValueOf")
                                                    .addAnnotation(Override.class)
                                                    .addModifiers(Modifier.PUBLIC)
                                                    .addParameter(ownerType, "actual")
                                                    .returns(fieldType)
                                                    .addStatement(
                                                            "return $L.$L()",
                                                            "actual",
                                                            getGetterName(field)
                                                    )
                                                    .build()
                                    ).build()
                    )
                    .build();
        };
    }

    private static String getGetterName(Element field) {
        String caseInsensitiveGetterName = "get" + field.getSimpleName();
        
        return field.getEnclosingElement().getEnclosedElements().stream()
                .filter(elem -> elem.getSimpleName().toString().equalsIgnoreCase(caseInsensitiveGetterName))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(
                        "cannot find getter for " + field.getSimpleName() + " on class " +
                                field.getEnclosingElement().getSimpleName())
                )
                .getSimpleName()
                .toString();
    }
}
