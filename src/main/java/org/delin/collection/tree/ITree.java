package org.delin.collection.tree;

import java.util.List;

/**
 *
 * @param <K>
 * @param <V>
 * @author delin 2019-05-02
 */
public interface ITree<K extends Comparable, V> {
    /**
     * 若关键字已经存在，则重复插入
     * @param key
     * @param value
     * @return
     */
    Node<K,V> insertRepeatablily(K key, V value);

    /**
     * 若关键字已经存在，则更新关键字的data域
     * @param key
     * @param value
     * @return
     */
    Node<K,V> insertOrUpdate(K key, V value);

    /**
     * 删除查找遇到的第一个结点
     * @param key
     * @return
     */
    Node<K,V> deleteFirst(K key);

    /**
     * 删除关键字为key的所有结点
     * @param key
     * @return
     */
    List<Node<K, V>> deleteAll(K key);

    /**
     * 查找关键字为key的结点
     * @param key
     * @return
     */
    Node<K,V> search(K key);

    /**
     * 查找关键字为key的全部结点
     * @param key
     * @return
     */
    List<Node<K,V>> searchAll(K key);

    /**
     * 获得根结点
     * @return
     */
    Node<K,V> getRoot();

    /**
     * 判断二叉查找树是否为空
     * @return
     */
    boolean isEmpty();
}
