package ru.yandex.qatools.processors.matcher.gen.processing;

import org.junit.Test;

import javax.lang.model.element.Name;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * User: lanwen
 * Date: 19.10.14
 * Time: 21:18
 */
public class NamingTest {

    /**
     * If add 'get' to field _class with ignoring first underscore we got getClass().
     * Need to be getClass_();
     */
    @Test
    public void shouldAvoidGetClassMethodCollisions() throws Exception {
        assertThat(Naming.normalize("_class"), equalTo("Class_"));
    }

    @Test
    public void shouldIgnoreUnderscoresInBeginning() throws Exception {
        assertThat(Naming.normalize("_public"), equalTo("Public"));
    }
}
