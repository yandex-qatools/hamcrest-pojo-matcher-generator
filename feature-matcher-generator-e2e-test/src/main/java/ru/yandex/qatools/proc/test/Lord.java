package ru.yandex.qatools.proc.test;

import ru.yandex.qatools.processors.matcher.gen.annotations.DoNotGenerateMatcher;
import ru.yandex.qatools.processors.matcher.gen.annotations.GenerateMatcher;

/**
 * @author Vladislav Bauer
 */

@GenerateMatcher
public class Lord {

//    #18
//    public static final String WEAPON = "Sword";

    private String name;
    private Integer slavesCount;

    @DoNotGenerateMatcher
    private String fullName;

    @GenerateMatcher
    @DoNotGenerateMatcher
    private Integer age;


    public String getName() {
        return name;
    }

    public Integer getSlavesCount() {
        return slavesCount;
    }

    public Object getFullName() {
        return fullName;
    }

    public Integer getAge() {
        return age;
    }
}
