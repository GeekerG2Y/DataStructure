package org.gzy.set;

import org.gzy.list.ILightList;
import org.gzy.list.linkedList.LightLinkedList;

import java.util.function.Consumer;

/**
 * 基于链表实现的集合
 * @author GaoZiYang
 * @since 2021年08月30日 21:44:08
 */
public class ListSet<E> implements ILightSet<E> {
    /**
     * 使用链表来存储元素
     */
    private final ILightList<E> list = new LightLinkedList<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean contains(E e) {
        return list.contains(e);
    }

    @Override
    public void add(E e) {
        int i = list.indexOf(e);
        if (i == -1) {
            list.add(e);
        } else {
            list.set(i, e);
        }
    }

    @Override
    public void remove(E e) {
        if (list.contains(e)) {
            list.remove(e);
        }
    }

    @Override
    public void traversal(Consumer<E> consumer) {
        if (consumer == null) {
            throw new IllegalArgumentException("遍历处理器不能为空！");
        }

        for (int i = 0; i < list.size(); i++) {
            consumer.accept(list.get(i));
        }
    }
}
