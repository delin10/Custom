package org.delin.mem;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    private static Map<Class<?>,Integer> primitives;
    public static int INT_SIZE_byte=4;
    public static int LONG_SIZE_byte=8;
    public static int CHAR_SIZE_byte=2;
    public static int BYTE_SIZE_byte=1;
    public static int DOUBLE_SIZE_byte=8;
    public static int FLOAT_SIZE_byte=4;
    public static int BOOLEAN_SIZE_byte=1;
    public static int SHORT_SIZE_byte=2;

    static {
        primitives=new HashMap<>();
        primitives.put(int.class,INT_SIZE_byte);
        primitives.put(Integer.class,INT_SIZE_byte);
        primitives.put(long.class,LONG_SIZE_byte);
        primitives.put(Long.class,LONG_SIZE_byte);
        primitives.put(byte.class,BYTE_SIZE_byte);
        primitives.put(Byte.class,BYTE_SIZE_byte);
        primitives.put(char.class,CHAR_SIZE_byte);
        primitives.put(Character.class,CHAR_SIZE_byte);
        primitives.put(float.class,FLOAT_SIZE_byte);
        primitives.put(Float.class,FLOAT_SIZE_byte);
        primitives.put(double.class,DOUBLE_SIZE_byte);
        primitives.put(Double.class,DOUBLE_SIZE_byte);
        primitives.put(short.class,SHORT_SIZE_byte);
        primitives.put(Short.class,SHORT_SIZE_byte);
        primitives.put(boolean.class,BOOLEAN_SIZE_byte);
        primitives.put(Boolean.class,BOOLEAN_SIZE_byte);
    }

    public static int get(Class<?> clazz){
        return primitives.get(clazz);
    }
}
