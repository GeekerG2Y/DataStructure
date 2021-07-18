package org.gzy.list.linkedList;

import org.gzy.list.ILightList;

/**
 * @author GaoZiYang
 * @since 2021年07月14日 22:35:26
 */
public class LinkedListTest {
    public static void main(String[] args) {
        ILightList<Integer> list = new LightLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(3);
        list.add(2);
        list.add(5);
        System.out.println(list);
    }
}
