package org.delin.collection.tree;

import java.util.Random;

public class BalancedBinaryTreeTest {
    public static void main(String[] args) {
        BalancedBinaryTree<Integer, Integer> tree = new BalancedBinaryTree<>();
        int[] keys = {26, 83, 27, 28, 60, 89, 23, 79, 77, 65};
        for (int key : keys) {
            System.out.println("insert ========>" + key);
            tree.insertOrUpdate(key, key);
        }
        TreeUtils.midorder(tree.getRoot(), n -> {
            System.out.print(n + " ");
        });
        Random random = new Random();
        for (int i = 0; i < 10; ++i) {
            int key = random.nextInt(100);
            System.out.println("-------------->insert " + key);

            Node node = tree.insertOrUpdate(key, random.nextInt(100000));
            System.out.println("insert " + node + "==>" + node.getParent());
        }
        TreeUtils.midorder(tree.getRoot(), n -> {
            System.out.print(n + " ");
        });
        System.out.println();
    }
}
