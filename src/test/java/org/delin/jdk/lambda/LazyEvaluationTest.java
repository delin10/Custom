package org.delin.jdk.lambda;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class LazyEvaluationTest {
    static class A {
        private String name;

        public A(String name) {
            this.name = name;
        }

        public A get() {
            System.out.println("call A.get()");
            return this;
        }

        public String getName() {
            System.out.println("call A.getName()");
            return name;
        }
    }

    public static void main(String[] args) {
        List<A> ls = Arrays.asList(new A("0"), new A("1"), new A("2"));
        System.out.println("ls.stream().map(A::get)");
        Stream<A> stream = ls.stream().map(A::get);

        System.out.println("stream.map(A::getName)");
        Stream<String> ss = stream.map(A::getName);//进入这个阶段之后对stream操作发生IllegalStateException: stream has already been operated upon or closed
        System.out.println("ss.findFirst()");
        //ss.findFirst().get();
        Iterator<String> it=ss.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }
    }
}
