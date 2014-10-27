package ru.yandex.qatools.processors.matcher.gen.bean;

/**
 * User: lanwen
 * Date: 18.09.14
 * Time: 16:42
 *
 * Bean to store info about field to generate matcher
 */
public class FieldDescription {
    private CharSequence name;
    private CharSequence type;

    private FieldDescription(CharSequence name, CharSequence type) {
        this.name = name;
        this.type = type;
    }

    public static FieldDescription field(CharSequence name, CharSequence type) {
        return new FieldDescription(name, type);
    }

    public String name() {
        return String.valueOf(name);
    }

    public String type() {
        return String.valueOf(type);
    }
}
