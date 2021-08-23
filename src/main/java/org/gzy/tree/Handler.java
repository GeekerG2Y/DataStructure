package org.gzy.tree;

/**
 * 二叉树遍历处理器
 * @author GaoZiYang
 * @since 2021年08月22日 20:08:56
 */
@FunctionalInterface
public interface Handler<E> {
    /**
     * 处理二叉树节点元素，并且判断是否要中止遍历
     * @param e 要处理的节点元素
     */
    void handle(E e);
}
