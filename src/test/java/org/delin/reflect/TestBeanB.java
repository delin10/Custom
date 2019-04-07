package org.delin.reflect;

public class TestBeanB {
    private int a;
    private TestBeanA ta;

    public TestBeanA getTa() {
        return ta;
    }

    public void setTa(TestBeanA ta) {
        this.ta = ta;
    }

    public TestBeanB(int a) {
        this.a = a;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }
}
