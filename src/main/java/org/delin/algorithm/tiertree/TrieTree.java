package org.delin.algorithm.tiertree;

public class TrieTree {
    private TrieTreeNode root;

    public TrieTree(TrieTreeNode root) {
        this.root = root;
    }

    public TrieTreeNode insert(String seq, char base) {
        root.setFrequency(root.getFrequency() + 1);
        return helpInsert(seq, root.getTrieTreeNodeBychOrCreate(seq.charAt(0), base), 0, base);
    }

    public TrieTreeNode findTireTreeNode(String seq, char base) {
        return helpFind(seq, root.getTrieTreeNodeBych(seq.charAt(0), base), 0, base);
    }

    public void tranverse() {
        helpTranverse(new StringBuilder(), root);
    }

    public void tranversePrefix(String prefix, TrieTreeNode node, char base) {
        helpTranverse(new StringBuilder(prefix.length() < 2 ? "" : prefix.substring(0, prefix.length() - 1)), node);
    }

    public void printAllPrefixAndFrequency() {
        helpPrintAllPrefixAndFrequency(new StringBuilder(), root);
    }


    private void helpPrintAllPrefixAndFrequency(StringBuilder str, TrieTreeNode node) {
        if (node != root) {
            str.append(node.getCh());
            System.out.println(str + ":" + node.getFrequency());
        }
        int cap = node.getCapacity();
        if (cap > 0) {
            TrieTreeNode[] children = node.getChildren();
            for (int i = 0; i < cap; ++i) {
                if (children[i] == null) {
                    continue;
                }
                helpPrintAllPrefixAndFrequency(new StringBuilder(str), children[i]);
            }
        }
    }

    public void helpTranverse(StringBuilder str, TrieTreeNode node) {
        if (node != root) {
            str.append(node.getCh());
            if (node.getEndCount() > 0) {
                System.out.println(str);
            }
        }
        int cap = node.getCapacity();
        if (cap > 0) {
            TrieTreeNode[] children = node.getChildren();
            for (int i = 0; i < cap; ++i) {
                if (children[i] == null) {
                    continue;
                }
                helpTranverse(new StringBuilder(str), children[i]);
            }
        }
    }

    private TrieTreeNode helpInsert(String seq, TrieTreeNode node, int i, char base) {
        node.setFrequency(node.getFrequency() + 1);
        if (i + 1 == seq.length()) {
            node.setEndCount(node.getEndCount() + 1);
            return node;
        }
        char ch = seq.charAt(i + 1);
        TrieTreeNode next = node.getTrieTreeNodeBychOrCreate(ch, base);
        return helpInsert(seq, next, i + 1, base);
    }

    private TrieTreeNode helpFind(String seq, TrieTreeNode node, int i, char base) {
        if (i + 1 == seq.length()) {
            return node;
        }
        char ch = seq.charAt(i + 1);
        TrieTreeNode next = node.getTrieTreeNodeBych(ch, base);
        if (next == null) {
            return null;
        }
        return helpFind(seq, next, i + 1, base);
    }
}
