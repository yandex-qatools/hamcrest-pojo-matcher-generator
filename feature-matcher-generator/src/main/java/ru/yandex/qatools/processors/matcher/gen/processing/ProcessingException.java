package ru.yandex.qatools.processors.matcher.gen.processing;

import javax.tools.JavaFileObject;

/**
 * @author darkfelex (Dmitriy Shibaev)
 */
public class ProcessingException extends RuntimeException {

    public ProcessingException(String message, String simpleName, JavaFileObject.Kind kind, Exception cause) {
        super(String.format("%s: %s (%s)", message, simpleName, kind), cause);
    }

}
