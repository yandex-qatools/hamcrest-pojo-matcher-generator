package ru.yandex.qatools.processors.matcher.gen.processing;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;

/**
 * @author darkfelex (Dmitriy Shibaev)
 */
public class ProcessingException extends RuntimeException {

    public ProcessingException(String message, Name simpleName, ElementKind kind, Exception cause) {
        super(String.format(message, simpleName, kind), cause);
    }

}
