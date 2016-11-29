package ru.yandex.qatools.proc.test;

import ru.yandex.qatools.processors.matcher.gen.annotations.GenerateMatcher;

@GenerateMatcher
public class EnclosingClass {

    private StaticNestedClass1 staticNestedObject1;

    public StaticNestedClass1 getStaticNestedObject1() {
        return staticNestedObject1;
    }

    public static class StaticNestedClass1 {

        private StaticNestedClass2 staticNestedObject2;

        public StaticNestedClass2 getStaticNestedObject2() {
            return staticNestedObject2;
        }

        public class StaticNestedClass2 {

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

