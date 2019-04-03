package org.delin.reflect;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractReflector implements IReflector{
    private static Map<Class<?>,Map<String,Field>> fields;
    protected void init(){
        fields=new HashMap<>();
    }
}
