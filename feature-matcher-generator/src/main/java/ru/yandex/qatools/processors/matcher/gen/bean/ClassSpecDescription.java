package ru.yandex.qatools.processors.matcher.gen.bean;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import ru.yandex.qatools.processors.matcher.gen.elements.ElementParentsIterable;
import ru.yandex.qatools.processors.matcher.gen.processing.MethodsCollector;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import java.util.Comparator;
import java.util.Map;

import static ru.yandex.qatools.processors.matcher.gen.processing.ProcessingPredicates.hasParentPackageElement;

/**
 * Bean to store info about class. Stores fields methods list (returning matchers)
 * Contains some methods to simplify processing
 *
 * @author lanwen (Merkushev Kirill)
 */
public class ClassSpecDescription implements Comparable<ClassSpecDescription> {

    private Element classElement;
    private TypeSpec spec;

    public ClassSpecDescription(Element classElement, TypeSpec spec) {
        this.classElement = classElement;
        this.spec = spec;
    }

    public Element getClassElement() {
        return classElement;
    }

    public TypeSpec getSpec() {
        return spec;
    }

    public void setSpec(TypeSpec spec) {
        this.spec = spec;
    }

    public JavaFile asJavaFile() {
        return JavaFile.builder(
                ((PackageElement) getClassElement().getEnclosingElement()).getQualifiedName().toString(),
                getSpec()
        ).build();
    }

    public void mergeWithParentFrom(Map<Element, ClassSpecDescription> classes) {
        ClassSpecDescription container = classes.computeIfAbsent(
                getClassElement().getEnclosingElement(),
                enclosing -> new ClassSpecDescription(
                        getClassElement().getEnclosingElement(),
                        MethodsCollector.commonClassPart(enclosing).build()
                )
        );

        container.setSpec(
                container.getSpec()
                        .toBuilder()
                        .addType(getSpec())
                        .build()
        );
    }

    @Override
    public int compareTo(ClassSpecDescription o) {
        return Comparator.comparing(ClassSpecDescription::depthOf).compare(this, o);
    }

    private static long depthOf(ClassSpecDescription element) {
        return ElementParentsIterable.stream(element.getClassElement())
                .filter(hasParentPackageElement().negate())
                .count();
    }
}
