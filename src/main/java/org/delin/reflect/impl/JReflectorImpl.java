package org.delin.reflect.impl;

import org.delin.reflect.IReflector;

import java.lang.reflect.Field;

public class JReflectorImpl implements IReflector {

    @Override
    public void register(Class<?> clazz) {

    }

    @Override
    public Field getField(Class<?> clazz, String name) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            field = null;
        }
        return field;
    }

    @Override
    public boolean setField(Class<?> clazz, String name, String value) {
        return false;
    }
}
