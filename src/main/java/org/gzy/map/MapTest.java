package org.gzy.map;

import java.util.*;

/**
 * @author GaoZiYang
 * @since 2021年08月31日 19:34:43
 */
public class MapTest {
    public static void main(String[] args) {
        LightLinkedHashMap<String, Integer> map = new LightLinkedHashMap<>();
        map.put("Tom", new Random().nextInt(100));
        map.put("Rose", new Random().nextInt(100));
        map.put("Jack", new Random().nextInt(100));
        map.put("Jerry", new Random().nextInt(100));
        map.remove("Jack");
        map.traversal(System.out::println);
    }
}
