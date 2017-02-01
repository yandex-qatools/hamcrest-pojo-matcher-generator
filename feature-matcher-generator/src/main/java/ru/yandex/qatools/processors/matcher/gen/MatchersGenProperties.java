package ru.yandex.qatools.processors.matcher.gen;

import ru.qatools.properties.DefaultValue;
import ru.qatools.properties.Property;
import ru.qatools.properties.PropertyLoader;
import ru.qatools.properties.Resource;

import java.util.List;

/**
 * Configuration properties for annotation processing with {@link MatcherFactoryGenerator} processor
 *
 * @author Merkushev Kirill (github:lanwen)
 */
@Resource.Classpath("matchers.gen.properties")
public interface MatchersGenProperties {

    static MatchersGenProperties props() {
        return PropertyLoader.newInstance().populate(MatchersGenProperties.class);
    }

    /**
     * FQC names of annotation classes to work with (comma-separated list)
     */
    @Property("matcher.gen.annotations")
    @DefaultValue("")
    List<String> annotationsToProcess();

}
