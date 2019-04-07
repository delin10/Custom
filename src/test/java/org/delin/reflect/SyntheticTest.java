package org.delin.reflect;

public class SyntheticTest {
    private static class Inner {
        private int a;
        private Inner(int a){
            this.a=a;
        }
        //没有$1类
//        public Inner(){
//
//        }
    }

    static void checkSynthetic(String name) {
        try {
            System.out.println(name + " : " + Class.forName(name).isSynthetic());
        } catch (ClassNotFoundException exc) {
            exc.printStackTrace(System.out);
        }
    }

    public static void main(String[] args) throws Exception {
        new Inner(1);
        checkSynthetic("org.delin.reflect.SyntheticTest");
        checkSynthetic("org.delin.reflect.SyntheticTest$Inner");
        checkSynthetic("org.delin.reflect.SyntheticTest$1");
    }
}
