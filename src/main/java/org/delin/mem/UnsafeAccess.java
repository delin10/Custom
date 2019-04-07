package org.delin.mem;

import org.delin.reflect.IReflector;
import org.delin.reflect.impl.JReflectorImpl;
import sun.misc.Unsafe;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

// Thread Unsafe
public class UnsafeAccess {
    private static volatile Unsafe unsafe;
    private static IReflector reflector = new JReflectorImpl();

    /**
     * Thread-Safe Singleton
     *
     * @return
     */
    public static Unsafe getUnsafe() {
        if (unsafe == null) {
            Field theUnsafeInstance = null;
            try {
                theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
                theUnsafeInstance.setAccessible(true);
                unsafe = (Unsafe) theUnsafeInstance.get(Unsafe.class);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                unsafe = null;
                e.printStackTrace();
            }

        }
        return unsafe;
    }

    public static long objectSizeOf(Object obj) {
        if (obj == null) {
            throw new NullPointerException("obj is null");
        }
        Class<?> clazz = obj.getClass();
        //primitive type
        if (clazz.isPrimitive()) {
            return Constants.get(clazz);
        }
        //Array
        else if (clazz.isArray()) {
            long head = unsafe.arrayBaseOffset(clazz);
            long compSize = unsafe.arrayIndexScale(clazz);
            int len = Array.getLength(obj);
            return head + compSize * len;
        }
        //Object
        else {
            Field[] fields = reflector.getAllField(clazz);
            int len = fields.length;
            long[] offsets = new long[len];
            for (int i = 0; i < len; ++i) {
                Field field = fields[i];
                System.out.println(field.getName() + ":" + Integer.toBinaryString(field.getModifiers()));
                //存在哪位 哪位为0
                if ((field.getModifiers() & Modifier.STATIC) == 0) {
                    System.out.println(fields[i].getName());
                    offsets[i] = unsafe.objectFieldOffset(fields[i]);
                } else {
                    //offsets[i] = unsafe.staticFieldOffset(fields[i]);
                }
                System.out.println(fields[i].getName() + ":" + offsets[i]);
            }
            long base = Arrays.stream(offsets).min().getAsLong();
            System.out.println("--------base:" + base);
            return base + len * 4;
        }
    }

    public static long objectSizeOfRecursively(Object obj) {
        if (obj == null) {
            throw new NullPointerException("obj is null");
        }
        Class<?> clazz = obj.getClass();
        //primitive type
        if (clazz.isPrimitive() || reflector.isPrimitiveWrapper(clazz)) {
            System.out.println("primitive==========");
            return Constants.get(clazz);
        }
        //Array
        else if (clazz.isArray()) {
            long head = unsafe.arrayBaseOffset(clazz);
            long compSize = unsafe.arrayIndexScale(clazz);
            int len = Array.getLength(obj);
            return head + compSize * len;
        }
        //Object
        else {
            Field[] fields = reflector.getAllField(clazz);
            int len = fields.length;
            int size = 0;
            long[] offsets = new long[len];
            for (int i = 0; i < len; ++i) {
                Field field = fields[i];
                field.setAccessible(true);
                //System.out.println(field.getName()+":"+Integer.toBinaryString(field.getModifiers()));
                //存在哪位 哪位为0
                if ((field.getModifiers() & Modifier.STATIC) == 0) {
                    offsets[i] = unsafe.objectFieldOffset(fields[i]);
                    try {
                        Object objj = field.get(obj);
                        if (objj != null) {
                            System.out.println("递归求解**************:"+objj.getClass());
                            size += objectSizeOfRecursively(objj);
                            System.out.println("递归OK******************8:"+objj.getClass());
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else {
                    offsets[i] = unsafe.staticFieldOffset(fields[i]);
                }
                System.out.println(fields[i].getName() + " offset:" + offsets[i]);
            }

            //计算对象头有问题 域的偏移量完全没有关系
            long base = Arrays.stream(offsets).filter(offset->offset!=0).min().getAsLong();
            System.out.println("--------base:" + base);
            return base + len * 4 + size;
        }
    }
}
