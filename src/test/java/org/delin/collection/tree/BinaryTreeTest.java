package org.delin.collection.tree;

import java.util.Random;

/**
 * 思考
 * Q1：
 * 当结点只有一个时的删除
 * yeah, it works ok!
 *
 *
 * @author delin 2019-05-02
 */
public class BinaryTreeTest {
    public static void main(String[] args) throws Exception {
        BinarySortTree<Integer, Integer> tree = new BinarySortTree<>();
        tree.insertOrUpdate(1,1);
        TreeUtils.midorder(tree.getRoot(), node -> {
            System.out.print(node + " ");
        });
        tree.deleteFirst(1);
        TreeUtils.midorder(tree.getRoot(), node -> {
            System.out.print(node + " ");
        });
        Random random = new Random();
        for (int i = 0; i < 100; ++i) {
            Node node = tree.insertOrUpdate(random.nextInt(100), random.nextInt(100000));
            System.out.println(node + "==>" + node.getParent());
        }

        TreeUtils.midorder(tree.getRoot(), node -> {
            System.out.print(node + " ");
        });

        for (int i = 0; i < 100; ++i) {
            Node node = tree.deleteFirst(random.nextInt(100));
            if (node != null) {
                System.out.println("delete " + node);
            }
        }

        System.out.println();
        for (int i = 0; i < 100; ++i) {
            tree.insertRepeatablily(10, 10);
        }

        for (int i = 0; i < 100; ++i) {
            int key = random.nextInt(100);
            System.out.println("delete " + key);
            System.out.println("delete " + tree.deleteAll(key));
        }
        TreeUtils.midorder(tree.getRoot(), node -> {
            System.out.print(node + " ");
        });
    }
}
