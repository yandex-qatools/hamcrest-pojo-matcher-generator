package ru.yandex.qatools.processors.matcher.gen.processing;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import ru.yandex.qatools.processors.matcher.gen.bean.ClassDescription;
import ru.yandex.qatools.processors.matcher.gen.processing.utils.TestObjFactory;
import ru.yandex.qatools.processors.matcher.gen.util.helpers.GeneratorHelper;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.yandex.qatools.processors.matcher.gen.processing.matchers.ElementMatchers.withFields;

/**
 * User: lanwen
 * Date: 19.10.14
 * Time: 20:06
 */
@RunWith(MockitoJUnitRunner.class)
public class FillMapWithFieldsProcessTest {

    public static final String QUALIFIED_PACKAGE_NAME = "pack";
    public static final String SIMPLE_ENCLOSING_CLASS_NAME = "Bean";

    public static final String SIMPLE_ENCLOSING_CLASS_NAME_2 = "Bean2";

    public static final String FIELD_NAME_1 = "field1";
    public static final String FIELD_NAME_2 = "field2";

    private FillMapWithFieldsProcess process;

    @Mock
    private VariableElement field1;

    @Mock
    private VariableElement field2;

    @Mock
    private TypeMirror fieldType;

    @Mock
    private TypeElement type;

    @Mock
    private PackageElement pack;

    @Rule
    public ExternalResource prepare = new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            GeneratorHelper helper = mock(GeneratorHelper.class);

            // Java's Annotation Processing API is not available in unit tests, so it is necessary to mock method
            // "getWrappedType" for returning the first argument/element as result - boxing operation will not work.
            when(helper.getWrappedType(Mockito.<Element>any()))
                    .thenAnswer(invocation -> {
                        Object[] args = invocation.getArguments();
                        return args[0];
                    });

            process = FillMapWithFieldsProcess.fillMapOfClassDescriptionsProcess(helper);

            when(type.getEnclosingElement()).thenReturn(pack);

            bindNameAndType(field1, type, fieldType, FIELD_NAME_1);
            bindNameAndType(field2, type, fieldType, FIELD_NAME_2);
        }

        private void bindNameAndType(VariableElement field, TypeElement type, TypeMirror fieldType, String name) {
            when(field.getKind()).thenReturn(ElementKind.FIELD);
            when(field.getEnclosingElement()).thenReturn(type);
            when(field.asType()).thenReturn(fieldType);
            when(field.getSimpleName()).thenReturn(TestObjFactory.name(name));
        }
    };

    @Test
    public void shouldAskPackAndClassNames() throws Exception {
        process.accept(field1);

        verify(pack).getQualifiedName();
        verify(type).getSimpleName();
    }

    @Test
    public void shouldCollectFieldsOfOneClassInOneClass() throws Exception {
        when(pack.getQualifiedName()).thenReturn(TestObjFactory.name(QUALIFIED_PACKAGE_NAME));
        when(type.getSimpleName()).thenReturn(TestObjFactory.name(SIMPLE_ENCLOSING_CLASS_NAME));

        process.accept(field1);
        process.accept(field2);

        Collection<ClassDescription> classes = process.collectedClasses();

        assertThat("Should have one class in map", classes, hasSize(1));
        assertThat("Should collect two fields", classes, everyItem(withFields(hasSize(2))));
    }

    @Test
    public void shouldCollectFieldsOfTwoClassesSeparated() throws Exception {
        when(pack.getQualifiedName()).thenReturn(TestObjFactory.name(QUALIFIED_PACKAGE_NAME));
        when(type.getSimpleName())
                .thenReturn(TestObjFactory.name(SIMPLE_ENCLOSING_CLASS_NAME))
                .thenReturn(TestObjFactory.name(SIMPLE_ENCLOSING_CLASS_NAME_2));

        process.accept(field1);
        process.accept(field2);

        Collection<ClassDescription> classes = process.collectedClasses();

        assertThat("Should have two classes in map", classes, hasSize(2));
        assertThat("Should collect one field", classes, everyItem(withFields(hasSize(1))));
    }

}
