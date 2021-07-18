package org.gzy.queue.circleQueue;

import org.gzy.queue.ILightQueue;

import java.util.Arrays;

/**
 * 循环队列
 * @author GaoZiYang
 * @since 2021年07月18日 17:35:03
 */
@SuppressWarnings("unchecked")
public class LightCircleQueue<E> implements ILightQueue<E> {
    /**
     * 元素数量
     */
    private int size;

    /**
     * 元素数组
     */
    private E[] elements;

    /**
     * 队头指针
     */
    private int cursor;

    /**
     * 默认容量为10
     */
    private static final int DEFAULT_CAPACITY = 10;

    public LightCircleQueue() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void offer(E e) {
        expand(size + 1);
        elements[index(size)] = e;
        size++;
    }

    @Override
    public E poll() {
        E element = elements[cursor];
        elements[cursor] = null;
        cursor = index(1);
        size--;
        return element;
    }

    @Override
    public E peek() {
        return elements[cursor];
    }

    @Override
    public void clear() {
        Arrays.fill(elements, null);
        cursor = 0;
        size = 0;
    }

    /**
     * 获取索引映射的真实索引
     * @param i 偏移后的索引，偏移量就是队头指针长度
     * @return 真实的索引
     */
    private int index(int i) {
        i += cursor;
        return i - (i > elements.length ? elements.length : 0);
    }

    /**
     * 扩容
     * @param minCapacity 扩容要求的最小容量
     */
    private void expand(int minCapacity) {
        if (elements.length >= minCapacity) return;
        int newLength = elements.length + (elements.length >> 1);
        E[] newElements = (E[]) new Object[newLength];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[index(i)];
        }
        elements = newElements;
        cursor = 0;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < elements.length; i++) {
            if (i != 0) sb.append(",");
            sb.append(elements[i]);
            if (i == cursor) sb.append("(队头)");
        }
        sb.append("]");
        return sb.toString();
    }
}
