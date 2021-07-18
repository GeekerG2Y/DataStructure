package org.gzy.queue;

/**
 * 双端队列
 * @author GaoZiYang
 * @since 2021年07月18日 16:03:01
 */
public interface ILightDeque<E> {
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
     * 从队列头部入队
     * @param e 要入队的元素
     */
    void offerHead(E e);

    /**
     * 从队列尾部入队
     * @param e 要入队的元素
     */
    void offerTail(E e);

    /**
     * 从队列头部出队
     * @return 出队的元素
     */
    E pollHead();

    /**
     * 从队列尾部出队
     * @return 出队的元素
     */
    E pollTail();

    /**
     * 获取队列头部的元素，但不会删除该元素
     * @return 队列头部的元素
     */
    E peekHead();

    /**
     * 获取队列尾部的元素，但不会删除该元素
     * @return 队列尾部的元素
     */
    E peekTail();

    /**
     * 清空队列
     */
    void clear();
}
