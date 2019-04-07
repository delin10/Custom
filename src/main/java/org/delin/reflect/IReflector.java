package org.delin.reflect;

import java.lang.reflect.Field;
import java.util.Collection;

public interface IReflector {
    public Field getField(Class<?> clazz,String name);

    public Field[] getAllField(Class<?> clazz);

    public boolean setField(Class<?> clazz,String name,String value);

    public Object getValue(Field field,Object obj);

    public void recursivelyGetAllSuperClass(Class<?> clazz, Collection<Class<?>> c);

    public boolean isPrimitiveWrapper(Class<?> clazz);
}
