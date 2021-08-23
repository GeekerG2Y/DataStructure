package org.gzy.tree;

import org.gzy.tree.printer.BinaryTrees;

/**
 * @author GaoZiYang
 * @since 2021年08月22日 18:36:10
 */
public class BinarySearchTreeTest {
    public static void main(String[] args) {
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();
        binarySearchTree.add(7);
        binarySearchTree.add(4);
        binarySearchTree.add(9);
        binarySearchTree.add(2);
        binarySearchTree.add(5);
        binarySearchTree.add(6);
        binarySearchTree.add(8);
        binarySearchTree.add(11);
        binarySearchTree.add(3);
        binarySearchTree.add(12);
        binarySearchTree.add(1);
        BinaryTrees.println(binarySearchTree);
        binarySearchTree.remove(7);
        BinaryTrees.println(binarySearchTree);
    }
}
