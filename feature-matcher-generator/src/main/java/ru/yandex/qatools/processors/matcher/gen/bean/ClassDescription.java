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

    private final CharSequence packageName;
    private final CharSequence name;
    private final CharSequence qualifiedName;

    private Set<FieldDescription> fields = new LinkedHashSet<>();

    public ClassDescription(CharSequence packageName, CharSequence className) {
        this.packageName = packageName;
        this.name = className;
        this.qualifiedName = packageName + "." + className;
    }

    public ClassDescription(CharSequence packageName, CharSequence className, CharSequence qualifiedName) {
        this.qualifiedName = qualifiedName;
        this.packageName = packageName;
        this.name = className;
    }

    public String packageName() {
        return String.valueOf(packageName);
    }

    public String name() {
        return String.valueOf(name);
    }

    /**
     * @return Full name of class including package name, owner classes names
     * (in case of multiple nested classes) and class' own name
     */
    public CharSequence qualifiedName() {
        return qualifiedName;
    }

    public Collection<FieldDescription> fields() {
        return fields;
    }

    public void addField(FieldDescription field) {
        fields().add(field);
    }
}
