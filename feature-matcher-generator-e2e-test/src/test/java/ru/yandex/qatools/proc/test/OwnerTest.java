package ru.yandex.qatools.proc.test;

import org.hamcrest.Matcher;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * User: lanwen
 * Date: 23.10.14
 * Time: 14:28
 */
public class OwnerTest {
    @Test
    public void shouldBeEmailFieldInOwner() throws Exception {
        Field email = Owner.class.getDeclaredField("email");
        assertThat(email, notNullValue());
    }

    @Test
    public void shouldBeUidFieldInOwner() throws Exception {
        Field email = Owner.class.getDeclaredField("uid");
        assertThat(email, notNullValue());
    }

    @Test
    public void shouldBeNameFieldInOwner() throws Exception {
        Field email = Owner.class.getDeclaredField("name");
        assertThat(email, notNullValue());
    }

    @Test
    public void shouldFindMatcherClassForOwner() throws Exception {
        assertThat(Class.forName(OwnerMatchers.class.getCanonicalName()), notNullValue());
    }

    @Test
    public void shouldFindMatcherForEmailField() throws Exception {
        assertThat(OwnerMatchers.class.getDeclaredMethod("withEmail", Matcher.class), notNullValue());
    }

    @Test
    public void shouldFindMatcherForUidField() throws Exception {
        assertThat(OwnerMatchers.class.getDeclaredMethod("withUid", Matcher.class), notNullValue());
    }

    @Test(expected = NoSuchMethodException.class)
    public void shouldNotFindMatcherForNameField() throws Exception {
        OwnerMatchers.class.getDeclaredMethod("withName", Matcher.class);
    }
}
