package org.delin.test.array;

import java.util.Arrays;

public class ArrayPrinter {
    public static void printIntArray(int[] arr){
        System.out.print("[");
        Arrays.stream(arr).forEach(e->{
            System.out.print(e);
            System.out.print(",");
        });
        System.out.println("]");
    }
}
