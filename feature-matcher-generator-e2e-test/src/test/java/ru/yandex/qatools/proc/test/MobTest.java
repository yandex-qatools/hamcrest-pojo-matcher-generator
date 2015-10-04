package ru.yandex.qatools.proc.test;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Vladislav Bauer
 */

@RunWith(Parameterized.class)
public class MobTest {

    @Parameterized.Parameters(name = "{0}")
    public static Collection<String> matchers() {
        return Arrays.asList(
            "withValByte",
            "withValueByte",
            "withValChar",
            "withValueCharacter",
            "withValInt",
            "withValueInteger",
            "withValLong",
            "withValueLong",
            "withValFloat",
            "withValueFloat",
            "withValDouble",
            "withValueDouble",
            "withValBoolean",
            "withValueBoolean"
        );
    }


    @Parameterized.Parameter
    public String matcherName;


    @Test
    public void shouldFindMatcher() throws Exception {
        assertThat(MobMatchers.class.getDeclaredMethod(matcherName, Matcher.class), notNullValue());
    }

}
