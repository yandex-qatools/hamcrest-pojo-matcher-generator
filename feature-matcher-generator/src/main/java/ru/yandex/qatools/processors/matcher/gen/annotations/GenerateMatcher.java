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
 * Source annotation to find fields to generate {@link org.hamcrest.FeatureMatcher} for it.
 * This annotation specified by default in properties. You can use your own instead
 * @see ru.yandex.qatools.processors.matcher.gen.util.MatchersGenProperties
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ ElementType.FIELD, ElementType.TYPE })
public @interface GenerateMatcher {
}
