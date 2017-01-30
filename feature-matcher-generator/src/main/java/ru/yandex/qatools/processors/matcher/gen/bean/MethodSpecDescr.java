package ru.yandex.qatools.processors.matcher.gen.bean;

import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Element;

/**
 * User: lanwen
 * Date: 18.09.14
 * Time: 16:42
 *
 * Bean to store info about field to generate matcher
 */
public class MethodSpecDescr {
    private Element field;
    private MethodSpec spec;

    public MethodSpecDescr(Element field, MethodSpec spec) {
        this.field = field;
        this.spec = spec;
    }

    public Element getField() {
        return field;
    }

    public void setField(Element field) {
        this.field = field;
    }

    public MethodSpec getSpec() {
        return spec;
    }

    public void setSpec(MethodSpec spec) {
        this.spec = spec;
    }
}
