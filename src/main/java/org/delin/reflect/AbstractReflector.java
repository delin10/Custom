package org.delin.reflect;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractReflector implements IReflector{
    protected static Map<Class<?>,Map<String,Field>> fields;
    protected static Map<Class<?>,Map<String,Field>> methods;
    protected Map<String,Field> init(Class<?> clazz){
        Map<String,Field> ffields=null;
        if (fields==null){
            fields=new HashMap<>();
        }else {
            ffields= fields.get(clazz);
        }
        if (ffields==null){
            ffields=new HashMap<>();
            fields.put(clazz,ffields);
        }
        return ffields;
    }
}
