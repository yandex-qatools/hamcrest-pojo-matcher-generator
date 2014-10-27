package ru.yandex.qatools.processors.matcher.gen.processing;

import ch.lambdaj.function.convert.Converter;
import ru.yandex.qatools.processors.matcher.gen.bean.ClassDescription;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.valueOf;
import static ru.yandex.qatools.processors.matcher.gen.bean.FieldDescription.field;

/**
 * User: lanwen
 * Date: 18.09.14
 * Time: 18:50
 */
public class FillMapWithFieldsProcess implements Converter<Element, Void> {
    private Map<String, ClassDescription> classes = new HashMap<>();

    private FillMapWithFieldsProcess() {
    }

    public static FillMapWithFieldsProcess fillMapOfClassDescriptionsProcess() {
        return new FillMapWithFieldsProcess();
    }

    /**
     * Fills map of classes with each new field. Merges existing classes with new fields,
     * create new classes if field from new class
     * @param elem next element to process. Will be skipped if not field
     * @return null
     */
    @Override
    public Void convert(Element elem) {

        if (elem.getKind() == ElementKind.FIELD) {
            TypeElement classElement = (TypeElement) elem.getEnclosingElement();
            PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();

            ClassDescription classDescription = byClassFrom(classes,
                    packageElement.getQualifiedName(),
                    classElement.getSimpleName());

            classDescription.addField(field(elem.getSimpleName(), elem.asType().toString()));
        }

        return null;
    }

    /**
     * Get class description bean from map. If no such, creates a new one
     * @param map to find a bean
     * @param packageName package of target class
     * @param name - simple name of target class
     * @return ClassDescription bean to add new field
     */
    private ClassDescription byClassFrom(Map<String, ClassDescription> map,
                                         CharSequence packageName, CharSequence name) {
        String key = valueOf(packageName) + name;
        if (!map.containsKey(key)) {
            map.put(key, new ClassDescription(packageName, name));
        }
        return map.get(key);
    }

    /**
     * @return collection with all class beans
     */
    public Collection<ClassDescription> collectedClasses() {
        return classes.values();
    }
}
