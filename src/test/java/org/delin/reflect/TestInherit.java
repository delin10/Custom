package org.delin.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public class TestInherit {
    private static class Generic<T> {
        public Generic(T v) {
            this.v = v;
        }

        public T getV() {
            return v;
        }

        public void setV(T v) {
            this.v = v;
        }

        T v;
    }

    public static void main(String[] args) {
        B b = new B();
        C c = new C();
        //private static int org.delin.reflect.B.sa
        //private int org.delin.reflect.B.c
        Field[] fields0 = B.class.getDeclaredFields();
        for (Field field : fields0) {
            System.out.println(field);
        }
        //private static int org.delin.reflect.B.sa
        //private int org.delin.reflect.B.c
        Field[] fields1 = C.class.getDeclaredFields();
        for (Field field : fields1) {
            System.out.println(field);
        }

        Class<?> supB = B.class.getSuperclass();
        Field[] fields2 = supB.getDeclaredFields();
        for (Field field : fields2) {
            System.out.println(field);
        }
        System.out.println(supB.getSuperclass());
        System.out.println(Object.class.getSuperclass());
        List<Class<?>> ls = new LinkedList<>();
        recursivelyGetAllSuperClass(D.class, ls);
        System.out.println(ls);
        Set<Class<?>> alltrypes = new HashSet<>();
        recursivelyGetAllInheritType(IG.class, alltrypes);
        System.out.println("recursivelyGetAllInheritType:" + alltrypes);
        //测试获得父类的方法
        System.out.println("接口获取父类:" + IF.class.getSuperclass());
        System.out.println(IF.class.getInterfaces()[0]);
        //测试获得父接口的方法
        System.out.println(IE.class.getInterfaces().length);
        //获取泛型的类型
        Generic<Integer> g = new Generic<>(1);
        System.out.println("泛型类型:" + g.v.getClass());
        List<Integer> ls1 = new LinkedList<>();
        ParameterizedType parameterizedType = (ParameterizedType) ls1.getClass().getGenericSuperclass();
        System.out.println(parameterizedType.getRawType());
        System.out.println(parameterizedType.getActualTypeArguments()[0]);
        System.out.println(parameterizedType.getOwnerType());
        System.out.println(parameterizedType.getTypeName());
    }

    /**
     * 获得所有继承的类
     * @param clazz
     * @param c
     */
    public static void recursivelyGetAllSuperClass(Class<?> clazz, Collection c) {
        Class<?> sup = clazz.getSuperclass();
        if (sup.equals(Object.class)) {
            return;
        }
        c.add(sup);
        recursivelyGetAllSuperClass(sup, c);
    }

    /**
     * 同时获得所有继承的接口和类
     * @param clazz
     * @param s
     */
    public static void recursivelyGetAllInheritType(Class<?> clazz, Set s) {
        Class<?> sup = clazz.getSuperclass();
        Class<?>[] interfaces = clazz.getInterfaces();
        if (sup != null && !(Object.class == sup) && s.add(sup)) {
            recursivelyGetAllInheritType(sup, s);
        }

        for (Class<?> c : interfaces) {
            if (s.add(c)) {
                recursivelyGetAllInheritType(c, s);
            }
        }
    }
}
