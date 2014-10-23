package ru.yandex.qatools.processors.matcher.gen.processing.matchers;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import ru.yandex.qatools.processors.matcher.gen.bean.ClassDescription;
import ru.yandex.qatools.processors.matcher.gen.bean.FieldDescription;

import java.util.Collection;
import java.util.List;

/**
 * User: lanwen
 * Date: 19.10.14
 * Time: 21:12
 */
public final class ElementMatchers {
    private ElementMatchers() {
    }

    public static Matcher<ClassDescription> withFields(Matcher<Collection<?>> matcher) {
        return new FeatureMatcher<ClassDescription, List<FieldDescription>>(matcher, "fields", "fields") {
            @Override
            protected List<FieldDescription> featureValueOf(ClassDescription actual) {
                return actual.fields();
            }
        };
    }
}
