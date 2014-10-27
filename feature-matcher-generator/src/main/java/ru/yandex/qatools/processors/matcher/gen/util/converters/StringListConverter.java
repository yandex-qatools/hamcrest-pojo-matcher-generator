package ru.yandex.qatools.processors.matcher.gen.util.converters;

import org.apache.commons.beanutils.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: lanwen
 * Date: 23.10.14
 * Time: 0:13
 */
public class StringListConverter implements Converter {

    @Override
    public Object convert(Class type, Object o) {
        if (List.class.equals(type))
            if (!(o instanceof String)) {
                return new ArrayList<String>();
            }
        String str = (String) o;
        return Arrays.asList(str.split(","));
    }

}
