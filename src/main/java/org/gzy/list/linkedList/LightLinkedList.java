package org.gzy.list.linkedList;

import org.gzy.list.AbstractLightList;
import org.gzy.list.ILightList;

import java.io.Serializable;
import java.util.Iterator;

/**
 * 双向链表
 * @author GaoZiYang
 * @since 2021年07月14日 16:22:14
 */
@SuppressWarnings("unchecked")
public class LightLinkedList<E> extends AbstractLightList<E> implements ILightList<E>, Cloneable, Serializable {
    /**
     * 头节点
     */
    private Node<E> head;

    /**
     * 尾节点
     */
    private Node<E> tail;

    /**
     * 节点元素
     * @param <E> 所存储元素的类型
     */
    private static class Node<E> {
        /**
         * 实际存储的元素
         */
        private E element;

        /**
         * 关联的上一个节点
         */
        private Node<E> prev;

        /**
         * 关联的下一个节点
         */
        private Node<E> next;

        public Node(E e, Node<E> prev, Node<E> next) {
            this.element = e;
            this.prev = prev;
            this.next = next;
        }
    }

    /**
     * 根据索引获取指定位置上的节点
     * @param index 要获取节点的索引
     * @return 指定索引上的节点
     */
    private Node<E> node(int index) {
        Node<E> node;
        if (index <= (size >> 1)) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    /**
     * 迭代器
     * @param <E> 元素类型
     */
    private class LightLinkedListIterator<E> implements Iterator<E> {
        private final int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor <= size - 1;
        }

        @Override
        public E next() {
            return (E) node(cursor).element;
        }

        @Override
        public void remove() {
            LightLinkedList.this.remove(cursor);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new LightLinkedListIterator<>();
    }

    @Override
    public int indexOf(E e) {
        if (head == null) return -1;

        Node<E> node = head;
        if (e == null) {
            for (int i = 0; i < size; i++) {
                if (node.element == null) return i;
                node = node.next;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (node.element.equals(e)) return i;
                node = node.next;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(E e) {
        if (tail == null) return -1;

        Node<E> node = tail;
        if (e == null) {
            for (int i = size; i > 0; i--) {
                if (node.element == null) return i;
                node = node.prev;
            }
        } else {
            for (int i = size; i > 0; i--) {
                if (e.equals(node.element)) return i - 1;
                node = node.prev;
            }
        }
        return -1;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        rangeCheck(index);
        return node(index).element;
    }

    @Override
    public E set(int index, E e) {
        rangeCheck(index);
        Node<E> node = node(index);
        E oldElement = node.element;
        node.element = e;
        return oldElement;
    }

    @Override
    public void add(int index, E e) {
        rangeCheckForAdd(index);
        if (index == size) {
            Node<E> newNode = new Node<>(e, tail, null);
            if (tail == null) {
                head = newNode;
            } else {
                tail.next = newNode;
            }
            tail = newNode;
        } else {
            Node<E> oldNode = node(index);
            Node<E> prevNode = oldNode.prev;
            Node<E> newNode = new Node<>(e, prevNode, oldNode);
            oldNode.prev = newNode;
            if (prevNode == null) {
                head = newNode;
            } else {
                prevNode.next = newNode;
            }
        }
        size++;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        Node<E> oldNode = node(index);
        Node<E> prevNode = oldNode.prev;
        Node<E> nextNode = oldNode.next;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
        }
        size--;
        return oldNode.element;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<E> node = head;
        if (node != null) {
            sb.append("null <- ");
            for (int i = 0; i < size; i++) {
                if (i != 0) sb.append(" <-> ");
                sb.append("[");
                sb.append(node.prev == null ? "null" : node.prev.element);
                sb.append(",");
                sb.append(node.element);
                sb.append(",");
                sb.append(node.next == null ? "null" : node.next.element);
                sb.append("]");
                node = node.next;
            }
            sb.append(" -> null");
        } else {
            sb.append("null");
        }
        return sb.toString();
    }
}
