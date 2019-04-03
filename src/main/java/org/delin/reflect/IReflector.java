package org.delin.reflect;

import java.lang.reflect.Field;

public interface IReflector {
    public void register(Class<?> clazz);

    public Field getField(Class<?> clazz,String name);

    public boolean setField(Class<?> clazz,String name,String value);
}
