package ru.yandex.qatools.processors.matcher.gen.bean;

import java.util.Objects;

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

    @Override
    public int hashCode() {
        return Objects.hash(name(), type());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FieldDescription) {
            final FieldDescription other = (FieldDescription) obj;
            return Objects.equals(other.name(), name())
                && Objects.equals(other.type(), type());
        }
        return false;
    }
}
