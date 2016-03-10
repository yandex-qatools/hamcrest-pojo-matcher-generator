package ru.yandex.qatools.processors.matcher.gen.processing;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import ru.yandex.qatools.processors.matcher.gen.bean.ClassDescription;
import ru.yandex.qatools.processors.matcher.gen.util.GenUtils;

import javax.annotation.processing.Filer;
import javax.tools.JavaFileObject;
import java.io.Writer;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

/**
 * User: lanwen
 * Date: 18.09.14
 * Time: 18:34
 */
public class ClassDescriptionProcessing implements Consumer<ClassDescription> {

    public static final String CLASS_TEMPLATE = "templates/class.vm";

    public static final Logger LOGGER = Logger.getLogger(ClassDescriptionProcessing.class.toString());


    private Filer filer;
    private VelocityEngine engine;

    private ClassDescriptionProcessing(Filer filer, VelocityEngine engine) {
        this.filer = filer;
        this.engine = engine;
    }

    public static ClassDescriptionProcessing processClassDescriptionsWith(Filer filer, VelocityEngine engine) {
        return new ClassDescriptionProcessing(filer, engine);
    }

    /**
     * Merges each class bean (with fields) and template and write it as source file
     * uses {@link ru.yandex.qatools.processors.matcher.gen.processing.ClassDescriptionProcessing} to write sources
     * and {@link org.apache.velocity.app.VelocityEngine} to create template
     *
     * @param from - bean to write as matcher class
     *
     * @return null
     * @see ru.yandex.qatools.processors.matcher.gen.bean.ClassDescription
     */
    @Override
    public void accept(ClassDescription from) {
        VelocityContext context = new VelocityContext();

        context.put("type", from);
        context.put("utils", GenUtils.class);

        try {
            JavaFileObject jfo = filer
                    .createSourceFile(GenUtils.withGeneratedSuffix(
                            Stream.of(from.packageName(), from.name()).collect(joining("."))));

            try (Writer writer = jfo.openWriter()) {
                engine.getTemplate(CLASS_TEMPLATE).merge(context, writer);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, format("Error: %s", e.getMessage()), e);
        }
    }
}
