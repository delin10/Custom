package org.delin.collection.tree;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class BalancedBinaryTree<K extends Comparable, V> extends BinarySortTree<K, V> {
    private BalancedTreeNode<K, V> root;

    public void transverse(){
        TreeUtils.midorder(getRoot(), n -> {
            System.out.print(n + " ");
        });
        System.out.println();
    }

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
    public Node<K, V> deleteFirst(K key) {
        Node<K, V> node = search(key);
        if (node == null) {
            return null;
        }

        if (node.getLeft() == null) {
            replace(node, node.getRight(), false);
            rebalance((BalancedTreeNode<K, V>) node.getParent());//因为少了子结点，可能导致不平衡
        } else if (node.getRight() == null) {
            replace(node, node.getLeft(), false);
            rebalance((BalancedTreeNode<K, V>) node.getParent());//因为少了子结点，可能导致不平衡
        } else {// if left and right not null
            Node<K, V> precursor = searchProcessor(node.getLeft());//find the precursor
            BalancedTreeNode precursorParent=(BalancedTreeNode<K, V>) precursor.getParent();//最开始的父结点
            //不继承子结点
            replace(precursor, precursor.getLeft(), false);//replace precursor with its left node
            resetBalanceFactor(precursorParent,false);//相当于删除了一个结点，重新计算父结点的平衡因子
            replace(node, precursor, true);//replace this node with precursor
            resetBalanceFactor((BalancedTreeNode<K, V>) precursor,false);//插入了新的位置，需要重新计算平衡因子
            BalancedTreeNode<K, V> parent = (BalancedTreeNode<K, V>) precursorParent.getParent();
            // 这种情况可以当作插入一个新的结点
            while (parent != null) {//因删除precursor导致其原来父链上的所有结点需要重新计算
                resetBalanceFactor(parent, true);
                parent = parent.getParent();
            }
        }
        --size;
        return node;
    }

    private Node<K, V> helpDeleteSearch(K key, Node<K, V> node) {
        if (node == null) {
            return null;
        }
        if (key == null) {
            throw new NullPointerException("The search key cannot be null");
        }
        if (node.getKey().compareTo(key) > 0) {
            Node<K, V> n = helpDeleteSearch(key, node.getLeft());
            resetBalanceFactor((BalancedTreeNode<K, V>) node, true);
            return n;
        } else if (node.getKey().compareTo(key) < 0) {
            Node<K, V> n = helpDeleteSearch(key, node.getRight());
            resetBalanceFactor((BalancedTreeNode<K, V>) node, true);
            return n;
        } else {
            //如果是根结点，不影响任何父结点
            if (node != getRoot()) {
                // 把当前结点设置为null
                replace(node, null, false);//node的左右子树
                // 重新计算平衡因子
                resetBalanceFactor((BalancedTreeNode<K, V>) node.getParent(), true);
            }
            return node;
        }
    }

    private Node<K, V> searchProcessor(Node<K, V> node) {
        if (node.getRight() == null) {
            return node;
        }
        Node<K, V> tNode = searchProcessor(node.getRight());
        resetBalanceFactor((BalancedTreeNode<K, V>) tNode, true);
        return tNode;
    }

    @Override
    public Node<K, V> insertOrUpdate(K key, V value) {
        Consumer<Node<K, V>> progress = node -> {
            BalancedTreeNode<K, V> balancedTreeNode = (BalancedTreeNode<K, V>) node;
        };

        Consumer<Node<K, V>> complete = node -> {
            BalancedTreeNode<K, V> balancedTreeNode = (BalancedTreeNode<K, V>) node;
            if (node.getLeft() == null || node.getRight() == null) {
                //插入完成，只需要判断父亲的左右结点是否存在其中一个为空
                //若存在，说明，父结点的子树高度加一
                if (balancedTreeNode != getRoot()) {
                    //插入root结点不会影响任何父结点，因为其父结点为空
                    balancedTreeNode.getParent().inc();
                }
            }
        };

        Consumer<Node<K, V>> after = node -> {
            BalancedTreeNode<K, V> balancedTreeNode = (BalancedTreeNode<K, V>) node;
            //balancedTreeNode.inc();//路径上的结点高度都加一 不对！！！树的高度不是这样计算的！！
            System.out.println("before reset " + balancedTreeNode);
            resetBalanceFactor(balancedTreeNode, true);//结点插入后需要进行一次平衡因子的重置
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
        setRightChild(node, right.getLeft());
        setLeftChild(right, node);
        if (node == root) {
            replaceRoot(right);
        }else{
            replace(node, right, false);//忘记替换该结点
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

    /**
     * 重新平衡子树
     * 注意平衡因子的改变
     * @param node
     * @return
     */
    protected Node<K, V> rebalance(BalancedTreeNode<K, V> node) {
        int nDeff = getHeightDifference(node);
        if (nDeff > 1) {//左子树比右子树高
            int lDeff = getHeightDifference(node.getLeft());
            if (lDeff > 0) {//左子树的左子树比较高
                BalancedTreeNode<K, V> n = (BalancedTreeNode<K, V>) rightRotate(node);
                resetBalanceFactor(n.getRight(), false);
                resetBalanceFactor(n, false);//需要先平衡子结点
                return n;
            } else {//左子树的右子树比价高
                BalancedTreeNode<K, V> n = (BalancedTreeNode<K, V>) leftAndRightRotate(node);
                resetBalanceFactor(n.getLeft(), false);
                resetBalanceFactor(n.getRight(), false);
                resetBalanceFactor(n, false);//需要先平衡子结点
                return n;
            }
        } else if (nDeff < -1) {//右子树比较高
            int rDeff = getHeightDifference(node.getRight());
            if (rDeff > 0) {//右子树的左子树比较高
                BalancedTreeNode<K, V> n = (BalancedTreeNode<K, V>) rightAndLeftRotate(node);
                resetBalanceFactor(n.getLeft(), false);//需要先平衡子结点
                resetBalanceFactor(n.getRight(), false);
                resetBalanceFactor(n, false);//再平衡父结点
                return n;
            } else {//右子树的右子树比较高
                BalancedTreeNode<K, V> n = (BalancedTreeNode<K, V>) leftRotate(node);
                resetBalanceFactor(n.getLeft(), false);//顺序相当重要
                resetBalanceFactor(n, false);////需要先平衡子结点
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
    }

    /**
     * 获得左右子树高度的差值
     * @param node
     * @return
     */
    protected int getHeightDifference(BalancedTreeNode<K, V> node) {
        int leftHeight = node == null ? 0 : node.getLeft() == null ? 0 : node.getLeft().getBalanceFactor();
        int rightHeight = node == null ? 0 : node.getRight() == null ? 0 : node.getRight().getBalanceFactor();
        return leftHeight - rightHeight;
    }

    /**
     * 参考左右子树的值重新设置node结点的平衡因子
     * @param node
     * @param insertOrDelete 是否新增或者删除了结点
     * @return
     */
    protected int resetBalanceFactor(BalancedTreeNode<K, V> node, boolean insertOrDelete) {
        int l = 0, r = 0;
        if (node.getLeft() != null) {
            l = node.getLeft().getBalanceFactor();
        }

        if (node.getRight() != null) {
            r = node.getRight().getBalanceFactor();
        }
        //如果是插入结点，则可能会存在高度增加的情况
        //如果父节点要小于等于儿子结点，则高度增加
        if (insertOrDelete) {
            //插入结点的情况
            if (node.getBalanceFactor() == Math.max(l, r)) {
                node.inc();
                //删除的情况下
            } else if (node.getBalanceFactor() - Math.max(l, r) == 2) {//正常条件下父子结点差值为1，但是差值达到了2说明父亲所在的树高度降低了
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
