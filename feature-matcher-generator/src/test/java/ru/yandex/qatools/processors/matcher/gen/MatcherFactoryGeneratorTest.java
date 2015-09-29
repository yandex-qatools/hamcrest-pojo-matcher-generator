package ru.yandex.qatools.processors.matcher.gen;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * User: lanwen
 * Date: 19.10.14
 * Time: 19:31
 */
@RunWith(MockitoJUnitRunner.class)
public class MatcherFactoryGeneratorTest {

    @Mock
    private RoundEnvironment env;

    @Mock
    private ProcessingEnvironment prenv;

    @Mock
    private Elements elements;

    private Set<TypeElement> annotations = new HashSet<>();


    private MatcherFactoryGenerator matcherFactoryGenerator;


    @Rule
    public ExternalResource prepare = new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            when(env.processingOver()).thenReturn(false);
            when(env.getElementsAnnotatedWith(any(Class.class))).thenReturn((Set) new HashSet<Element>());
            when(prenv.getElementUtils()).thenReturn(elements);


            matcherFactoryGenerator = new MatcherFactoryGenerator();
            matcherFactoryGenerator.init(prenv);
        }
    };


    @Test
    public void shouldDoNothingWithNoneAnnotationsOrEmptyAnnotationClass() throws Exception {
        matcherFactoryGenerator.process(annotations, env);
        verify(env).processingOver();
        verifyNoMoreInteractions(env);
    }
}
