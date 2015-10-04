package ru.yandex.qatools.proc.test;

import org.hamcrest.Matcher;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayWithSize.arrayWithSize;

/**
 * @author Vladislav Bauer
 */

public class LordTest {

    @Test
    public void shouldFindMatcherClassForOwner() throws Exception {
        assertThat(Class.forName(LordMatchers.class.getCanonicalName()), notNullValue());
    }

    @Test
    public void shouldFindMatchersForAllField() throws Exception {
        assertThat(LordMatchers.class.getDeclaredMethods(), arrayWithSize(2));
        assertThat(LordMatchers.class.getDeclaredMethod("withName", Matcher.class), notNullValue());
        assertThat(LordMatchers.class.getDeclaredMethod("withSlavesCount", Matcher.class), notNullValue());
    }

}
