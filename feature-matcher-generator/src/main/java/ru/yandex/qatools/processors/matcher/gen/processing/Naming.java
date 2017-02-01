package ru.yandex.qatools.processors.matcher.gen.processing;

import javax.lang.model.element.Name;

/**
 * To simplify some string operations
 *
 * @author lanwen (Merkushev Kirill)
 */
final class Naming {

    private static final String CLASS_SUFFIX = "Matchers";

    private Naming() {
        throw new UnsupportedOperationException();
    }

    /**
     * Used in template processing
     *
     * @param name field name
     * @return capitalized name with avoiding collisions for getClass() method
     */
    static String normalize(CharSequence name) {
        return capitalize(name.toString().replaceFirst("^_", ""))
                .replaceFirst("^Class$", "Class_");
    }

    static String withGeneratedSuffix(CharSequence what) {
        return what + CLASS_SUFFIX;
    }

    private static String capitalize(String name) {
        if (name != null && name.length() != 0) {
            char[] chars = name.toCharArray();
            chars[0] = Character.toUpperCase(chars[0]);
            return new String(chars);
        } else {
            return name;
        }
    }
}
