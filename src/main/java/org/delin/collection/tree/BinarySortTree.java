package org.delin.collection.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

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
    protected int size;

    public BinarySortTree() {
    }

    @Override
    public Node<K, V> insertRepeatablily(K key, V value) {
        return insert(key, value, getRoot(), true, null, null, null, () -> new Node());
    }

    @Override
    public Node<K, V> insertOrUpdate(K key, V value) {
        return insert(key, value, getRoot(), false, null, null, null, () -> new Node());
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
        --size;
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
        return helpSearch(key, getRoot());
    }

    @Override
    public List<Node<K, V>> searchAll(K key) {
        List<Node<K, V>> ls = new ArrayList<>();
        helpSearchAll(key, getRoot(), ls);
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

    @Override
    public int getSize() {
        return size;
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

    protected Node<K, V> insert(K key, V value, Node<K, V> parent, boolean repeatable, Consumer<Node<K, V>> progress, Consumer<Node<K, V>> complete, Consumer<Node<K, V>> after, Supplier<Node<K, V>> supplier) {
        if (getRoot() == null) {
            Node<K, V> newRoot = newNode(supplier, key, value);
            setRoot(newRoot);
            if (complete != null) {
                complete.accept(newRoot);
            }
            ++size;
            return newRoot;
        }
        if (progress != null) {
            progress.accept(parent);
        }
        Node<K, V> node = null;
        //如果小于关键字 或者 可以插入相同关键字
        if (key.compareTo(parent.getKey()) < 0 || (repeatable && key.compareTo(parent.getKey()) == 0)) {
            if (parent.getLeft() != null) {
                node = insert(key, value, parent.getLeft(), repeatable, progress, complete, after, supplier);
            } else {
                node = newNode(supplier, key, value);
                setLeftChild(parent, node);
                if (complete != null) {
                    complete.accept(node);
                }
                ++size;
            }
        } else if (key.compareTo(parent.getKey()) > 0) {//大于关键字
            if (parent.getRight() != null) {
                node = insert(key, value, parent.getRight(), repeatable, progress, complete, after, supplier);
            } else {
                node = newNode(supplier, key, value);
                setRightChild(parent, node);
                if (complete != null) {
                    complete.accept(node);
                }
                ++size;
            }
        } else {//等于关键字
            parent.setData(value);
            node = parent;
            if (complete != null) {
                complete.accept(node);
            }
        }
        if (after != null) {
            after.accept(parent);
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
    protected void replace(Node<K, V> cur, Node<K, V> newNode, boolean inherit) {
        Node<K, V> parent = cur.getParent();//if root, parent is null
        if (cur != getRoot()) {
            if (parent.getRight() == cur) {
                setRightChild(parent, newNode);
            } else {
                setLeftChild(parent, newNode);
            }
        } else {
            setRoot(newNode);
            newNode.setParent(null);
        }
        if (inherit) {
            if (newNode != null) {
                setLeftChild(newNode, cur.getLeft());
                setRightChild(newNode, cur.getRight());
            }
        }
    }


    protected void setLeftChild(Node<K, V> node, Node<K, V> child) {
        node.setLeft(child);
        if (child != null) {
            child.setParent(node);
        }
    }

    protected void setRightChild(Node<K, V> node, Node<K, V> child) {
        node.setRight(child);
        if (child != null) {
            child.setParent(node);
        }
    }

    protected void setRoot(Node<K, V> root) {
        this.root = root;
    }

    protected Node<K, V> newNode(Supplier<Node<K, V>> supplier, K key, V value) {
        Node<K, V> node = supplier.get();
        node.setKey(key);
        node.setData(value);
        return node;
    }
}
