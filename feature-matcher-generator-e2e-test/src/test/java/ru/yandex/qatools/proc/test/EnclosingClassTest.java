package ru.yandex.qatools.proc.test;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;

/**
 * @author darkfelex (Dmitriy Shibaev)
 */
public class EnclosingClassTest {

    @Test
    public void shouldHaveMatcherForStaticNestedClass1() throws Exception {
        assertThat(
                Stream.of(EnclosingClassMatchers.class.getDeclaredMethods())
                        .map(Method::getName)
                        .collect(Collectors.toList()),
                hasItems("withStaticNestedObject1")
        );
    }

    @Test
    public void shouldHaveMatcherForStaticNestedClass2() throws Exception {
        assertThat(
                Stream.of(StaticNestedClass1Matchers.class.getDeclaredMethods())
                        .map(Method::getName)
                        .collect(Collectors.toList()),
                hasItems("withStaticNestedObject2")
        );
    }

    @Test
    public void shouldVariableMatcher() throws Exception {
        assertThat(
                Stream.of(StaticNestedClass2Matchers.class.getDeclaredMethods())
                        .map(Method::getName)
                        .collect(Collectors.toList()),
                hasItems("withVariable")
        );
    }

}