package ru.yandex.qatools.proc.test;

import ru.yandex.qatools.processors.matcher.gen.annotations.GenerateMatcher;

@GenerateMatcher
public class Capitalization {
    private String id;

    public String getID() {
        return id;
    }
}
