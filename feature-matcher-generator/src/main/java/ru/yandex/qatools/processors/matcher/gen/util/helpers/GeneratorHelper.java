package ru.yandex.qatools.processors.matcher.gen.util.helpers;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.hamcrest.collection.IsIn.isIn;
import static ru.yandex.qatools.processors.matcher.gen.util.MatchersGenProperties.props;

/**
 * User: lanwen
 * Date: 23.10.14
 * Time: 1:08
 */
public class GeneratorHelper {

    private final Elements elUtils;
    private final Types tUtils;


    public GeneratorHelper(Elements elUtils, Types tUtils) {
        this.elUtils = elUtils;
        this.tUtils = tUtils;
    }

    /**
     * Retain any processing annotation that we provide in props to generate matchers
     *
     * @param annotations processing with annotation processor annotations (any)
     *
     * @return filtered set of annotations to generate matchers for fields with such annotations
     */
    public Set<TypeElement> filter(Set<? extends TypeElement> annotations) {
        List<TypeElement> toProcess = props().annotationsToProcess().stream()
                .map(elUtils::getTypeElement)
                .filter(Objects::nonNull)
                .collect(toList());
        return annotations.stream()
                .map(anno -> (TypeElement) anno)
                .filter(anno -> isIn(toProcess).matches(anno))
                .collect(toSet());
    }

    /**
     * "Box" element object to primitive-class wrapper or use the original element
     *
     * @param element element object
     *
     * @return wrapped element for primitive or original element otherwise
     */
    public Element getWrappedType(Element element) {
        TypeMirror mirror = element.asType();
        TypeKind kind = mirror.getKind();
        boolean primitive = kind.isPrimitive();

        if (primitive) {
            return tUtils.boxedClass((PrimitiveType) mirror);
        }
        return tUtils.asElement(mirror);
    }

}
