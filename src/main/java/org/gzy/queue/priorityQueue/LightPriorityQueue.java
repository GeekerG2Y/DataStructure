package org.gzy.queue.priorityQueue;

import com.sun.istack.internal.Nullable;
import org.gzy.heap.ILightHeap;
import org.gzy.heap.LightBinaryHeap;
import org.gzy.queue.ILightQueue;

import java.util.Comparator;

/**
 * 优先级队列
 * @author GaoZiYang
 * @since 2021年09月12日 22:42:57
 */
public class LightPriorityQueue<E> implements ILightQueue<E> {
    /**
     * 底层使用二叉堆实现
     */
    ILightHeap<E> heap;

    public LightPriorityQueue() {
        this(null);
    }

    public LightPriorityQueue(@Nullable Comparator<E> comparator) {
        heap = new LightBinaryHeap<>(comparator);
    }

    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    @Override
    public void offer(E e) {
        heap.add(e);
    }

    @Override
    public E poll() {
        return heap.remove();
    }

    @Override
    public E peek() {
        return heap.get();
    }

    @Override
    public void clear() {
        heap.clear();
    }
}
