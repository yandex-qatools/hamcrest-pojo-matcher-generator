package ru.yandex.qatools.proc.test;

import ru.yandex.qatools.processors.matcher.gen.annotations.GenerateMatcher;


@GenerateMatcher
public class DoubleEmptyClass {

    public static class StaticNestedInDoubleEmptyClass {

        public static class StaticNestedTwiceInDoubleEmptyClass {

            int variable;

            public int getVariable() {
                return variable;
            }

            public void setVariable(int variable) {
                this.variable = variable;
            }

        }

    }

}

