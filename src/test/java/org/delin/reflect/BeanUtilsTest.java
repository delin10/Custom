package org.delin.reflect;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.BeanUtilsBean2;

import java.lang.reflect.InvocationTargetException;

public class BeanUtilsTest {
    private static BeanUtilsBean bub2 = BeanUtilsBean2.getInstance();

    public static void main(String[] args) {
        TestBeanA a = new TestBeanA(1);
        TestBeanB b = new TestBeanB(2);
        TestBeanB b1 = new TestBeanB(3);

        try {
            bub2.copyProperties(a, b);
            bub2.populate(null, null);
            System.out.println(a.getA());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
