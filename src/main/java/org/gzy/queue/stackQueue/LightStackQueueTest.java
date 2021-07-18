package org.gzy.queue.stackQueue;

import org.gzy.queue.ILightQueue;

/**
 * @author GaoZiYang
 * @since 2021年07月18日 15:11:24
 */
public class LightStackQueueTest {
    public static void main(String[] args) {
        ILightQueue<Integer> lightQueue = new LightStackQueue<>();
        lightQueue.offer(1);
        lightQueue.offer(2);
        lightQueue.offer(3);
        lightQueue.offer(4);
        lightQueue.offer(5);
        lightQueue.poll();
        lightQueue.poll();
        lightQueue.poll();
        System.out.println(lightQueue.peek());
        System.out.println(lightQueue.peek());
        System.out.println(lightQueue.peek());
        System.out.println(lightQueue);
    }
}
