package org.delin.reflect.impl;

import org.delin.reflect.AbstractReflector;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class JReflectorImpl extends AbstractReflector {
    //non-threadSafe
    protected Field cacheField(Class<?> clazz, String name) {
        Map<String, Field> ffields = null;
        if (fields == null) {
            fields = new HashMap<>();
        } else {
            ffields = fields.get(clazz);
        }
        Field field = null;
        try {
            field = clazz.getDeclaredField(name);
            if (ffields == null) {
                ffields = new HashMap<>();
                fields.put(clazz, ffields);
            }
            ffields.put(name, field);
        } catch (NoSuchFieldException e) {
            field = null;
        }
        return field;
    }

    @Override
    public Field getField(Class<?> clazz, String name) {
        Field field = cacheField(clazz, name);
        return field;
    }

    @Override
    public void recursivelyGetAllSuperClass(Class<?> clazz, Collection<Class<?>> c) {
        Class<?> sup = clazz.getSuperclass();
        if (sup.equals(Object.class)) {
            return;
        }
        c.add(sup);
        recursivelyGetAllSuperClass(sup, c);
    }

    @Override
    public boolean setFieldValue(Object obj, String name, Object value) {
        Field field = getField(obj.getClass(), name);
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            return false;
        }
        return true;
    }

    @Override
    public Field[] getAllField(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Map<String, Field> ffields = init(clazz);
        for (Field field : fields) {
            ffields.put(field.getName(), field);
        }
        return fields;
    }

    @Override
    public Object getValue(Field field, Object obj) {
        Object v = null;
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        try {
            v = field.get(obj);
        } catch (IllegalAccessException e) {
            v = null;
        }
        return v;
    }

    @Override
    public boolean isPrimitiveWrapper(Class<?> clazz) {
        return Integer.class.equals(clazz) || Float.class.equals(clazz) || Double.class.equals(clazz) || Long.class.equals(clazz) || Boolean.class.equals(clazz) || Byte.class.equals(clazz) || Short.class.equals(clazz) || Character.class.equals(clazz);
    }
}
