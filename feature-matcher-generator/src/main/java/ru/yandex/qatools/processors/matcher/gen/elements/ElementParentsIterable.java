package ru.yandex.qatools.processors.matcher.gen.elements;

import javax.lang.model.element.Element;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Objects.nonNull;

/**
 * Provides iteration over parent chain
 * It allows to get depth, find nested classes, find package easily
 *
 * This iterator starts with current element
 *
 * @author lanwen (Merkushev Kirill)
 */
public class ElementParentsIterable implements Iterable<Element> {
    private final Element start;

    public ElementParentsIterable(Element start) {
        this.start = Objects.requireNonNull(start, "element can't be null");
    }

    @Override
    public Iterator<Element> iterator() {
        return new Iterator<Element>() {
            private Element current = start;

            @Override
            public boolean hasNext() {
                return nonNull(current.getEnclosingElement());
            }

            @Override
            public Element next() {
                Element next = current;
                current = current.getEnclosingElement();
                return next;
            }
        };
    }

    /**
     * Creates stream with parent chain
     *
     * @param element element to start with (will be included to chain)
     * @return stream with elements from current with his parents until package (inclusive)
     */
    public static Stream<Element> stream(Element element) {
        return StreamSupport.stream(new ElementParentsIterable(element).spliterator(), false);
    }
}
