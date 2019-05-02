package org.delin.collection.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉搜索树
 * 支持关键字相容或相异
 * 插入
 * 删除第一个结点/删除所有
 * 搜索第一个结果
 * 搜索所有相同关键字的结点，返回List
 * 2019-05-02
 *
 * @param <K>
 * @param <V>
 * @author delin 2019-05-02
 */
public class BinarySortTree<K extends Comparable, V> implements ITree<K, V> {
    private Node<K, V> root;

    public BinarySortTree() {
    }

    @Override
    public Node<K, V> insertRepeatablily(K key, V value) {
        return insert(key, value, root, true);
    }

    @Override
    public Node<K, V> insertOrUpdate(K key, V value) {
        return insert(key, value, root, false);
    }

    @Override
    public Node<K, V> deleteFirst(K key) {
        Node<K, V> node = search(key);
        if (node == null) {
            return null;
        }

        if (node.getLeft() == null) {
            replace(node, node.getRight(), false);
        } else if (node.getRight() == null) {
            replace(node, node.getLeft(), false);
        } else {// if left and right not null
            Node<K, V> precursor = searchProcessor(node.getLeft());//find the precursor
            //不继承子结点
            replace(precursor, precursor.getLeft(), false);//replace precursor with its left node
            replace(node, precursor, true);//replace this node with precursor
        }
        return node;
    }

    @Override
    public List<Node<K, V>> deleteAll(K key) {
        List<Node<K, V>> ls = new ArrayList<>();
        while (true) {
            Node<K, V> node = deleteFirst(key);
            if (node == null) {
                break;
            }
            ls.add(node);
        }
        return ls;
    }

    @Override
    public Node<K, V> search(K key) {
        return helpSearch(key, root);
    }

    @Override
    public List<Node<K, V>> searchAll(K key) {
        List<Node<K, V>> ls = new ArrayList<>();
        helpSearchAll(key, root, ls);
        return ls;
    }

    @Override
    public Node<K, V> getRoot() {
        return root;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    private Node<K, V> helpSearch(K key, Node<K, V> node) {
        if (node == null) {
            return null;
        }
        if (key == null) {
            throw new NullPointerException("The search key cannot be null");
        }
        if (node.getKey().compareTo(key) > 0) {
            return helpSearch(key, node.getLeft());
        } else if (node.getKey().compareTo(key) < 0) {
            return helpSearch(key, node.getRight());
        } else {
            return node;
        }
    }

    private void helpSearchAll(K key, Node<K, V> node, List<Node<K, V>> ls) {
        if (node == null) {
            return;
        }
        if (key == null) {
            throw new NullPointerException("The search key cannot be null");
        }
        if (node.getKey().compareTo(key) > 0) {
            helpSearchAll(key, node.getLeft(), ls);
        } else if (node.getKey().compareTo(key) < 0) {
            helpSearchAll(key, node.getRight(), ls);
        } else {
            ls.add(node);
        }
    }

    private Node<K, V> searchProcessor(Node<K, V> node) {
        if (node.getRight() == null) {
            return node;
        }
        return searchProcessor(node.getRight());
    }

    private Node<K, V> insert(K key, V value, Node<K, V> parent, boolean repeatable) {
        if (root == null) {
            return root = new Node<>(key, value);
        }
        Node<K, V> node = null;
        //如果小于关键字 或者 可以插入相同关键字
        if (key.compareTo(parent.getKey()) < 0 || (repeatable && key.compareTo(parent.getKey()) == 0)) {
            if (parent.getLeft() != null) {
                node = insert(key, value, parent.getLeft(), repeatable);
            } else {
                node = new Node<>(key, value);
                setLeftChild(parent, node);
            }
        } else if (key.compareTo(parent.getKey()) > 0) {//大于关键字
            if (parent.getRight() != null) {
                node = insert(key, value, parent.getRight(), repeatable);
            } else {
                node = new Node<>(key, value);
                setRightChild(parent, node);
            }
        } else {//等于关键字
            parent.setData(value);
            node = parent;
        }
        return node;
    }

    /**
     * 替代cur结点为newNode结点
     *
     * @param cur
     * @param newNode
     * @param inherit 是否继承cur的子节点
     */
    private void replace(Node<K, V> cur, Node<K, V> newNode, boolean inherit) {
        Node<K, V> parent = cur.getParent();//if root, parent is null
        if (cur != root) {
            if (parent.getRight() == cur) {
                setRightChild(parent, newNode);
            } else {
                setLeftChild(parent, newNode);
            }
        } else {
            root = newNode;
        }
        if (inherit) {
            if (newNode != null) {
                setLeftChild(newNode, cur.getLeft());
                setRightChild(newNode, cur.getRight());
            }
        }
    }


    private void setLeftChild(Node<K, V> node, Node<K, V> child) {
        node.setLeft(child);
        if (child != null) {
            child.setParent(node);
        }
    }

    private void setRightChild(Node<K, V> node, Node<K, V> child) {
        node.setRight(child);
        if (child != null) {
            child.setParent(node);
        }
    }
}
