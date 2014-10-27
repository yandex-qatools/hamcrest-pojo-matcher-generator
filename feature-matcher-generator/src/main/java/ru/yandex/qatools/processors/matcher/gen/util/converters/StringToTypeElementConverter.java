package ru.yandex.qatools.processors.matcher.gen.util.converters;

import ch.lambdaj.function.convert.Converter;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * User: lanwen
 * Date: 23.10.14
 * Time: 0:34
 */
public class StringToTypeElementConverter implements Converter<String, TypeElement> {

    private Elements elUtils;

    private StringToTypeElementConverter(Elements elUtils) {
        this.elUtils = elUtils;
    }

    public static StringToTypeElementConverter toTypeElements(Elements elUtils) {
        return new StringToTypeElementConverter(elUtils);
    }

    /**
     * Converts annotation name from string to {@link javax.lang.model.element.TypeElement}
     * @param from - full qualified class name to return type element (used for annotations mostly)
     * @return TypeElement of annotation class
     */
    @Override
    public TypeElement convert(String from) {
        return elUtils.getTypeElement(from);
    }
}
