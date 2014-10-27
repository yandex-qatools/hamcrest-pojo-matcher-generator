package ru.yandex.qatools.proc.test;

import com.google.gson.annotations.Expose;
import ru.yandex.qatools.processors.matcher.gen.annotations.GenerateMatcher;

/**
 * User: lanwen
 * Date: 23.10.14
 * Time: 11:44
 */
public class Owner {

    @GenerateMatcher
    private String email;

    @Expose
    private String uid;

    private String name;

    public String getEmail() {
        return email;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }
}
