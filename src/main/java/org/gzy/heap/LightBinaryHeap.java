package org.gzy.heap;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.gzy.tree.printer.BinaryTreeInfo;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * 二叉堆
 * @author GaoZiYang
 * @since 2021年09月09日 15:05:14
 */
public class LightBinaryHeap<E> implements ILightHeap<E> {
    /**
     * 存放元素的数组
     */
    private E[] elements;
    /**
     * 元素数量
     */
    private int size;
    /**
     * 是否为最大堆，反之为最小堆
     */
    private boolean isMaxHeap = true;
    /**
     * 比较器
     */
    private final Comparator<E> comparator;

    /**
     * 数组默认大小
     */
    private static final int DEFAULT_CAPACITY = 10;

    public LightBinaryHeap() {
        this(DEFAULT_CAPACITY, null, null, true);
    }

    public LightBinaryHeap(boolean isMaxHeap) {
        this(DEFAULT_CAPACITY, null, null, isMaxHeap);
    }

    public LightBinaryHeap(@Nullable E[] elements) {
        this(DEFAULT_CAPACITY, elements, null, true);
    }

    public LightBinaryHeap(@Nullable Comparator<E> comparator) {
        this(DEFAULT_CAPACITY, null, comparator, true);
    }

    public LightBinaryHeap(@Nullable E[] elements, boolean isMaxHeap) {
        this(DEFAULT_CAPACITY, elements, null, isMaxHeap);
    }

    public LightBinaryHeap(@Nullable Comparator<E> comparator, boolean isMaxHeap) {
        this(DEFAULT_CAPACITY, null, comparator, isMaxHeap);
    }

    public LightBinaryHeap(@Nullable E[] elements, @Nullable Comparator<E> comparator) {
        this(DEFAULT_CAPACITY, elements, comparator, true);
    }

    public LightBinaryHeap(@Nullable E[] elements, @Nullable Comparator<E> comparator, boolean isMaxHeap) {
        this(DEFAULT_CAPACITY, elements, comparator, isMaxHeap);
    }

    public LightBinaryHeap(int capacity, @Nullable E[] elements, @Nullable Comparator<E> comparator, boolean isMaxHeap) {
        this.comparator = comparator;
        this.isMaxHeap = isMaxHeap;
        if (elements == null || elements.length == 0) {
            this.elements = (E[]) new Object[capacity];
        } else {
            this.elements = Arrays.copyOf(elements, size = elements.length);
            heapify();
        }
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
    public void clear() {
        Arrays.fill(elements, null);
        size = 0;
    }

    @Override
    public void add(E e) {
        checkNotNull(e);
        expand(size + 1);
        // 先将元素添加至数组末尾，然后再进行上滤
        elements[size++] = e;
        siftUp(size - 1);
    }

    @Override
    public E get() {
        // 堆顶元素就是索引为0的元素
        checkNotEmpty();
        return elements[0];
    }

    @Override
    public E remove() {
        checkNotEmpty();

        E removedValue = elements[0];
        // 用最后一个元素来替代堆顶元素，然后进行下滤
        elements[0] = elements[size - 1];
        elements[size - 1] = null;
        size--;
        siftDown(0);
        return removedValue;
    }

    @Override
    public E replace(E e) {
        checkNotNull(e);

        E removedValue = null;
        if (isEmpty()) {
            elements[0] = e;
            size++;
        } else {
            removedValue = elements[0];
            elements[0] = e;
            siftDown(0);
        }
        return removedValue;
    }

    /**
     * 将指定索引位置上的元素进行上滤
     * @param index 要上滤的元素的索引位置
     */
    private void siftUp(int index) {
        E own = elements[index];
        // 循环向上比较元素大小
        while (index > 0) {
            // 父节点编号：floor((i - 1) / 2)
            int parentIndex = (index - 1) >> 1;
            E parent = elements[parentIndex];
            if (compare(own, parent) <= 0) break;

            elements[index] = parent;
            // 重置为交换后的索引
            index = parentIndex;
        }
        elements[index] = own;
    }

    /**
     * 将指定索引位置上的元素进行下滤
     * @param index 要下滤的元素的索引位置
     */
    private void siftDown(int index) {
        E own = elements[index];
        /*
            下滤节点必须的有子节点，所以这里的条件是索引小于第一个叶子节点的索引，并且第一个叶子节点的索引就等于非叶子节点的数量
            叶子节点数量：floor((n + 1) / 2)或ceiling(n / 2)
            非叶子节点的数量：floor(n / 2)或ceiling((n - 1) / 2)
         */
        int firstLeafIndex = size >> 1;
        while (index < firstLeafIndex) {
            /*
                该节点只能有两种情况：
                1.左右都有子节点。
                2.只有左子节点。

                左右子节点索引公式：
                1.左子节点索引：2i + 1
                2.右子节点索引：2i + 2

                这里我们默认先取左节点！
             */
            int childIndex = (index << 1) + 1;
            E child = elements[childIndex];
            int rightChildIndex = childIndex + 1;
            if (rightChildIndex < size && compare(elements[rightChildIndex], child) > 0) {
                child = elements[childIndex = rightChildIndex];
            }
            // 如果子节点大于被删除节点就直接退出循环
            if (compare(own, child) >= 0) break;
            elements[index] = child;
            index = childIndex;
        }
        elements[index] = own;
    }

    /**
     * 批量建堆
     */
    private void heapify() {
        // 自下而上的下滤
        for (int i = (size >> 1) - 1; i >= 0; i--) {
            siftDown(i);
        }
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

    /**
     * 比较两个元素的大小
     * @param e1 要比较的第一个元素
     * @param e2 要比较的第二个元素
     * @return 如果返回正数表示e1大于e2，返回负数表示e1小于e2，0表示相等
     */
    private int compare(E e1, E e2) {
        return comparator != null ? comparator.compare(e1, e2) : isMaxHeap ? ((Comparable<E>) e1).compareTo(e2) : ((Comparable<E>) e2).compareTo(e1);
    }

    /**
     * 检查堆是否为空
     */
    private void checkNotEmpty() {
        if (size == 0) throw new IndexOutOfBoundsException("堆为空！");
    }

    /**
     * 检查元素是否为空
     * @param e 要检查的元素
     */
    private void checkNotNull(E e) {
        if (e == null) throw new IllegalArgumentException("元素不能为空！");
    }

    @Override
    public Object root() {
        return 0;
    }

    @Override
    public Object left(Object node) {
        int index = ((Integer) node << 1) + 1;
        return index > size - 1 ? null : index;
    }

    @Override
    public Object right(Object node) {
        int index = ((Integer) node << 1) + 2;
        return index > size - 1 ? null : index;
    }

    @Override
    public Object string(Object node) {
        return elements[(Integer) node];
    }
}
