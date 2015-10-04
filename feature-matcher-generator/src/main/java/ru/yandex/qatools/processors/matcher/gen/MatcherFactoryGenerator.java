package ru.yandex.qatools.processors.matcher.gen;

import org.apache.velocity.app.VelocityEngine;
import ru.yandex.qatools.processors.matcher.gen.processing.FillMapWithFieldsProcess;
import ru.yandex.qatools.processors.matcher.gen.util.helpers.GeneratorHelper;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ch.lambdaj.collection.LambdaCollections.with;
import static java.lang.String.format;
import static org.apache.velocity.util.ClassUtils.getResourceAsStream;
import static ru.yandex.qatools.processors.matcher.gen.processing.ClassDescriptionProcessing.processClassDescriptionsWith;
import static ru.yandex.qatools.processors.matcher.gen.processing.FillMapWithFieldsProcess.fillMapOfClassDescriptionsProcess;
import static ru.yandex.qatools.properties.utils.PropertiesUtils.readProperties;

/**
 * User: lanwen
 * Date: 17.09.14
 * Time: 20:32
 */
@SupportedAnnotationTypes({
        MatcherFactoryGenerator.ANY
})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
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

            FillMapWithFieldsProcess fillMapWithFields =
                fillMapOfClassDescriptionsProcess(helper);

            for (TypeElement annotation : toProcess) {
                LOGGER.info(format("Work with %s...", annotation.getQualifiedName()));
                with(roundEnv.getElementsAnnotatedWith(annotation)).convert(fillMapWithFields);
            }

            LOGGER.info(format("Got %s classes to generate matchers. Writing them...",
                    fillMapWithFields.collectedClasses().size()));

            with(fillMapWithFields.collectedClasses())
                    .convert(processClassDescriptionsWith(processingEnv.getFiler(), engine));
            LOGGER.info("All classes were successfully processed!");

        } catch (Throwable t) {
            LOGGER.log(Level.SEVERE, format("Can't generate matchers, because of: %s", t.getMessage()), t);
        }

        return false;
    }


    private VelocityEngine engine() {
        Properties props = readProperties(getResourceAsStream(this.getClass(), "velocity.matchers.gen.properties"));
        VelocityEngine ve = new VelocityEngine(props);
        ve.init();

        return ve;
    }

}
