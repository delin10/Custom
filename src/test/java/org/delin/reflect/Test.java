package org.delin.reflect;

import java.util.LinkedList;
import java.util.List;

public class Test {
    public static void main(String[]args){
        System.out.println(Object.class.isAssignableFrom(LinkedList.class));
        System.out.println(LinkedList.class.isAssignableFrom(List.class));
        System.out.println(List.class.isAssignableFrom(List.class));
    }
}
