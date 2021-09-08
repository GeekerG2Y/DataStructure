package org.gzy.set;

import com.sun.istack.internal.Nullable;

import java.util.function.Consumer;

/**
 * 集合接口
 * @author GaoZiYang
 * @since 2021年08月30日 21:27:26
 */
public interface ILightSet<E> {
    /**
     * 获取元素个数
     * @return 元素个数
     */
    int size();

    /**
     * 判断集合是否为空
     * @return 如果为空就返回true，反之为false
     */
    boolean isEmpty();

    /**
     * 清空集合
     */
    void clear();

    /**
     * 判断集合中是否包含某个元素
     * @param e 判断包含的元素
     * @return 如果包含就返回true，反之为false
     */
    boolean contains(E e);

    /**
     * 添加元素
     * @param e 要添加的元素
     */
    void add(E e);

    /**
     * 删除元素
     * @param e 要删除的元素
     */
    void remove(E e);

    /**
     * 遍历集合
     * @param consumer 遍历操作
     */
    void traversal(@Nullable Consumer<E> consumer);
}
