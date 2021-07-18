package org.gzy.queue;

/**
 * 队列接口
 * @author GaoZiYang
 * @since 2021年07月18日 13:46:17
 */
public interface ILightQueue<E> {
    /**
     * 获取元素数量
     * @return 元素数量
     */
    int size();

    /**
     * 判断是否为空
     * @return 如果队列为空就返回true，否则返回false
     */
    boolean isEmpty();

    /**
     * 入队
     * @param e 要入队的元素
     */
    void offer(E e);

    /**
     * 出队
     * @return 出队的元素
     */
    E poll();

    /**
     * 获取队列头部的元素，但不会删除该元素
     * @return 队列头部的元素
     */
    E peek();

    /**
     * 清空队列
     */
    void clear();
}
