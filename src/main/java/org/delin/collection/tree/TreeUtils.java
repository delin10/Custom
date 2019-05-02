package org.delin.collection.tree;

import org.delin.designpattern.Visitor;

import java.util.*;

/**
 * 二叉树的工具
 * 遍历 访问者模式：先根、中根、后根
 * 判断树是否成环
 * @author delin
 */
public class TreeUtils {
    /**
     * 先根遍历结点
     * @param root
     * @param visitor
     * @param <K>
     * @param <V>
     */
    public static<K extends Comparable,V> void preorder(Node<K,V> root, Visitor visitor){
        if (root==null){
            return;
        }

        visitor.visit(root);
        preorder(root.getLeft(),visitor);
        preorder(root.getRight(),visitor);
    }

    /**
     * 中根遍历
     * @param root
     * @param visitor
     * @param <K>
     * @param <V>
     */
    public static<K extends Comparable,V> void midorder(Node<K,V> root, Visitor visitor){
        if (root==null){
            return;
        }

        midorder(root.getLeft(),visitor);
        visitor.visit(root);
        midorder(root.getRight(),visitor);
    }

    /**
     * 后根遍历
     * @param root
     * @param visitor
     * @param <K>
     * @param <V>
     */
    public static<K extends Comparable,V> void postorder(Node<K,V> root, Visitor visitor){
        if (root==null){
            return;
        }

        postorder(root.getLeft(),visitor);
        postorder(root.getRight(),visitor);
        visitor.visit(root);
    }

    /**
     * 判断树是否有环
     * @param root
     * @param set
     * @param <K>
     * @param <V>
     * @return
     */
    public static<K extends Comparable,V> boolean hasCircle(Node<K,V> root, Set<Object> set){
        if (root==null){
            return false;
        }
        if (!set.add(root)){
            return true;
        }
        return hasCircle(root.getLeft(),set) || hasCircle(root.getRight(),set);
    }
}
