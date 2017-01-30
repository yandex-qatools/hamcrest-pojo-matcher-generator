package ru.yandex.qatools.proc.test;

import ru.yandex.qatools.processors.matcher.gen.annotations.GenerateMatcher;


public class EmptyClass {

    @GenerateMatcher
    public static class StaticNestedInEmptyClass {

        int variable;

        public int getVariable() {
            return variable;
        }

        public void setVariable(int variable) {
            this.variable = variable;
        }

    }

}

