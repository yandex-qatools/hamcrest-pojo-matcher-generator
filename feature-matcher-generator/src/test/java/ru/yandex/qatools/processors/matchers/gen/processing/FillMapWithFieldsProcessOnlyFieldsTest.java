package ru.yandex.qatools.processors.matchers.gen.processing;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.qatools.processors.matcher.gen.processing.FillMapWithFieldsProcess;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import java.util.Collection;

import static ch.lambdaj.collection.LambdaCollections.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * User: lanwen
 * Date: 19.10.14
 * Time: 20:06
 */
@RunWith(Parameterized.class)
public class FillMapWithFieldsProcessOnlyFieldsTest {

    @Parameterized.Parameters(name = "{0}")
    public static Collection<ElementKind> data() {
        return with(ElementKind.values()).remove(equalTo(ElementKind.FIELD));
    }

    private static FillMapWithFieldsProcess process;

    @ClassRule
    public static ExternalResource prepare = new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            process = FillMapWithFieldsProcess.fillMapOfClassDescriptions();
        }
    };

    @Parameterized.Parameter
    public ElementKind kind;


    @Test
    public void shouldProcessOnlyFields() throws Exception {
        Element element = mock(Element.class);
        when(element.getKind()).thenReturn(kind);

        process.convert(element);

        verify(element, only()).getKind();
        verifyNoMoreInteractions(element);
    }
}
