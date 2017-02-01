package ru.yandex.qatools.processors.matcher.gen.elements;

import com.google.testing.compile.CompilationRule;
import org.junit.Rule;
import org.junit.Test;
import ru.yandex.qatools.processors.matcher.gen.testclasses.Outer;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static ru.yandex.qatools.processors.matcher.gen.elements.ElementParentsIterable.stream;
import static ru.yandex.qatools.processors.matcher.gen.processing.MethodsCollectorTest.ofKind;

/**
 * @author lanwen (Merkushev Kirill)
 */
public class ElementParentsIterableTest {

    @Rule
    public CompilationRule compilation = new CompilationRule();

    @Test
    public void shouldGetParents() throws Exception {
        TypeElement twiceNested = compilation.getElements()
                .getTypeElement(Outer.Nested.TwiceNested.class.getCanonicalName());
        List<? extends Element> members = compilation.getElements().getAllMembers(twiceNested);

        Element field = members.stream().filter(ofKind(ElementKind.FIELD)).findFirst()
                .orElseThrow(IllegalStateException::new);

        List<String> parents = stream(field)
                .map(element -> element.getSimpleName().toString())
                .collect(Collectors.toList());

        assertThat(parents, hasItems("field", "TwiceNested", "Nested", "Outer"));
    }

    @Test
    public void shouldReturnNothingForPackage() throws Exception {
        PackageElement pkg = compilation.getElements()
                .getPackageElement(Outer.class.getPackage().getName());

        assertThat(stream(pkg).count(), is(0L));
    }
}
