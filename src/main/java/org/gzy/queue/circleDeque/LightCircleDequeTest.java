package org.gzy.queue.circleDeque;

import org.gzy.queue.ILightDeque;

/**
 * @author GaoZiYang
 * @since 2021年07月18日 19:05:12
 */
public class LightCircleDequeTest {
    public static void main(String[] args) {
        ILightDeque<Integer> lightDeque = new LightCircleDeque<>();
        for (int i = 0; i < 10; i++) {
            lightDeque.offerHead(i + 1);
            lightDeque.offerTail(i + 100);
        }
        System.out.println(lightDeque);
    }
}
