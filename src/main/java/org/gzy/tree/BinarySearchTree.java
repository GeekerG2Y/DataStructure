package org.gzy.tree;

import org.gzy.queue.queue.LightQueue;

import java.util.Comparator;

/**
 * 二叉搜索树
 * @author GaoZiYang
 * @since 2021年08月21日 18:15:02
 */
@SuppressWarnings({"unchecked", "unused"})
public class BinarySearchTree<E> extends AbstractBinaryTree<E> implements BinaryTree {
    /**
     * 元素比较器
     */
    private final Comparator<E> comparator;

    public BinarySearchTree() {
        this(null);
    }

    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    /**
     * 添加元素
     * @param e 要添加的元素
     */
    public void add(E e) {
        checkElementNotNull(e);

        // 添加第一个元素
        if (root == null) {
            root = new Node<>(e, null);
            size++;
            return;
        }

        // 添加非第一个元素
        Node<E> node = root;
        Node<E> parent = null;
        int cmp = 0;
        // 寻找插入元素的父节点
        while (node != null) {
            cmp = compare(e, node.element);
            parent = node;
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else {
                // 相等直接替换
                node.element = e;
                return;
            }
        }

        Node<E> newNode = new Node<>(e, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;
    }

    /**
     * 删除某个元素
     * @param e 要删除的元素
     */
    public void remove(E e) {
        remove(node(e));
    }

    /**
     * 删除某个节点
     * @param node 要删除的节点
     */
    private void remove(Node<E> node) {
        if (node == null) return;
        size--;

        // 节点的度为2
        if (node.left != null && node.right != null) {
            // 找到前驱节点
            Node<E> predecessor = predecessor(node);
            // 使用前驱节点
            node.element = predecessor.element;
            // 删除前驱节点，因为前驱节点一定是度为0或1的节点，所以可以走下面的删除逻辑
            node = predecessor;
        }

        // 节点的度为1或0
        Node<E> replacement = node.left != null ? node.left : node.right;
        if (replacement != null) { // 节点的度为1
            replacement.parent = node.parent;
            // 更改父节点的左右指针
            if (node.parent == null) {
                root = replacement;
            } else if (node.parent.left == node) {
                node.parent.left = replacement;
            } else {
                node.parent.right = replacement;
            }
            // 下面是度为0的情况
        } else if (node.parent == null) { // 节点是节点节点并且同时也是根节点，等价于判断：node == root
            root = null;
        } else { // 节点为叶子节点但是有父节点
            if (node.parent.left == node) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }
        }
    }

    /**
     * 判断某个元素是否存在
     * @param e 要判断的元素
     * @return 是否存在
     */
    public boolean contains(E e) {
        return node(e) != null;
    }

    /**
     * 根据元素查找对应的节点
     * @param e 要查找的元素
     * @return 元素节点
     */
    private Node<E> node(E e) {
        Node<E> node = root;
        while (node != null) {
            int cmp = compare(e, node.element);
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else {
                return node;
            }
        }
        return null;
    }

    /**
     * 元素比较
     * @param e1 第一个元素
     * @param e2 第二个元素
     * @return 比较结果，正数表示e1大于e2，负数表示e1小于e2，0表示相等
     */
    private int compare(E e1, E e2) {
        // 如果传入了自定义比较器，则使用它
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        // 否则元素类型必须使用Comparable接口
        return ((Comparable<E>) e1).compareTo(e2);
    }
}
