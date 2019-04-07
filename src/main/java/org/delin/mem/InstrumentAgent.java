package org.delin.mem;

import java.lang.instrument.Instrumentation;

public class InstrumentAgent {
    private static Instrumentation inst;

    public static void premain(String agent, Instrumentation instt) {
        inst = instt;
    }

    public static long sizeOf(Object obj){
        return inst.getObjectSize(obj);
    }

    public static void main(String[]args){
        System.out.println(sizeOf(new Integer(1)));
    }
}
