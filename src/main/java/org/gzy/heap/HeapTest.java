package org.gzy.heap;

import org.gzy.tree.printer.BinaryTrees;

/**
 * @author GaoZiYang
 * @since 2021年09月09日 16:49:30
 */
public class HeapTest {
    public static void main(String[] args) {
        Integer[] data = {88, 44, 53, 41, 16, 6, 70, 18, 85, 98, 81, 23, 36, 43, 37};
        ILightHeap<Integer> heap = new LightBinaryHeap<>(data, false);
        BinaryTrees.println(heap);
    }
}
