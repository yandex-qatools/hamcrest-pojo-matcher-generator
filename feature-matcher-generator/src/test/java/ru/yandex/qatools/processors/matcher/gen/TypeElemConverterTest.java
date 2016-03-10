package ru.yandex.qatools.processors.matcher.gen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.yandex.qatools.processors.matcher.gen.util.helpers.GeneratorHelper;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

/**
 * User: lanwen
 * Date: 23.10.14
 * Time: 17:14
 */
@RunWith(MockitoJUnitRunner.class)
public class TypeElemConverterTest {

    @Mock
    private Elements elements;

    @Mock
    private Types types;

    @Test
    public void shouldHandleEmptyAnnotationsList() throws Exception {
        Set<TypeElement> filter = new GeneratorHelper(elements, types)
                .filter(new HashSet<>());

        assertThat(filter, hasSize(0));
    }

}
