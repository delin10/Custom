package org.delin.collection.tree;

/**
 * @author delin 2019-05-02
 * @param <K>
 * @param <V>
 */
public class Node<K extends Comparable, V> {
    private K key;
    private Object data;
    private Node<K, V> left;
    private Node<K, V> right;
    private Node<K, V> parent;

    public Node() {
    }

    public Node(K key, Object data) {
        this.key = key;
        this.data = data;
    }

    public Node(K key, Object data, Node<K, V> left, Node<K, V> right) {
        this.key = key;
        this.data = data;
        this.left = left;
        this.right = right;
    }

    public Node<K, V> getParent() {
        return parent;
    }

    public void setParent(Node<K, V> parent) {
        this.parent = parent;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Node<K, V> getLeft() {
        return left;
    }

    public void setLeft(Node<K, V> left) {
        this.left = left;
    }

    public Node<K, V> getRight() {
        return right;
    }

    public void setRight(Node<K, V> right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return  String.format("[key:%s, data:%s]",key,data);
    }
}
