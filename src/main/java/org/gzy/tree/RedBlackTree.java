package org.gzy.tree;

import java.util.Comparator;

/**
 * 红黑树
 * @author GaoZiYang
 * @since 2021年08月27日 20:52:53
 */
@SuppressWarnings("unused")
public class RedBlackTree<E> extends BalanceBinarySearchTree<E> {
    public RedBlackTree() {
        super();
    }

    public RedBlackTree(Comparator<E> comparator) {
        super(comparator);
    }

    /**
     * 元素节点
     * @param <E> 元素类型
     */
    private static class RedBlackNode<E> extends Node<E> {
        /**
         * 节点颜色，默认为红色，如果添加的是根节点就将其染成黑色
         */
        private Color color = Color.RED;

        protected RedBlackNode(E e, Node<E> parent) {
            super(e, parent);
        }

        @Override
        public String toString() {
            return String.format("%s(%s)", element, color == Color.BLACK ? "B" : "R");
        }
    }

    @Override
    protected Node<E> createNode(E e, Node<E> parent) {
        return new RedBlackNode<>(e, parent);
    }

    @Override
    protected void afterAdd(Node<E> node) {
        // 如果添加元素的父节点为黑色时，无需任何处理
        Node<E> parent = node.parent;
        if (parent == null) {
            // 如果添加的元素为根节点或上溢至根节点，直接染成黑色即可
            color(node, Color.BLACK);
            return;
        }
        if (isBlack(parent)) return;

        Node<E> uncle = parent.sibling();
        Node<E> grandparent = color(parent.parent, Color.RED);
        // 如果叔叔节点为红色，将父节点和叔叔节点染为黑色
        if (isRed(uncle)) {
            color(parent, Color.BLACK);
            color(uncle, Color.BLACK);

            // 祖父节点需要向上合并（上溢），就类似于向上添加一个新节点，因此将其染成红色
            afterAdd(grandparent);
            return;
        }

        // 叔叔节点不是红色
        if (parent.isLeftChild()) {
            if (node.isLeftChild()) {
                // LL的情况
                color(parent, Color.BLACK);
                rightRotate(grandparent);
            } else {
                // LR的情况
                color(node, Color.BLACK);
                leftRotate(parent);
                rightRotate(grandparent);
            }
        } else {
            if (node.isRightChild()) {
                // RR的情况
                color(parent, Color.BLACK);
                leftRotate(grandparent);
            } else {
                // RL的情况
                color(node, Color.BLACK);
                rightRotate(parent);
                leftRotate(grandparent);
            }
        }
    }

    @Override
    protected void afterRemove(Node<E> node) {
        /*
            这里有两种情况：
            1.删除的是红色节点。
            2.删除的时度为1的黑色节点。
            这里将这两种情况做了一个统一处理，即使删除的是红色节点也将其进行染黑处理，而因为它是要被删除的，所以什么颜色对其无任何影响
         */
        if (isRed(node)) {
            // 如果用以替代的节点（即删除节点的子节点）是红色，直接将其染黑即可
            color(node, Color.BLACK);
            return;
        }

        // 删除的是度为0的黑色节点
        if (node.parent == null) {
            return;
        }
        /*
            判断被删除节点是左是右有两种情况：
            1.因为在执行该方法之前，删除节点父节点的子节点指针已经被清空了，所以通过isRightChild或isLeftChild方法是无法判断的
            2.如果是父节点发生下溢调用的次方法，那么它的子节点指针并不会被清空，所以要通过isRightChild或isLeftChild方法来判断
         */
        Node<E> parent = node.parent;
        boolean isNodeRight = parent.right == null || node.isRightChild();
        Node<E> sibling = isNodeRight ? parent.left : parent.right;
        // 被删除节点是左还是右的处理逻辑是对称的
        if (isNodeRight) {
            // 被删除的节点在右边，兄弟节点在左边的情况
            if (isRed(sibling)) {
                // 先处理兄弟节点为红色的情况，需要先将其转换为黑色（统一为黑色的情况）
                color(sibling, Color.BLACK);
                color(parent, Color.RED);
                rightRotate(parent);
                sibling = parent.left;
            }

            Color parentColor = isBlack(parent) ? Color.BLACK : Color.RED;
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点左右子节点均为黑色节点
                color(parent, Color.BLACK);
                color(sibling, Color.RED);
                if (parentColor == Color.BLACK) {
                    // 如果父节点也是黑色就会导致父节点也发生下溢，需要对递归调用该方法
                    afterRemove(parent);
                }
            } else {
                // 兄弟节点至少有一个红色子节点
                if (isBlack(sibling.left)) {
                    leftRotate(sibling);
                    // 兄弟节点发生改变了
                    sibling = parent.left;
                }

                color(sibling, parentColor);
                color(parent, Color.BLACK);
                color(sibling.left, Color.BLACK);
                rightRotate(parent);
            }
        } else {
            // 被删除的节点在左边，兄弟节点在右边的情况
            if (isRed(sibling)) {
                // 先处理兄弟节点为红色的情况，需要先将其转换为黑色（统一为黑色的情况）
                color(sibling, Color.BLACK);
                color(parent, Color.RED);
                leftRotate(parent);
                sibling = parent.right;
            }

            Color parentColor = isBlack(parent) ? Color.BLACK : Color.RED;
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点左右子节点均为黑色节点
                color(parent, Color.BLACK);
                color(sibling, Color.RED);
                if (parentColor == Color.BLACK) {
                    // 如果父节点也是黑色就会导致父节点也发生下溢，需要对递归调用该方法
                    afterRemove(parent);
                }
            } else {
                // 兄弟节点至少有一个红色子节点
                if (isBlack(sibling.right)) {
                    rightRotate(sibling);
                    // 兄弟节点发生改变了
                    sibling = parent.right;
                }

                color(sibling, parentColor);
                color(parent, Color.BLACK);
                color(sibling.right, Color.BLACK);
                leftRotate(parent);
            }
        }
    }

    /**
     * 为节点染色
     * @param node 要染色的节点
     * @param color 要设置的颜色
     * @return 染色后的节点
     */
    private Node<E> color(Node<E> node, Color color) {
       if (node == null) return null;

       ((RedBlackNode<E>) node).color = color;
       return node;
    }

    /**
     * 获取节点的颜色，如果为空默认为黑色
     * @param node 要获取颜色的节点
     * @return 颜色
     */
    private Color colorOf(Node<E> node) {
        return node == null ? Color.BLACK : ((RedBlackNode<E>) node).color;
    }

    /**
     * 判断是否为红色节点
     * @param node 要判断的节点
     * @return 是否为红色
     */
    private boolean isRed(Node<E> node) {
        return colorOf(node) == Color.RED;
    }

    /**
     * 判断是否为黑色节点
     * @param node 要判断的节点
     * @return 是否为黑色
     */
    private boolean isBlack(Node<E> node) {
        return colorOf(node) == Color.BLACK;
    }

    /**
     * 红黑树节点颜色
     */
    private enum Color {
        /**
         * 红色节点
         */
        RED,
        /**
         * 黑色节点
         */
        BLACK
    }
}
