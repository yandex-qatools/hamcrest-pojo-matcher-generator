package ru.yandex.qatools.proc.test;

import ru.yandex.qatools.processors.matcher.gen.annotations.GenerateMatcher;

@GenerateMatcher
public class EnclosingClass {

    private StaticNestedClass staticNested;

    public StaticNestedClass getStaticNested() {
        return staticNested;
    }

    public static class StaticNestedClass {

        private StaticNestedForStaticNestedClass staticNestedTwice;

        public StaticNestedForStaticNestedClass getStaticNestedTwice() {
            return staticNestedTwice;
        }

        public class StaticNestedForStaticNestedClass {

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

