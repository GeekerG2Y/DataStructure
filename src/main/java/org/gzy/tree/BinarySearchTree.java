package org.gzy.tree;

import org.gzy.queue.queue.LightQueue;

import java.util.Comparator;

/**
 * 二叉搜索树
 * @author GaoZiYang
 * @since 2021年08月21日 18:15:02
 */
@SuppressWarnings({"unchecked", "unused"})
public class BinarySearchTree<E> extends AbstractBinaryTree<E> {
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
            root = createNode(e, null);
            size++;

            afterAdd(root);
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

        Node<E> newNode = createNode(e, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;

        afterAdd(newNode);
    }

    /**
     * 空方法，添加后的操作，待子类来实现
     * @param node 添加的节点
     */
    protected void afterAdd(Node<E> node) {
        // 空方法
    }

    /**
     * 创建节点，子类可以重写该方法来实现创建子类自己的节点
     * @param e 节点元素
     * @param parent 父节点
     * @return 创建的节点
     */
    protected Node<E> createNode(E e, Node<E> parent) {
        return new Node<>(e, parent);
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
            // 找到后继节点
            Node<E> successor = successor(node);
            // 使用后继节点的值来替代原有值
            node.element = successor.element;
            // 删除后继节点，因为后继节点一定是度为0或1的节点，所以可以走下面的删除逻辑
            node = successor;
        }

        // 节点的度为1或0
        Node<E> replacement = node.left != null ? node.left : node.right;
        if (replacement != null) {
            // 节点的度为1的情况
            replacement.parent = node.parent;
            // 更改父节点的左右指针
            if (node.isLeftChild()) {
                node.parent.left = replacement;
            } else if (node.isRightChild()) {
                node.parent.right = replacement;
            } else {
                root = replacement;
            }

            afterRemove(replacement);
        } else if (node.parent == null) {
            // 节点是叶子节点（即度为0）并且同时也是根节点，等价于判断：node == root
            root = null;
        } else {
            // 节点是叶子节点（即度为0）但是有父节点
            if (node.isLeftChild()) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }

            afterRemove(node);
        }
    }

    /**
     * 空方法，删除后的操作，待子类来实现
     * @param node 删除的节点或者用以取代删除节点的节点
     */
    protected void afterRemove(Node<E> node) {
        // 空方法
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
