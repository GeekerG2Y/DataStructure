package org.gzy.queue.circleQueue;

/**
 * @author GaoZiYang
 * @since 2021年07月18日 17:48:50
 */
public class LightCircleQueueTest {
    public static void main(String[] args) {
        LightCircleQueue<Integer> lightCircleQueue = new LightCircleQueue<>();
        for (int i = 0; i < 10; i++) {
            lightCircleQueue.offer(i);
        }
        for (int i = 0; i < 5; i++) {
            lightCircleQueue.poll();
        }
        for (int i = 15; i < 35; i++) {
            lightCircleQueue.offer(i);
        }
        System.out.println(lightCircleQueue);
    }
}
