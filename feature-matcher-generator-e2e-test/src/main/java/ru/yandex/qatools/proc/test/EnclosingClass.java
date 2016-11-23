package ru.yandex.qatools.proc.test;

import ru.yandex.qatools.processors.matcher.gen.annotations.GenerateMatcher;

@GenerateMatcher
public class EnclosingClass {

    private InnerClass innerObject;

    public InnerClass getInnerObject() {
        return innerObject;
    }

    public void setInnerObject(InnerClass innerObject) {
        this.innerObject = innerObject;
    }

    public static class InnerClass {

        int variable;

        public int getVariable() {
            return variable;
        }

        public void setVariable(int variable) {
            this.variable = variable;
        }
    }

}

