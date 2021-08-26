package org.gzy.tree;

import org.gzy.tree.printer.BinaryTrees;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author GaoZiYang
 * @since 2021年08月22日 18:36:10
 */
public class BinarySearchTreeTest {
    public static void main(String[] args) {
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            data.add(new Random().nextInt(1000000));
        }

        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();
        for (int i = 0; i < data.size(); i++) {
            binarySearchTree.add(data.get(i));
        }
    }
}
