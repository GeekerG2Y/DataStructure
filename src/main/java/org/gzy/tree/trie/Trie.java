package org.gzy.tree.trie;

import com.sun.istack.internal.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Trie
 * @author GaoZiYang
 * @since 2021年09月13日 16:35:12
 */
public class Trie<V> implements ITrie<V> {
    /**
     * 元素数量
     */
    private int size;
    /**
     * 根节点
     */
    private final Node<V> root;

    public Trie() {
        root = new Node<>(null);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        size = 0;
        root.getChildren().clear();
    }

    @Override
    public boolean contains(String str) {
        return node(str) != null;
    }

    @Override
    public V add(String str, V value) {
        checkStr(str);

        Node<V> node = root;
        Node<V> childNode;
        // 遍历查找或添加字符，未找到某个字符就直接添加
        for (char c : str.toCharArray()) {
            childNode = node.getChildren().get(c);
            if (childNode == null) {
                childNode = new Node<>(node);
                childNode.character = c;
                node.getChildren().put(c, childNode);
            }
            node = childNode;
        }

        // 判断是否存在该字符串词语
        if (!node.isEnding) {
            node.isEnding = true;
            node.value = value;
            size++;
            return null;
        }

        // 表示找到了传入的字符串词语，并将旧值返回
        V oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public V get(String str) {
        Node<V> node = node(str);
        return node == null ? null : node.value;
    }

    @Override
    public V remove(String str) {
        Node<V> node = node(str);
        if (node == null || !node.isEnding) return null;
        size--;

        V oldValue = node.value;
        if (!node.getChildren().isEmpty()) {
            node.isEnding = true;
            node.value = null;
            return oldValue;
        }

        Node<V> parent;
        while ((parent = node.parent) != null) {
            parent.getChildren().remove(node.character);
            if (parent.isEnding || !parent.getChildren().isEmpty()) break;
            node = parent;
        }
        return oldValue;
    }

    @Override
    public boolean startWith(String prefix) {
        checkStr(prefix);

        Node<V> node = root;
        Node<V> childNode;
        for (char c : prefix.toCharArray()) {
            childNode = node.getChildren().get(c);
            // 如果某个字符没有子节点并且该字符也不是最后一个字符
            if (childNode == null && !node.isEnding) return false;
            node = childNode;
        }
        return true;
    }

    /**
     * 根据字符串查找对应的节点
     * @param str 要查找节点的字符串
     * @return 字符串对应的节点
     */
    private Node<V> node(String str) {
        checkStr(str);

        Node<V> node = root;
        for (char c : str.toCharArray()) {
            node = node.getChildren().get(c);
            if (node == null) return null;
        }
        return node.isEnding ? node : null;
    }

    /**
     * 检查字符串的合法性
     * @param str 要检查的字符串
     */
    private void checkStr(String str) {
        if (str == null || str.length() == 0) throw new IllegalArgumentException("字符串不能为空！");
    }

    /**
     * 元素节点
     * @param <V> 元素值的类型
     */
    private static class Node<V> {
        /**
         * 父节点
         */
        private Node<V> parent;

        /**
         * 子节点集合
         */
        private Map<Character, Node<V>> children;

        /**
         * 节点中存储的字符
         */
        private Character character;

        /**
         * 节点的值
         */
        private V value;

        /**
         * 是否为一个词语的结尾，如果为true表示它与前面的节点构成了一个完整词语
         */
        private boolean isEnding;

        private Node(@Nullable Node<V> parent) {
            this.parent = parent;
        }

        private Map<Character, Node<V>> getChildren() {
            return children == null ? (children = new HashMap<>()) : children;
        }
    }
}
