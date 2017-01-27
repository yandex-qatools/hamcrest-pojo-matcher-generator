package ru.yandex.qatools.proc.test;

import ru.yandex.qatools.processors.matcher.gen.annotations.GenerateMatcher;

/**
 * @author lanwen (Merkushev Kirill)
 */
@GenerateMatcher
public class StaticNestedClass {

    private String uninteresting;

    public String getUninteresting() {
        return uninteresting;
    }

}
