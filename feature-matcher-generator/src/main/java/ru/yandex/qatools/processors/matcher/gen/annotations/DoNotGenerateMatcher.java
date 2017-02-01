package ru.yandex.qatools.processors.matcher.gen.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Don't generate matchers for annotated field/class
 *
 * @author lanwen (Merkushev Kirill)
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface DoNotGenerateMatcher {
}
