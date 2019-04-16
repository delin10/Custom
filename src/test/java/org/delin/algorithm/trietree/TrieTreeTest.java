package org.delin.algorithm.trietree;

import org.delin.algorithm.tiertree.TrieTree;
import org.delin.algorithm.tiertree.TrieTreeNode;
import org.junit.Test;
import xin.xihc.utils.common.FileUtil;

import java.io.File;

public class TrieTreeTest {
    @Test
    public void test() throws Exception{
        TrieTreeNode root=new TrieTreeNode(26);
        TrieTree tree=new TrieTree(root);
        String text=FileUtil.readFileToStr(new File("E:/test/trietree/test.txt"));
        String[] words=text.split("[^a-zA-Z]+");
        char base='a';
        for (String word:words){
            tree.insert(word.toLowerCase(),base);
        }
        tree.tranverse();
        System.out.println("findTrieTreeNode==========");
        String prefix="tr";
        TrieTreeNode result=tree.findTireTreeNode(prefix,base);
        tree.tranversePrefix(prefix,result,base);
    }
}
