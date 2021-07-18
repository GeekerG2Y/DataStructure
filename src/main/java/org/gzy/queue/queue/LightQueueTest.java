package org.gzy.queue.queue;

import org.gzy.queue.ILightQueue;

/**
 * @author GaoZiYang
 * @since 2021年07月18日 14:56:07
 */
public class LightQueueTest {
    public static void main(String[] args) {
        ILightQueue<Integer> lightQueue = new LightQueue<>();
        lightQueue.offer(1);
        lightQueue.offer(2);
        lightQueue.offer(3);
        lightQueue.offer(4);
        lightQueue.offer(5);
        lightQueue.offer(6);
        System.out.println(lightQueue);
    }
}
