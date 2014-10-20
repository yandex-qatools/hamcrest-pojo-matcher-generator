package ru.yandex.qatools.processors.matcher.gen.util;

import java.util.logging.Logger;

import static java.lang.String.format;

/**
 * User: lanwen
 * Date: 18.09.14
 * Time: 16:26
 */
public class GenUtils {
    public static final String CLASS_SUFFIX = "Matchers";
    public static final Logger LOGGER = Logger.getLogger(GenUtils.class.getCanonicalName());

    public static Class forName(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            LOGGER.info(format("Can't find class %s in classpath, skipping...", name));
            return null;
        }
    }

    /**
     * Used in template processing
     *
     * @param name - field name
     *
     * @return capitalized name with avoiding collisions for getClass() method
     */
    @SuppressWarnings("unused")
    public String normalize(String name) {
        return capitalize(name.replaceFirst("^_", ""))
                .replaceFirst("^Class$", "Class_");
    }


    @SuppressWarnings("unused")
    public String withGeneratedSuffix(String what) {
        return what + CLASS_SUFFIX;
    }


    public static String capitalize(final String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }

        char firstChar = str.charAt(0);
        if (Character.isTitleCase(firstChar)) {
            // already capitalized
            return str;
        }

        return new StringBuilder(strLen)
                .append(Character.toTitleCase(firstChar))
                .append(str.substring(1))
                .toString();
    }
}
