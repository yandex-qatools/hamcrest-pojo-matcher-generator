package ru.yandex.qatools.processors.matcher.gen.processing.utils;

import javax.lang.model.element.Name;

/**
 * User: lanwen
 * Date: 21.10.14
 * Time: 21:19
 */
public final class TestObjFactory {
    private TestObjFactory() {
    }


    public static Name name(final String name) {
        return new Name() {
            @Override
            public boolean contentEquals(CharSequence cs) {
                return name.equals(cs);
            }

            @Override
            public int length() {
                return name.length();
            }

            @Override
            public char charAt(int index) {
                return name.charAt(index);
            }

            @Override
            public CharSequence subSequence(int start, int end) {
                return name.subSequence(start, end);
            }
        };
    }
}
