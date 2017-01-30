package ru.yandex.qatools.processors.matcher.gen.bean;

import com.squareup.javapoet.TypeSpec;

/**
 * User: lanwen
 * Date: 18.09.14
 * Time: 16:41
 *
 * Bean to store info about class. Stores fields list for matcher generation.
 */
public class ClassSpecDescription {

    private TypeSpec spec;

    public ClassSpecDescription(TypeSpec spec) {
        this.spec = spec;
    }

    public TypeSpec getSpec() {
        return spec;
    }

    public void setSpec(TypeSpec spec) {
        this.spec = spec;
    }
}
