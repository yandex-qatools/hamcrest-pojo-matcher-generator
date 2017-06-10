package ru.yandex.qatools.processors.matcher.gen.processing;

import com.google.testing.compile.CompilationRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.yandex.qatools.processors.matcher.gen.bean.ClassSpecDescription;
import ru.yandex.qatools.processors.matcher.gen.testclasses.WithSomeFields;
import ru.yandex.qatools.processors.matcher.gen.testclasses.WithoutGetter;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static ru.yandex.qatools.processors.matcher.gen.processing.MethodsCollector.collectingMethods;

/**
 * @author lanwen (Merkushev Kirill)
 */
public class MethodsCollectorTest {

    @Rule
    public CompilationRule compilation = new CompilationRule();
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldCollectFields() throws Exception {
        LinkedList<Element> fields = findFields(WithSomeFields.class);

        ClassSpecDescription apply = collectingMethods().finisher().apply(fields);

        assertThat(apply.getClassElement().getSimpleName().toString(), equalTo(WithSomeFields.class.getSimpleName()));
        assertThat(apply.getSpec().methodSpecs, hasSize(fields.size() + 1));
        assertThat(
                apply.getSpec().methodSpecs.stream().map(methodSpec -> methodSpec.name).collect(toList()),
                hasItems("withValue", "withBytes", "withStr", "withInteger", "withLower")
        );
    }

    @Test
    public void shouldThrowException_ifNoGetterPresent() throws Exception {
        thrown.expect(NoSuchElementException.class);
        thrown.expectMessage(allOf(containsString("WithoutGetter"), containsString("prop")));

        collectingMethods().finisher().apply(findFields(WithoutGetter.class));
    }
    
    private LinkedList<Element> findFields(final Class<?> withSomeFieldsClass) {
        TypeElement withFields = compilation.getElements().getTypeElement(withSomeFieldsClass.getCanonicalName());
        return new LinkedList<>(compilation.getElements().getAllMembers(withFields).stream()
                .filter(ofKind(ElementKind.FIELD))
                .map(elem -> (Element) elem)
                .collect(toList()));
    }

    public static Predicate<Element> ofKind(ElementKind kind) {
        return elem -> elem.getKind() == kind;
    }
}
