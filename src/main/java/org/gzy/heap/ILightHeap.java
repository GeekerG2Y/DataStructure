package org.gzy.heap;

import org.gzy.tree.printer.BinaryTreeInfo;

/**
 * 堆接口
 * @author GaoZiYang
 * @since 2021年09月09日 11:53:53
 */
public interface ILightHeap<E> extends BinaryTreeInfo {
    /**
     * 获取元素个数
     * @return 元素个数
     */
    int size();

    /**
     * 判断是否为空
     * @return 是否为空
     */
    boolean isEmpty();

    /**
     * 清空二叉树
     */
    void clear();

    /**
     * 添加元素
     * @param e 要添加的元素
     */
    void add(E e);

    /**
     * 获取堆顶元素（可能是最大值也可能是最小值）
     * @return 堆顶元素
     */
    E get();

    /**
     * 删除堆顶元素
     * @return 被删除的堆顶元素
     */
    E remove();

    /**
     * 删除堆顶元素，同时插入一个新元素
     * @param e 要插入的新元素
     * @return 被删除的堆顶元素
     */
    E replace(E e);
}
