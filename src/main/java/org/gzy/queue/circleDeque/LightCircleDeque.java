package org.gzy.queue.circleDeque;

import org.gzy.queue.ILightDeque;

import java.util.Arrays;

/**
 * 循环双端队列
 * @author GaoZiYang
 * @since 2021年07月18日 18:28:32
 */
@SuppressWarnings("unchecked")
public class LightCircleDeque<E> implements ILightDeque<E> {
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

    public LightCircleDeque() {
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
    public void offerHead(E e) {
        expand(size + 1);
        cursor = index(-1);
        elements[cursor] = e;
        size++;
    }

    @Override
    public void offerTail(E e) {
        expand(size + 1);
        elements[index(size)] = e;
        size++;
    }

    @Override
    public E pollHead() {
        E element = elements[cursor];
        elements[cursor] = null;
        // 相当于队头指针向后移动一位
        cursor = index(1);
        size--;
        return element;
    }

    @Override
    public E pollTail() {
        // 获取队尾元素的真实索引
        int index = index(size - 1);
        E oldElement = elements[index];
        elements[index] = null;
        size--;
        return oldElement;
    }

    @Override
    public E peekHead() {
        return elements[cursor];
    }

    @Override
    public E peekTail() {
        return elements[index(size - 1)];
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
        // 真实索引计算
        i += cursor;
        // 如果计算后的真实索引为负数，则需要将该索引调到数组的末端
        if (i < 0) {
            return i + elements.length;
        }
        // 取模确保计算得到的真实索引一直都在数组索引区间之内
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
            if (i == index(size - 1)) sb.append("(队尾)");
        }
        sb.append("]");
        return sb.toString();
    }
}
