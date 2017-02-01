package ru.yandex.qatools.processors.matcher.gen.processing;

import com.google.testing.compile.CompilationRule;
import org.junit.Rule;
import org.junit.Test;
import ru.yandex.qatools.processors.matcher.gen.bean.ClassSpecDescription;
import ru.yandex.qatools.processors.matcher.gen.testclasses.WithSomeFields;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.util.LinkedList;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static ru.yandex.qatools.processors.matcher.gen.processing.MethodsCollector.collectingMethods;

/**
 * @author lanwen (Merkushev Kirill)
 */
public class MethodsCollectorTest {

    @Rule
    public CompilationRule compilation = new CompilationRule();

    @Test
    public void shouldCollectFields() throws Exception {
        TypeElement withFields = compilation.getElements().getTypeElement(WithSomeFields.class.getCanonicalName());
        LinkedList<Element> fields = new LinkedList<>(compilation.getElements().getAllMembers(withFields).stream()
                .filter(ofKind(ElementKind.FIELD))
                .map(elem -> (Element) elem)
                .collect(toList()));

        ClassSpecDescription apply = collectingMethods().finisher().apply(fields);

        assertThat(apply.getClassElement().getSimpleName().toString(), equalTo(WithSomeFields.class.getSimpleName()));
        assertThat(apply.getSpec().methodSpecs, hasSize(fields.size() + 1));
        assertThat(
                apply.getSpec().methodSpecs.stream().map(methodSpec -> methodSpec.name).collect(toList()),
                hasItems("withValue", "withBytes", "withStr", "withInteger")
        );
    }



    public static Predicate<Element> ofKind(ElementKind kind) {
        return elem -> elem.getKind() == kind;
    }
}
