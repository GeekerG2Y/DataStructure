package org.gzy.list.arrayList;

import org.gzy.list.ILightList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author GaoZiYang
 * @since 2021年07月14日 10:53:57
 */
public class ArrayListTest {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(1);
        list.add(3);
        list.add(3);
        list.add(3);
        list.remove(3);
        System.out.println(list);
        ILightList<Integer> lightArrayList = new LightArrayList<>();
        lightArrayList.add(1);
        lightArrayList.add(2);
        lightArrayList.add(3);
        lightArrayList.add(4);
        lightArrayList.add(5);
        lightArrayList.add(6);
        lightArrayList.add(7);
        lightArrayList.add(8);
        lightArrayList.add(9);
        lightArrayList.add(10);
        lightArrayList.add(11);
        Iterator<Integer> iterator = lightArrayList.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            System.out.println(next);
        }
        System.out.println(lightArrayList);
    }
}
