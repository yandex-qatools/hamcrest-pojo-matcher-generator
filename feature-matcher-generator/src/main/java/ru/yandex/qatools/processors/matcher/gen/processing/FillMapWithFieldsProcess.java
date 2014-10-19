package ru.yandex.qatools.processors.matcher.gen.processing;

import ch.lambdaj.function.convert.Converter;
import ru.yandex.qatools.processors.matcher.gen.bean.ClassDescription;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import java.util.HashMap;
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

    public static FillMapWithFieldsProcess fillMapOfClassDescriptions() {
        return new FillMapWithFieldsProcess();
    }

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

    private ClassDescription byClassFrom(Map<String, ClassDescription> map,
                                         CharSequence packageName, CharSequence name) {
        String key = valueOf(packageName) + name;
        if (!map.containsKey(key)) {
            map.put(key, new ClassDescription(packageName, name));
        }
        return map.get(key);
    }


    public Map<String, ClassDescription> collectedClassesMap() {
        return classes;
    }
}
