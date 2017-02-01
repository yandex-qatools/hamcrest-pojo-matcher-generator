package ru.yandex.qatools.proc.test;

import ru.yandex.qatools.processors.matcher.gen.annotations.DoNotGenerateMatcher;
import ru.yandex.qatools.processors.matcher.gen.annotations.GenerateMatcher;

/**
 * @author lanwen (Merkushev Kirill)
 */
@GenerateMatcher
@DoNotGenerateMatcher
public class DontGenerateMatchersClass {

    private String shouldNotBe;

    public String getShouldNotBe() {
        return shouldNotBe;
    }
}
