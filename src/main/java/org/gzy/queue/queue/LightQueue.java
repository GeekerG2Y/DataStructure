package org.gzy.queue.queue;

import org.gzy.list.ILightList;
import org.gzy.list.linkedList.LightLinkedList;
import org.gzy.queue.ILightQueue;

import java.io.Serializable;

/**
 * 队列
 * @author GaoZiYang
 * @since 2021年07月18日 13:48:56
 */
public class LightQueue<E> implements ILightQueue<E>, Cloneable, Serializable {
    private ILightList<E> lightList = new LightLinkedList<>();

    @Override
    public int size() {
        return lightList.size();
    }

    @Override
    public boolean isEmpty() {
        return lightList.isEmpty();
    }

    @Override
    public void offer(E e) {
        lightList.add(0, e);
    }

    @Override
    public E poll() {
        return lightList.remove(lightList.size() - 1);
    }

    @Override
    public E peek() {
        return lightList.get(lightList.size() - 1);
    }

    @Override
    public void clear() {
        lightList.clear();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("队尾 [");
        for (int i = 0; i < lightList.size(); i++) {
            if (i != 0) sb.append(",");
            sb.append(lightList.get(i));
        }
        sb.append("] 队头");
        return sb.toString();
    }
}
