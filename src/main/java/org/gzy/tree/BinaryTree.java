package org.gzy.tree;

/**
 * 二叉树
 * @author GaoZiYang
 * @since 2021年08月23日 00:05:55
 */
public interface BinaryTree {
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
     * 判断是否为完全二叉树
     * @return 是否为完全二叉树
     */
    boolean isComplete();

    /**
     * 获取树的高度
     * @return 高度
     */
    int height();
}
