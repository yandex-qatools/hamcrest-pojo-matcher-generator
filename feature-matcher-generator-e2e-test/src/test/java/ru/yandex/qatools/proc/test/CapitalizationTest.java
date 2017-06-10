package ru.yandex.qatools.proc.test;

import org.hamcrest.Matcher;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Vladislav Bauer
 */

public class CapitalizationTest {

    @Test
    public void shouldFindMatcher() throws Exception {
        assertThat(CapitalizationMatchers.class.getDeclaredMethod("withId", Matcher.class), notNullValue());
    }

}
