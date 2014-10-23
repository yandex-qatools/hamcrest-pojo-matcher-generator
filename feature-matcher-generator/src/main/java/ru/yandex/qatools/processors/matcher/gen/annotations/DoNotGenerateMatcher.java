package ru.yandex.qatools.processors.matcher.gen.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User: lanwen
 * Date: 23.10.14
 * Time: 0:25
 *
 * Not used anywhere by now
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface DoNotGenerateMatcher {
}
