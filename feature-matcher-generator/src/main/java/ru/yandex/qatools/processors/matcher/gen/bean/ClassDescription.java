package ru.yandex.qatools.processors.matcher.gen.bean;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * User: lanwen
 * Date: 18.09.14
 * Time: 16:41
 *
 * Bean to store info about class. Stores fields list for matcher generation.
 */
public class ClassDescription {

    private CharSequence packageName;
    private CharSequence name;

    private Set<FieldDescription> fields = new LinkedHashSet<>();


    public ClassDescription(CharSequence packageName, CharSequence name) {
        this.packageName = packageName;
        this.name = name;
    }

    public String packageName() {
        return String.valueOf(packageName);
    }

    public String name() {
        return String.valueOf(name);
    }

    public Collection<FieldDescription> fields() {
        return fields;
    }

    public void addField(FieldDescription field) {
        fields().add(field);
    }
}
