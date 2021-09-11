package org.gzy.list.arrayList;

import org.gzy.list.AbstractLightList;
import org.gzy.list.ILightList;

import java.io.Serializable;
import java.util.*;

/**
 * 动态数组
 * @author GaoZiYang
 * @since 2021年07月14日 09:57:26
 */
@SuppressWarnings("unchecked")
public class LightArrayList<E> extends AbstractLightList<E> implements ILightList<E>, RandomAccess, Cloneable, Serializable {
    /**
     * 元素数组
     */
    private E[] elements;

    /**
     * 默认容量为10
     */
    private static final int DEFAULT_CAPACITY = 10;

    public LightArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public LightArrayList(int capacity) {
        capacity = capacity <= 0 ? DEFAULT_CAPACITY : capacity;
        elements = (E[]) new Object[capacity];
    }

    /**
     * 迭代器
     * @param <E> 元素类型
     */
    private class LightArrayListIterator<E> implements Iterator<E> {
        private int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor <= size - 1;
        }

        @Override
        public E next() {
            return (E) elements[cursor++];
        }

        @Override
        public void remove() {
            LightArrayList.this.remove(cursor);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new LightArrayListIterator<>();
    }

    @Override
    public void clear() {
        Arrays.fill(elements, null);
        size = 0;
    }

    /**
     * 扩容
     * @param minCapacity 扩容要求的最小容量
     */
    private void expand(int minCapacity) {
        if (elements.length >= minCapacity) return;
        int newLength = elements.length + (elements.length >> 1);
        elements = Arrays.copyOf(elements, newLength);
    }

    @Override
    public E get(int index) {
        rangeCheck(index);
        return elements[index];
    }

    @Override
    public E set(int index, E element) {
        rangeCheck(index);
        E oldElement = elements[index];
        elements[index] = element;
        return oldElement;
    }

    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        expand(size + 1);
        for (int i = size; i > index; i--) {
            elements[i] = elements[i - 1];
        }
        elements[index] = element;
        size++;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        E oldElement = (E) elements[index];
        for (; index < size - 1; index++) {
            elements[index] = elements[index + 1];
        }
        elements[--size] = null;
        return oldElement;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) return i;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(elements[i])) return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (elements[i] == null) return i;
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (o.equals(elements[i])) return i;
            }
        }
        return -1;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            if (i != 0) sb.append(",");
            sb.append(elements[i]);
        }
        sb.append("]");
        return sb.toString();
    }
}