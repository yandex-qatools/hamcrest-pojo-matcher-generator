## Hamcrest Feature Matcher Generator for POJOs

Inspired by lot of dummy work to create matchers for fields in auto-generated beans to write POJO-based tests. 

Generates [Hamcrest](http://hamcrest.org/JavaHamcrest/)'s [Feature Matchers](http://hamcrest.org/JavaHamcrest/javadoc/1.3/org/hamcrest/FeatureMatcher.html) 
for every field annotated with `@GenerateMatcher` annotation (`ru.yandex.qatools.processors.matcher.gen.annotations.GenerateMatcher`).  

Useful for testing auto-generated unmarshalled beans with help of 
[jsonschema2pojo](https://github.com/joelittlejohn/jsonschema2pojo) + [Gson](https://code.google.com/p/google-gson/) or JAXB 

### Usage Guide

#### 1. Add dependency from Maven Central
```xml 
<dependency>
    <groupId>ru.yandex.qatools.processors</groupId>
    <artifactId>feature-matcher-generator</artifactId>
    <version>1.1</version>
    <!-- 'provided' scope because this is only needed during compilation -->
    <scope>provided</scope>
</dependency>
```

#### 2. Generate (or write) beans

```java 
public class Owner {
    @GenerateMatcher
    private String email;
    @GenerateMatcher
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

**Also**:  
- Can use any other annotation to process if override system property `matcher.gen.annotations`. 
- Can use multiply annotations comma-separated.

For example we have `@Expose` annotation (`com.google.gson.annotations.Expose`) by [Gson](https://code.google.com/p/google-gson/) 
and want to generate matchers for fields with such annotation. 


- Run compilation with 
```
mvn clean compile -Dmatcher.gen.annotations=ru.yandex.qatools.processors.matcher.gen.annotations.GenerateMatcher,com.google.gson.annotations.Expose
```
- Or put in classpath `matchers.gen.properties` with content 
```properties
 matcher.gen.annotations=ru.yandex.qatools.processors.matcher.gen.annotations.GenerateMatcher,com.google.gson.annotations.Expose
```

#### 4. See generated matchers

You can find result in `${project.build.directory}/generated-sources/annotations/*`.  
For each class with such fields will be generated class `*Matchers` with public static methods looks like: 

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

#### 5. Use it in your tests!

```java 
assertThat(someOwner, withEmail(containsString("@"))); 

// OR combined: 

assertThat(someOwner, both(withEmail(containsString("@"))).and(withUid(is(uid))); 
```


### How to debug Annotation Processors

#### With remote debugger on maven build

1. Run in shell `export MAVEN_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=8000,suspend=n"` 
2. Run `mvn clean compile`
3. Connect with remote debugger to port *8000*


