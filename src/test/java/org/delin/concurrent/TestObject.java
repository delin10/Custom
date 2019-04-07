package org.delin.concurrent;

public class TestObject {
    private String a="old";
    private String b="old";

    @Override
    public String toString() {
        return "TestObject{" +
                "a='" + a + '\'' +
                ", b='" + b + '\'' +
                '}';
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getA() {

        return a;
    }

    public void setA(String a) {
        this.a = a;
    }
}
