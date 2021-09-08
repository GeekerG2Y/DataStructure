package org.gzy.list;

import java.util.Iterator;

/**
 * List集合接口
 * @author GaoZiYang
 * @since 2021年07月14日 14:49:41
 */
public interface ILightList<E> {
    /**
     * 获取集合中元素数量
     * @return 元素数量
     */
    int size();

    /**
     * 集合是否为空
     * @return 如果为空则返回true，否则返回false
     */
    boolean isEmpty();

    /**
     * 判断集合中是否包含某个元素
     * @param e 要判断的元素
     * @return 如果包含就返回true，否则返回false
     */
    boolean contains(E e);

    /**
     * 获取迭代器
     * @return 迭代器实例
     */
    Iterator<E> iterator();

    /**
     * 添加元素，可以添加null
     * @param e 新添加的元素
     */
    void add(E e);

    /**
     * 移除某个元素
     * @param e 要移除的元素
     * @return 如果移除成功就返回true，否则返回false
     */
    boolean remove(E e);

    /**
     * 获取集合中指定元素第一次出现时的索引
     * @param e 要搜索的元素
     * @return 第一次出现时的索引
     */
    int indexOf(E e);

    /**
     * 获取集合中指定元素最后一次出现时的索引
     * @param e 要搜索的元素
     * @return 最后一次出现时的索引
     */
    int lastIndexOf(E e);

    /**
     * 清空集合
     */
    void clear();

    /**
     * 获取集合中指定索引上的元素
     * @param index 要获取的索引
     * @return 指定索引上的元素
     */
    E get(int index);

    /**
     * 设置集合中指定索引上的元素
     * @param index 要设置的索引
     * @param e 新元素
     * @return 旧元素
     */
    E set(int index, E e);

    /**
     * 在集合中指定索引上添加元素，可以添加null
     * @param index 要添加元素的索引
     * @param e 新添加的元素
     */
    void add(int index, E e);

    /**
     * 移除集合中指定索引上的元素
     * @param index 要移除元素的索引
     * @return 移除的元素
     */
    E remove(int index);
}
