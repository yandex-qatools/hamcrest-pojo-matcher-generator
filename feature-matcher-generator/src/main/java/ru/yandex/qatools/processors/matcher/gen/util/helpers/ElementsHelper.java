package ru.yandex.qatools.processors.matcher.gen.util.helpers;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.List;
import java.util.Set;

import static ch.lambdaj.collection.LambdaCollections.with;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.collection.IsIn.isIn;
import static ru.yandex.qatools.processors.matcher.gen.util.MatchersGenProperties.props;
import static ru.yandex.qatools.processors.matcher.gen.util.converters.StringToTypeElementConverter.toTypeElements;

/**
 * User: lanwen
 * Date: 23.10.14
 * Time: 1:08
 */
public class ElementsHelper {

    private Elements elUtils;

    public ElementsHelper(Elements elUtils) {
        this.elUtils = elUtils;
    }

    /**
     * Retain any processing annotation that we provide in props to generate matchers
     * @param annotations - processing with annotation processor annotations (any)
     * @return filtered set of annotations to generate matchers for fields with such annotations
     */
    public Set<TypeElement> filter(Set<? extends TypeElement> annotations) {
        List<TypeElement> toProcess = with(props().annotationsToProcess())
                .convert(toTypeElements(elUtils)).remove(nullValue());
        return with(annotations).remove(not(isIn(toProcess)));
    }
}
