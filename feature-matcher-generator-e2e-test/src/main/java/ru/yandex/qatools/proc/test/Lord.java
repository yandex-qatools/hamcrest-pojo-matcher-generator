package ru.yandex.qatools.proc.test;

import ru.yandex.qatools.processors.matcher.gen.annotations.DoNotGenerateMatcher;
import ru.yandex.qatools.processors.matcher.gen.annotations.GenerateMatcher;

/**
 * @author Vladislav Bauer
 */

@GenerateMatcher
public class Lord {
    @DoNotGenerateMatcher
    private String name;
    private Integer slavesCount;


    public String getName() {
        return name;
    }

    public Integer getSlavesCount() {
        return slavesCount;
    }

}
