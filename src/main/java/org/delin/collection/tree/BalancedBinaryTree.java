package org.delin.collection.tree;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class BalancedBinaryTree<K extends Comparable, V> extends BinarySortTree<K, V> {
    private BalancedTreeNode<K, V> root;

    @Override
    public BalancedTreeNode<K, V> getRoot() {
        //System.out.println("BalancedBinaryTree.getRoot");
        return this.root;
    }

    @Override
    protected void setRoot(Node<K, V> root) {
        this.root = (BalancedTreeNode<K, V>) root;
    }

    @Override
    public Node<K, V> insertOrUpdate(K key, V value) {
        Consumer<Node<K, V>> progress = node -> {
            BalancedTreeNode<K, V> balancedTreeNode = (BalancedTreeNode<K, V>) node;

        };

        Consumer<Node<K, V>> complete = node -> {
            BalancedTreeNode<K, V> balancedTreeNode = (BalancedTreeNode<K, V>) node;
            if (node.getLeft() == null || node.getRight() == null) {
                if (balancedTreeNode != getRoot()) {
                    balancedTreeNode.getParent().inc();
                }
            }
        };

        Consumer<Node<K, V>> after = node -> {
            BalancedTreeNode<K, V> balancedTreeNode = (BalancedTreeNode<K, V>) node;
            //balancedTreeNode.inc();//路径上的结点高度都加一 不对！！！树的高度不是这样计算的！！
            System.out.println("before reset " + balancedTreeNode);
            resetBalanceFactor(balancedTreeNode, true);
            rebalance(balancedTreeNode);//只需要rebalance一次，之后全部reset即可
            System.out.println("after reset " + balancedTreeNode);
        };

        Supplier<Node<K, V>> supplier = () -> new BalancedTreeNode<>();

        return insert(key, value, getRoot(), false, progress, complete, after, supplier);
    }

    /**
     * 右旋
     *
     * @param node
     */
    protected Node<K, V> rightRotate(Node<K, V> node) {
        System.out.println("rightRotate " + node);
        Node<K, V> left = node.getLeft();
        replace(node, left, false);
        setLeftChild(node, left.getRight());
        setRightChild(left, node);
        if (node == root) {
            replaceRoot(left);
        }
        return left;
    }

    /**
     * 右旋
     *
     * @param node
     */
    protected Node<K, V> leftRotate(Node<K, V> node) {
        System.out.println("leftRotate " + node);
        Node<K, V> right = node.getRight();
        replace(node, right, false);//忘记替换该结点
        setRightChild(node, right.getLeft());
        setLeftChild(right, node);
        if (node == root) {
            replaceRoot(right);
        }
        return right;
    }

    /**
     * 右-左旋转
     *
     * @param node
     * @return
     */
    protected Node<K, V> rightAndLeftRotate(Node<K, V> node) {
        System.out.println("rightAndLeftRotate");
        System.out.println("node " + node);
        Node<K, V> vertex = rightRotate(node.getRight());
        System.out.println("vertex " + vertex);
        System.out.println("vertex left " + vertex.getLeft());
        System.out.println("vertex right " + vertex.getRight());
        System.out.println("vertex parent " + vertex.getParent());
        return leftRotate(vertex.getParent());
    }

    /**
     * 左-右旋转
     *
     * @param node
     * @return
     */
    protected Node<K, V> leftAndRightRotate(Node<K, V> node) {
        System.out.println("leftAndRightRotate");
        System.out.println("node " + node);
        Node<K, V> vertex = leftRotate(node.getLeft());
        System.out.println("vertex " + vertex);
        System.out.println("vertex left " + vertex.getLeft());
        System.out.println("vertex right " + vertex.getRight());
        return rightRotate(vertex.getParent());
    }

    protected Node<K, V> rebalance(BalancedTreeNode<K, V> node) {
        int nDeff = getHeightDifference(node);
        System.out.println("call rebalance:");
        System.out.println("deff=" + nDeff + "," + node);
        if (nDeff > 1) {
            int lDeff = getHeightDifference(node.getLeft());
            if (lDeff > 0) {
                BalancedTreeNode<K, V> n = (BalancedTreeNode<K, V>) rightRotate(node);
                resetBalanceFactor(n.getRight(), false);
                resetBalanceFactor(n, false);
                return n;
            } else {
                BalancedTreeNode<K, V> n = (BalancedTreeNode<K, V>) leftAndRightRotate(node);
                resetBalanceFactor(n.getLeft(), false);
                resetBalanceFactor(n.getRight(), false);
                resetBalanceFactor(n, false);
                return n;
            }
        } else if (nDeff < -1) {
            int rDeff = getHeightDifference(node.getRight());
            if (rDeff > 0) {
                BalancedTreeNode<K, V> n = (BalancedTreeNode<K, V>) rightAndLeftRotate(node);
                resetBalanceFactor(n.getLeft(), false);
                resetBalanceFactor(n.getRight(), false);
                resetBalanceFactor(n, false);
                return n;
            } else {
                BalancedTreeNode<K, V> n = (BalancedTreeNode<K, V>) leftRotate(node);
                resetBalanceFactor(n.getLeft(), false);//顺序相当重要
                resetBalanceFactor(n, false);
                return n;
            }
        }
        return null;
    }

    /**
     * 替换根结点
     *
     * @param node
     */
    protected void replaceRoot(Node<K, V> node) {
        root = (BalancedTreeNode<K, V>) node;
        node.setParent(null);
    }

    protected int getHeightDifference(BalancedTreeNode<K, V> node) {
        System.out.println("getHeightDifference");
        System.out.println(node.getLeft());
        System.out.println(node.getRight());
        int leftHeight = node == null ? 0 : node.getLeft() == null ? 0 : node.getLeft().getBalanceFactor();
        int rightHeight = node == null ? 0 : node.getRight() == null ? 0 : node.getRight().getBalanceFactor();
        return leftHeight - rightHeight;
    }

    protected int resetBalanceFactor(BalancedTreeNode<K, V> node, boolean insert) {
        int l = 0, r = 0;
        if (node.getLeft() != null) {
            l = node.getLeft().getBalanceFactor();
        }

        if (node.getRight() != null) {
            r = node.getRight().getBalanceFactor();
        }
        //如果是插入结点，则可能会存在高度增加的情况
        //如果父节点要小于等于儿子结点，则高度增加
        if (insert) {
            if (node.getBalanceFactor() == Math.max(l, r)) {
                node.inc();
            } else if (node.getBalanceFactor() - Math.max(l, r) == 2) {
                node.dec();
            }
            return node.getBalanceFactor();
        }

        //左旋和右旋不会涉及结点的增删
        //但左右结点会变化，意味着高度可能发生改变
        node.setBalanceFactor(Math.max(l, r) + 1);
        return node.getBalanceFactor();
    }
}
