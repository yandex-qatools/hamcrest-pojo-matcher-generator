package ru.yandex.qatools.processors.matcher.gen.annotations;

import ru.yandex.qatools.processors.matcher.gen.MatchersGenProperties;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Source annotation to find fields to generate {@link org.hamcrest.FeatureMatcher} for it.
 * This annotation specified by default in properties. You can use your own instead
 *
 * @author lanwen (Merkushev Kirill)
 * @see MatchersGenProperties
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface GenerateMatcher {
}
