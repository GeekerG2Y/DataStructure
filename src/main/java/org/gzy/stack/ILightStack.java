package org.gzy.stack;

/**
 * 栈接口
 * @author GaoZiYang
 * @since 2021年07月18日 12:23:25
 */
public interface ILightStack<E> {
    /**
     * 获取元素数量
     * @return 元素数量
     */
    int size();

    /**
     * 判断是否为空
     * @return 如果元素数量为空返回true，否则返回false
     */
    boolean isEmpty();

    /**
     * 从栈顶压入元素
     * @param e 要压入栈的元素
     */
    void push(E e);

    /**
     * 从栈顶弹出元素
     * @return 要弹出的元素
     */
    E pop();

    /**
     * 获取栈顶元素
     * @return 栈顶元素
     */
    E peek();

    /**
     * 清空所有元素
     */
    void clear();
}
