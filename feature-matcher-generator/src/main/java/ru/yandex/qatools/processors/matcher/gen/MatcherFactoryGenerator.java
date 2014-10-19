package ru.yandex.qatools.processors.matcher.gen;

import org.apache.velocity.app.VelocityEngine;
import ru.yandex.qatools.processors.matcher.gen.processing.FillMapWithFieldsProcess;
import ru.yandex.qatools.processors.matcher.gen.util.GenUtils;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import static ch.lambdaj.collection.LambdaCollections.with;
import static java.lang.String.format;
import static org.apache.velocity.util.ClassUtils.getResourceAsStream;
import static ru.yandex.qatools.processors.matcher.gen.processing.ClassDescriptionProcessing.processClassDescriptionsWith;

/**
 * User: lanwen
 * Date: 17.09.14
 * Time: 20:32
 */
@SupportedAnnotationTypes({
        MatcherFactoryGenerator.EXPOSE_ANNOTATION
})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class MatcherFactoryGenerator extends AbstractProcessor {


    public static final String EXPOSE_ANNOTATION = "com.google.gson.annotations.Expose";
    public static final Logger LOGGER = Logger.getLogger(MatcherFactoryGenerator.class.toString());

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Class expose = GenUtils.forName(EXPOSE_ANNOTATION);

        if (expose == null) {
            return true;
        }

        if (!roundEnv.processingOver()) {
            LOGGER.info("Start processing...");

            VelocityEngine engine = engine();

            FillMapWithFieldsProcess fillMapWithFields = FillMapWithFieldsProcess.fillMapOfClassDescriptions();
            LOGGER.info("Collect classes...");
            with(roundEnv.getElementsAnnotatedWith(expose)).convert(fillMapWithFields);

            LOGGER.info(format("Found %s classes to generate matchers. Writing them...",
                    fillMapWithFields.collectedClassesMap().size()));

            with(fillMapWithFields.collectedClassesMap())
                    .convertValues(processClassDescriptionsWith(processingEnv.getFiler(), engine));
            LOGGER.info("Writed all!");
        }

        return true;
    }


    public VelocityEngine engine() {
        Properties props = new Properties();
        try {
            props.load(getResourceAsStream(this.getClass(), "velocity.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Can't load props for velocity", e);
        }

        VelocityEngine ve = new VelocityEngine(props);
        ve.init();

        return ve;

    }

}
