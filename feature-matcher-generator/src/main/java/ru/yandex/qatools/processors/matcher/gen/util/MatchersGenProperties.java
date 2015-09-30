package ru.yandex.qatools.processors.matcher.gen.util;

import ru.yandex.qatools.processors.matcher.gen.util.converters.StringListConverter;
import ru.yandex.qatools.properties.PropertyLoader;
import ru.yandex.qatools.properties.annotations.Property;
import ru.yandex.qatools.properties.annotations.Resource;
import ru.yandex.qatools.properties.annotations.Use;
import ru.yandex.qatools.properties.annotations.With;

import java.util.ArrayList;
import java.util.List;

/**
 * User: lanwen
 * Date: 22.10.14
 * Time: 21:53
 */
@Resource.Classpath("matchers.gen.properties")
@With(PropsClasspathProvider.class)
public class MatchersGenProperties {
    private static MatchersGenProperties instance;

    private MatchersGenProperties() {
        PropertyLoader.populate(this);
    }

    public static MatchersGenProperties props() {
        if (instance == null) {
            instance = new MatchersGenProperties();
        }
        return instance;
    }

    @Property("matcher.gen.annotations")
    @Use(StringListConverter.class)
    private List<String> annotations = new ArrayList<>();


    public List<String> annotationsToProcess() {
        return annotations;
    }
}
