package ru.yandex.qatools.processors.matcher.gen.processing;

import ru.yandex.qatools.processors.matcher.gen.bean.ClassDescription;
import ru.yandex.qatools.processors.matcher.gen.util.helpers.GeneratorHelper;

import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import static ru.yandex.qatools.processors.matcher.gen.bean.FieldDescription.field;

/**
 * User: lanwen
 * Date: 18.09.14
 * Time: 18:50
 */
public class FillMapWithFieldsProcess implements Consumer<Element> {

    private final Map<CharSequence, ClassDescription> classes = new HashMap<>();
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
     *
     * @param elem next element to process. Will be skipped if not field
     */
    @Override
    public void accept(Element elem) {
        try {
            switch (elem.getKind()) {
                case FIELD: {
                    TypeElement classElement = (TypeElement) elem.getEnclosingElement();

                    ClassDescription classDescription = byClassFrom(classes,
                            getPackageNameOf(classElement),
                            classElement.getSimpleName(),
                            classElement.getQualifiedName()
                    );

                    Name fieldName = elem.getSimpleName();
                    String fieldType = helper.getWrappedType(elem).toString();
                    classDescription.addField(field(fieldName, fieldType));

                    break;
                }

                case CLASS: {
                    elem.getEnclosedElements().forEach(this::accept);
                    break;
                }
            }
        } catch (Exception e) {
            throw new ProcessingException(
                    "Exception during processing of '%s' of kind '%s'",
                    elem.getSimpleName(),
                    elem.getKind(),
                    e
            );
        }
    }

    private CharSequence getPackageNameOf(TypeElement classElement) {
        Element enclosingElement = classElement.getEnclosingElement();
        if (enclosingElement instanceof PackageElement) {
            return ((PackageElement) enclosingElement).getQualifiedName();
        }

        return null;
    }

    /**
     * Get class description bean from map. If no such, creates a new one
     *
     * @param map           to find a bean
     * @param packageName   package of target class
     * @param name          simple name of target class
     * @param qualifiedName full name of target class (package + owner classes + simple name)
     * @return ClassDescription bean to add new field
     */
    private ClassDescription byClassFrom(
            Map<CharSequence, ClassDescription> map,
            CharSequence packageName,
            CharSequence name,
            CharSequence qualifiedName
    ) {
        return map.computeIfAbsent(qualifiedName, qname -> new ClassDescription(packageName, name, qname));
    }

    /**
     * @return collection with all class beans
     */
    public Collection<ClassDescription> collectedClasses() {
        return classes.values();
    }
}
