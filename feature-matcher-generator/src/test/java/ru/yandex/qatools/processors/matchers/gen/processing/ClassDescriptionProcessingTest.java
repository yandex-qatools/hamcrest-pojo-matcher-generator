package ru.yandex.qatools.processors.matchers.gen.processing;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.yandex.qatools.processors.matcher.gen.bean.ClassDescription;
import ru.yandex.qatools.processors.matcher.gen.processing.ClassDescriptionProcessing;

import javax.annotation.processing.Filer;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;

import static ch.lambdaj.collection.LambdaCollections.with;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static ru.yandex.qatools.processors.matcher.gen.processing.ClassDescriptionProcessing.processClassDescriptionsWith;

/**
 * User: lanwen
 * Date: 19.10.14
 * Time: 19:31
 */
@RunWith(MockitoJUnitRunner.class)
public class ClassDescriptionProcessingTest {


    public static final String PACKAGE_NAME = "ru.yandex";
    public static final String CLASS_NAME = "Bean";


    @Mock
    private Filer filer;

    @Mock
    private JavaFileObject javaFile;

    @Mock
    private VelocityEngine velocity;

    @Mock
    private Template template;


    private ClassDescriptionProcessing process;

    @Rule
    public ExternalResource prepare = new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            when(velocity.getTemplate(anyString())).thenReturn(template);
            when(filer.createSourceFile(anyString())).thenReturn(javaFile);

            process = processClassDescriptionsWith(filer, velocity);
        }
    };


    @Test
    public void shouldCreateSourceFileContainsPackageAndClassName() throws Exception {
        process.convert(new ClassDescription(PACKAGE_NAME, CLASS_NAME));

        verify(filer).createSourceFile(contains(with(PACKAGE_NAME, CLASS_NAME).join(".")));
    }

    @Test
    public void shouldInvokeClassTemplateOnceToWriteClassDescription() throws Exception {
        process.convert(new ClassDescription(PACKAGE_NAME, CLASS_NAME));

        verify(velocity, only()).getTemplate(ClassDescriptionProcessing.CLASS_TEMPLATE);
        verify(template, only()).merge(any(Context.class), any(Writer.class));
    }

    @Test
    public void shouldCloseWriter() throws Exception {
        Writer writer = mock(Writer.class);
        when(javaFile.openWriter()).thenReturn(writer);

        process.convert(new ClassDescription(PACKAGE_NAME, CLASS_NAME));

        verify(template).merge(any(Context.class), eq(writer));
        verify(writer).close();
    }

    @Test
    public void shouldSkipExceptionsOnClassDescriptionProcessing() throws Exception {
        when(javaFile.openWriter()).thenThrow(IOException.class);

        process.convert(new ClassDescription(PACKAGE_NAME, CLASS_NAME));
        verifyNoMoreInteractions(velocity, template);
    }
}
