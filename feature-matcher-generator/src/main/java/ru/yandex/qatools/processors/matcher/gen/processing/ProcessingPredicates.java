package ru.yandex.qatools.processors.matcher.gen.processing;

import ru.yandex.qatools.processors.matcher.gen.annotations.DoNotGenerateMatcher;
import ru.yandex.qatools.processors.matcher.gen.bean.ClassSpecDescription;
import ru.yandex.qatools.processors.matcher.gen.elements.ElementParentsIterable;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import java.util.function.Predicate;

import static java.util.Objects.nonNull;

/**
 * @author lanwen (Merkushev Kirill)
 */
public final class ProcessingPredicates {

    /**
     * Walks through parent chain and if any contains
     * {@link DoNotGenerateMatcher} annotation, skips this element
     *
     * @return predicate to help filter fields for matcher generation
     */
    public static Predicate<Element> shouldGenerateMatcher() {
        return element -> ElementParentsIterable.stream(element)
                .noneMatch(next -> nonNull(next.getAnnotation(DoNotGenerateMatcher.class)));
    }

    /**
     * Syntax sugar for {@link #hasParentPackageElement()}
     */
    public static Predicate<ClassSpecDescription> isEntryWithParentPackageElement() {
        return entry -> hasParentPackageElement().test(entry.getClassElement());
    }

    /**
     * To help filter nested classes
     */
    public static Predicate<Element> hasParentPackageElement() {
        return entry -> entry.getEnclosingElement() instanceof PackageElement;
    }
}
