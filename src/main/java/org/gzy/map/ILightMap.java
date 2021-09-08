package org.gzy.map;

import java.util.function.Consumer;

/**
 * 映射接口
 * @author GaoZiYang
 * @since 2021年08月31日 14:45:01
 */
public interface ILightMap<K, V> {
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
     * 清空元素
     */
    void clear();

    /**
     * 添加元素
     * @param key 元素的键
     * @param value 元素的值
     * @return 如果已存在该键，覆盖原有值并返回
     */
    V put(K key, V value);

    /**
     * 根据键获取元素值
     * @param key 元素的键
     * @return 元素的值
     */
    V get(K key);

    /**
     * 根据键移除元素
     * @param key 元素的键
     * @return 被移除元素的值
     */
    V remove(K key);

    /**
     * 判断是否包含指定的键
     * @param key 要判断是否包含的键
     * @return 如果包含就返回true，反之返回false
     */
    boolean containsKey(K key);

    /**
     * 判断是否包含指定的值
     * @param value 要判断是否包含的值
     * @return 如果包含就返回true，反之返回false
     */
    boolean containsValue(V value);
}
