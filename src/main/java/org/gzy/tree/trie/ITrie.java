package org.gzy.tree.trie;

import org.gzy.tree.BinaryTree;

/**
 * Trie接口
 * @author GaoZiYang
 * @since 2021年09月13日 16:28:23
 */
public interface ITrie<V> {
    /**
     * 获取元素个数
     * @return 元素个数
     */
    int size();

    /**
     * 判断是否Trie为空
     * @return 如果Trie中没有元素则返回true，反之返回false
     */
    boolean isEmpty();

    /**
     * 清空Trie
     */
    void clear();

    /**
     * 判断是否包含某个字符串
     * @param str 要判断是否包含的字符串
     * @return 如果包含则返回true，反之返回false
     */
    boolean contains(String str);

    /**
     * 添加字符串元素
     * @param str 要添加元素的字符串
     * @param value 要添加元素的值
     * @return 添加的字符串元素的值
     */
    V add(String str, V value);

    /**
     * 根据字符串查找其对应的值
     * @param str 要查找值的字符串
     * @return 字符串对应的值
     */
    V get(String str);

    /**
     * 移除字符串
     * @param str 要移除的字符串
     * @return 移除的字符串元素的值
     */
     V remove(String str);

    /**
     * 判断是否有以某个前缀开头的字符串
     * @param prefix 要判断的字符串子前缀
     * @return 如果包含则返回true，反之返回false
     */
    boolean startWith(String prefix);
}
