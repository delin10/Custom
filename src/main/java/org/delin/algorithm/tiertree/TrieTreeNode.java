package org.delin.algorithm.tiertree;

public class TrieTreeNode {
    private char ch;
    private int frequency;//经过这个结点的序列的数量
    private int endCount;//这个结点结束的序列数量
    private TrieTreeNode[] children;
    private int capacity;
    private int size;

    public TrieTreeNode(int capacity) {
        children = new TrieTreeNode[capacity];
        this.capacity = capacity;
    }

    public TrieTreeNode(int capacity, char ch) {
        children = new TrieTreeNode[capacity];
        this.capacity = capacity;
        this.ch = ch;
    }

    public TrieTreeNode getTrieTreeNodeBych(char ch, char base) {
        return children[ch - base];
    }

    public TrieTreeNode getTrieTreeNodeBychOrCreate(char ch, char base) {
        TrieTreeNode result = getTrieTreeNodeBych(ch, base);
        if (result != null) {
            return result;
        }
        return addTrieTreeNode(ch, base);
    }

    public boolean isChildren(TrieTreeNode node, char base) {
        int index = node.getCh() - base;
        return children[index] == node;
    }

    private TrieTreeNode addTrieTreeNode(char ch, char base) {
        int index = ch - base;
        children[index] = new TrieTreeNode(capacity, ch);
        return children[index];
    }

    public char getCh() {
        return ch;
    }

    public void setCh(char ch) {
        this.ch = ch;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getEndCount() {
        return endCount;
    }

    public void setEndCount(int endCount) {
        this.endCount = endCount;
    }

    public TrieTreeNode[] getChildren() {
        return children;
    }

    public void setChildren(TrieTreeNode[] children) {
        this.children = children;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSize() {
        return size;
    }
}
