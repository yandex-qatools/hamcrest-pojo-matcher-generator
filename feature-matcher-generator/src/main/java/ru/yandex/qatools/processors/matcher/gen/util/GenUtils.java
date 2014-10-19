package ru.yandex.qatools.processors.matcher.gen.util;

import org.apache.commons.lang3.StringUtils;

/**
 * User: lanwen
 * Date: 18.09.14
 * Time: 16:26
 */
public class GenUtils {
    public static final String CLASS_SUFFIX = "Matchers";

    public static Class forName(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Used in template processing
     * @param name - field name
     * @return capitalized name with avoiding collisions for getClass() method
     */
    @SuppressWarnings("unused")
    public String normalize(String name) {
         return StringUtils.capitalize(name.replaceFirst("^_", ""))
                 .replaceFirst("^Class$", "Class_");
    }


    @SuppressWarnings("unused")
    public String withGeneratedSuffix(String what) {
         return what + CLASS_SUFFIX;
    }
}
