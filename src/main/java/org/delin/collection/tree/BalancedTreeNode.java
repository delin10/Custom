package org.delin.collection.tree;

public class BalancedTreeNode<K extends Comparable, V> extends Node<K, V> {
    private int balanceFactor = 1;//null高度定义为0的话必须设置为1
    private BalancedTreeNode<K, V> left, right, parent;

    @Override
    public BalancedTreeNode<K, V> getLeft() {
        return left;
    }

    //重载参数不能BalancedTreeNode 调用了父类的
    public void setLeft(Node<K, V> left) {
        this.left = (BalancedTreeNode<K, V>) left;
    }

    @Override
    public BalancedTreeNode<K, V> getRight() {
        return right;
    }

    public void setRight(Node<K, V> right) {
        this.right = (BalancedTreeNode<K, V>) right;
    }

    @Override
    public BalancedTreeNode<K, V> getParent() {
        return parent;
    }

    public void setParent(Node<K, V> parent) {
        this.parent = (BalancedTreeNode<K, V>) parent;
    }

    public int getBalanceFactor() {
        return balanceFactor;
    }

    public void setBalanceFactor(int balanceFactor) {
        this.balanceFactor = balanceFactor;
    }

    public void inc() {
        setBalanceFactor(getBalanceFactor() + 1);
    }

    public void dec() {
        setBalanceFactor(getBalanceFactor() - 1);
    }

    @Override
    public String toString() {
        return super.toString() + String.format("<<bf=%s>>", balanceFactor);
    }
}
