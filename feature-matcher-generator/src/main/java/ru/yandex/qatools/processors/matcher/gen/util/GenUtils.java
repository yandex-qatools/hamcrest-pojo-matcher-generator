package ru.yandex.qatools.processors.matcher.gen.util;

import org.apache.commons.lang.StringUtils;

/**
 * User: lanwen
 * Date: 18.09.14
 * Time: 16:26
 */
public final class GenUtils {

    public static final String CLASS_SUFFIX = "Matchers";

    private GenUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Used in template processing
     *
     * @param name - field name
     *
     * @return capitalized name with avoiding collisions for getClass() method
     */
    public static String normalize(String name) {
        return capitalize(name.replaceFirst("^_", ""))
                .replaceFirst("^Class$", "Class_");
    }

    public static String withGeneratedSuffix(String what) {
        return what + CLASS_SUFFIX;
    }

    public static String capitalize(String str) {
        return StringUtils.capitalize(str);
    }

}
