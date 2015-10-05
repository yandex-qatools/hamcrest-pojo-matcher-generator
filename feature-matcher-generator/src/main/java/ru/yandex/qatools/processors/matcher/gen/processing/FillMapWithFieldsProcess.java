package ru.yandex.qatools.processors.matcher.gen.processing;

import ch.lambdaj.function.convert.Converter;
import ru.yandex.qatools.processors.matcher.gen.bean.ClassDescription;
import ru.yandex.qatools.processors.matcher.gen.util.helpers.GeneratorHelper;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
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

    private final Map<String, ClassDescription> classes = new HashMap<>();
    private final GeneratorHelper helper;


    private FillMapWithFieldsProcess(GeneratorHelper helper) {
        this.helper = helper;
    }

    public static FillMapWithFieldsProcess fillMapOfClassDescriptionsProcess(GeneratorHelper helper) {
        return new FillMapWithFieldsProcess(helper);
    }

    /**
     * Fills map of classes with each new field. Merges existing classes with new fields,
     * create new classes if field from new class
     * @param elem next element to process. Will be skipped if not field
     * @return null
     */
    @Override
    public Void convert(Element elem) {
        ElementKind elemKind = elem.getKind();

        if (elemKind == ElementKind.FIELD) {
            TypeElement classElement = (TypeElement) elem.getEnclosingElement();
            PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();

            ClassDescription classDescription = byClassFrom(classes,
                    packageElement.getQualifiedName(),
                    classElement.getSimpleName());

            Name fieldName = elem.getSimpleName();
            String fieldType = helper.getWrappedType(elem).toString();
            classDescription.addField(field(fieldName, fieldType));
        } else if (elemKind == ElementKind.CLASS) {
            List<? extends Element> enclosedElements = elem.getEnclosedElements();

            for (Element enclosedElement : enclosedElements) {
                convert(enclosedElement);
            }
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
