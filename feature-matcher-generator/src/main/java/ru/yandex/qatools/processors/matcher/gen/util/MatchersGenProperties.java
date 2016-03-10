package ru.yandex.qatools.processors.matcher.gen.util;

import ru.qatools.properties.Property;
import ru.qatools.properties.PropertyLoader;
import ru.qatools.properties.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * User: lanwen
 * Date: 22.10.14
 * Time: 21:53
 */
@Resource.Classpath("matchers.gen.properties")
public class MatchersGenProperties {
    private static MatchersGenProperties instance;

    private MatchersGenProperties() {
        PropertyLoader.newInstance().populate(this);
    }

    public static MatchersGenProperties props() {
        if (instance == null) {
            instance = new MatchersGenProperties();
        }
        return instance;
    }

    @Property("matcher.gen.annotations")
    private List<String> annotations = new ArrayList<>();


    public List<String> annotationsToProcess() {
        return annotations;
    }
}
