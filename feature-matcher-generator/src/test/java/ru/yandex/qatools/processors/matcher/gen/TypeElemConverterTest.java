package ru.yandex.qatools.processors.matcher.gen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.yandex.qatools.processors.matcher.gen.util.converters.StringToTypeElementConverter;
import ru.yandex.qatools.processors.matcher.gen.util.helpers.ElementsHelper;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

/**
 * User: lanwen
 * Date: 23.10.14
 * Time: 17:14
 */
@RunWith(MockitoJUnitRunner.class)
public class TypeElemConverterTest {

    public static final String CLASS_NAME = "ru.yandex.ClassBean";

    @Mock
    private Elements elements;

    @Test
    public void shouldGetTypeElemFromElementsUtils() throws Exception {
        StringToTypeElementConverter.toTypeElements(elements).convert(CLASS_NAME);
        verify(elements).getTypeElement(CLASS_NAME);
    }

    @Test
    public void shouldHandleEmptyAnnotationsList() throws Exception {
        Set<TypeElement> filter = new ElementsHelper(elements).filter(new HashSet<TypeElement>());
        assertThat(filter, hasSize(0));
    }
}
