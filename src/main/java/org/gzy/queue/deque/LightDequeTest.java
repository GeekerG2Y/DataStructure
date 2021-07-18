package org.gzy.queue.deque;

import org.gzy.queue.ILightDeque;

/**
 * @author GaoZiYang
 * @since 2021年07月18日 16:13:37
 */
public class LightDequeTest {
    public static void main(String[] args) {
        ILightDeque<Integer> lightDeque = new LightDeque<>();
        System.out.println(lightDeque.peekHead());
        lightDeque.offerHead(1);
        lightDeque.offerTail(2);
        lightDeque.offerTail(3);
        lightDeque.offerHead(4);
        System.out.println(lightDeque);
    }
}
