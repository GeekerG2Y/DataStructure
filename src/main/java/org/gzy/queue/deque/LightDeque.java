package org.gzy.queue.deque;

import org.gzy.list.ILightList;
import org.gzy.list.linkedList.LightLinkedList;
import org.gzy.queue.ILightDeque;

import java.io.Serializable;

/**
 * @author GaoZiYang
 * @since 2021年07月18日 16:08:25
 */
public class LightDeque<E> implements ILightDeque<E>, Cloneable, Serializable {
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
    public void offerHead(E e) {
        lightList.add(e);
    }

    @Override
    public void offerTail(E e) {
        lightList.add(0, e);
    }

    @Override
    public E pollHead() {
        return lightList.remove(lightList.size() - 1);
    }

    @Override
    public E pollTail() {
        return lightList.remove(0);
    }

    @Override
    public E peekHead() {
        return lightList.get(lightList.size() - 1);
    }

    @Override
    public E peekTail() {
        return lightList.get(0);
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
