package ru.yandex.qatools.proc.test;

import org.junit.Test;

/**
 * @author lanwen (Merkushev Kirill)
 */
public class DontGenerateTests {

    @Test(expected = ClassNotFoundException.class)
    public void shouldNotGenerateWithIgnoringAnnotation() throws Exception {
        Class.forName(DontGenerateMatchersClass.class.getCanonicalName() + "Matchers");
    }

    @Test(expected = ClassNotFoundException.class)
    public void shouldNotGenerateDoubleNestedEmptyClass() throws Exception {
        Class.forName(DoubleEmptyClass.class.getCanonicalName() + "Matchers");
    }
}
