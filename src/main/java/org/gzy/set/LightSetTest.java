package org.gzy.set;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

/**
 * @author GaoZiYang
 * @since 2021年08月30日 22:11:55
 */
public class LightSetTest {
    public static void main(String[] args) {
        ILightSet<Integer> hashSet = new LightHashSet<>();
        for (int i = 0; i < 10000; i++) {
            int element = new Random().nextInt(100);
            hashSet.add(element);
        }
        System.out.println(hashSet.size());
    }
}
