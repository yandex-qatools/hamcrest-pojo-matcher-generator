## Feature Matcher Generator

Inspired by lot of dummy work to create matchers for fields in auto-generated beans. 

Generates [Hamcrest](http://hamcrest.org/JavaHamcrest/)'s [Feature Matchers](http://hamcrest.org/JavaHamcrest/javadoc/1.3/org/hamcrest/FeatureMatcher.html) 
for every field annotated with `@Expose` annotation (`com.google.gson.annotations.Expose`) by [Gson](https://code.google.com/p/google-gson/) 

Useful with auto-generated beans with [jsonschema2pojo](https://github.com/joelittlejohn/jsonschema2pojo) 

### Usage Guide

#### 1. Add dependency from Maven Central
```xml 
<dependency>
    <groupId>ru.yandex.qatools.processors</groupId>
    <artifactId>feature-matcher-generator</artifactId>
    <version>1.0</version>
</dependency>
```

#### 2. Generate (or write) beans

```java 
public class Owner {
    @Expose
    private String email;
    @Expose
    private String uid;

    public String getEmail() {
        return email;
    }

    public String getUid() {
        return uid;
    }
}
``` 

Beans `MUST` have getters with named `get[Field]()` to generate working matchers

#### 3. Compile

Run `mvn clean compile`

#### 4. See generated matchers

You can find result in `${project.build.directory}/generated-sources/annotations/*`.  
For each class with such fields will be generated class `*Matchers` that looks like: 

```java
/**
 * Matcher for {@link ru.yandex.qatools.beans.Owner#email}
 */
@org.hamcrest.Factory
public static org.hamcrest.Matcher<ru.yandex.qatools.beans.Owner> withEmail(org.hamcrest.Matcher<java.lang.String> matcher) {
    return new org.hamcrest.FeatureMatcher<ru.yandex.qatools.beans.Owner, java.lang.String>(matcher, "email", "email") {
        @Override
        protected java.lang.String featureValueOf(ru.yandex.qatools.beans.Owner actual) {
            return actual.getEmail();
        }
    };
}
```

#### 5. Use it!

```java 
assertThat(someOwner, withEmail(containsString("@"))); 
```
