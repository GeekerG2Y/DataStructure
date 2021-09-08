package org.gzy.map;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * 基于哈希表实现并且有记录元素顺序的映射
 * @author GaoZiYang
 * @since 2021年09月08日 17:25:31
 */
public class LightLinkedHashMap<K, V> extends LightHashMap<K, V> {
    /**
     * 头节点
     */
    private LinkedNode<K, V> head;
    /**
     * 尾节点
     */
    private LinkedNode<K, V> tail;

    @Override
    public void clear() {
        super.clear();
        head = tail = null;
    }

    @Override
    protected Node<K, V> createNode(K key, V value, Node<K, V> parent) {
        LinkedNode<K, V> newNode = new LinkedNode<>(key, value, parent);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        return newNode;
    }

    @Override
    protected void swapBeforeRemove(Node<K, V> origin, Node<K, V> removedNode) {
        LinkedNode<K, V> originLinkedNode = (LinkedNode<K, V>) origin;
        LinkedNode<K, V> removedLinkedNode = (LinkedNode<K, V>) removedNode;

        // 在双向链表中交换两个节点
        // 交换prev
        LinkedNode<K, V> tempPrev = originLinkedNode.prev;
        originLinkedNode.prev = removedLinkedNode.prev;
        removedLinkedNode.prev = tempPrev;
        if (originLinkedNode.prev == null) {
            head = originLinkedNode;
        } else {
            originLinkedNode.prev.next = originLinkedNode;
        }
        if (removedLinkedNode.prev == null) {
            head = removedLinkedNode;
        } else {
            removedLinkedNode.prev.next = removedLinkedNode;
        }
        // 交换next
        LinkedNode<K, V> tempNext = originLinkedNode.next;
        originLinkedNode.next = removedLinkedNode.next;
        removedLinkedNode.next = tempNext;
        if (originLinkedNode.next == null) {
            tail = originLinkedNode;
        } else {
            originLinkedNode.next.prev = originLinkedNode;
        }
        if (removedLinkedNode.next == null) {
            tail = removedLinkedNode;
        } else {
            removedLinkedNode.next.prev = removedLinkedNode;
        }
    }

    @Override
    protected void afterRemove(Node<K, V> node) {
        LinkedNode<K, V> removedLinkedNode = (LinkedNode<K, V>) node;
        // 交换指针
        LinkedNode<K, V> prev = removedLinkedNode.prev, next = removedLinkedNode.next;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }
    }

    @Override
    public boolean containsValue(V value) {
        LinkedNode<K, V> node = head;
        while (node != null) {
            node = node.next;
            if (Objects.equals(node.value, value)) return true;
        }
        return false;
    }

    @Override
    public void traversal(Consumer<Node<K, V>> consumer) {
        LinkedNode<K, V> node = head;
        while (node != null) {
            consumer.accept(node);
            node = node.next;
        }
    }

    /**
     * 元素节点
     * <br/>带有指向前后节点的指针，用于记录元素节点的添加顺序
     * @param <K> 元素键的类型
     * @param <V> 元素值的类型
     */
    private static class LinkedNode<K, V> extends LightHashMap.Node<K, V> {
        /**
         * 上一个节点
         */
        private LinkedNode<K, V> prev;
        /**
         * 下一个节点
         */
        private LinkedNode<K, V> next;

        private LinkedNode(K key, V value, Node<K, V> parent) {
            super(key, value, parent);
        }

        private LinkedNode(K key, V value, Node<K, V> parent, int hash) {
            super(key, value, parent, hash);
        }

        private LinkedNode(K key, V value, Node<K, V> parent, LinkedNode<K, V> prev, LinkedNode<K, V> next) {
            super(key, value, parent);
            this.prev = prev;
            this.next = next;
        }
    }
}
