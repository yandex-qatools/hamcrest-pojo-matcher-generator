package ru.yandex.qatools.proc.test;

import ru.yandex.qatools.processors.matcher.gen.annotations.GenerateMatcher;

/**
 * @author Vladislav Bauer
 */

@GenerateMatcher
public class Lord {

    private String name;
    private Integer slavesCount;


    public String getName() {
        return name;
    }

    public Integer getSlavesCount() {
        return slavesCount;
    }

}
