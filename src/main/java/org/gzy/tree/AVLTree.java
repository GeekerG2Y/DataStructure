package org.gzy.tree;

import java.util.Comparator;

/**
 * 平衡二叉搜索树
 * @author GaoZiYang
 * @since 2021年08月23日 21:45:16
 */
@SuppressWarnings({"unused"})
public class AVLTree<E> extends BinarySearchTree<E> implements BinaryTree {
    /**
     * 元素比较器
     */
    private final Comparator<E> comparator;

    public AVLTree() {
        this(null);
    }

    public AVLTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    @Override
    protected void afterAdd(Node<E> node) {
        // 一直顺着节点的父节点向上遍历节点，判断是否平衡，如果平衡就增加节点高度，反之则进行重新平衡操作
        while ((node = node.parent) != null) {
            if (isBalanced(node)) {
                // 如果节点是平衡的，每次都更新它的高度
                updateHeight(node);
            } else {
                // 只需找到高度最低的不平衡的节点，将其平衡后整个树所有节点都将平衡
                reBalance(node);
                break;
            }
        }
    }

    @Override
    protected void afterRemove(Node<E> node) {
        // 一直顺着节点的父节点向上遍历节点，判断是否平衡，如果平衡就增加节点高度，反之则进行重新平衡操作
        while ((node = node.parent) != null) {
            if (isBalanced(node)) {
                // 如果节点是平衡的，每次都更新它的高度
                updateHeight(node);
            } else {
                // 只需找到高度最低的不平衡的节点，将其平衡后整个树所有节点都将平衡
                reBalance(node);
            }
        }
    }

    @Override
    protected Node<E> createNode(E e, Node<E> parent) {
        return new AVLNode<>(e, parent);
    }

    /**
     * 重新平衡
     * @param grandparent 要重新平衡的节点
     */
    private void reBalance(Node<E> grandparent) {
        Node<E> parent = ((AVLNode<E>) grandparent).tallerChild();
        Node<E> node = ((AVLNode<E>) parent).tallerChild();

        if (parent.isLeftChild()) {
            // 左左的情况（LL）
            if (node.isLeftChild()) {
                rightRotate(grandparent);
            } else {
                /*
                  左右的情况（LR）
                  先对parent节点进行左旋转，变为左左的情况，然后再对grandparent进行右旋转
                 */
                leftRotate(parent);
                rightRotate(grandparent);
            }
        } else {
            // 右右的情况（RR）
            if (node.isRightChild()) {
                leftRotate(grandparent);
            } else {
                /*
                  右左的情况（RL）
                  先对parent节点进行右旋转，变为右右的情况，然后再对grandparent进行左旋转
                 */
                rightRotate(parent);
                leftRotate(grandparent);
            }
        }
    }

    /**
     * 将节点左旋转
     * @param node 要旋转的节点
     */
    private void leftRotate(Node<E> node) {
        Node<E> p = node.right;
        // 更新父节点
        p.parent = node.parent;
        if (node.isLeftChild()) {
            node.parent.left = p;
        } else if (node.isRightChild()){
            node.parent.right = p;
        } else {
            // node为根节点
            root = p;
        }
        node.parent = p;
        if (p.left != null) {
            p.left.parent = node;
        }

        // 更改左右子节点
        node.right = p.left;
        p.left = node;

        // 更新高度
        updateHeight(node);
        updateHeight(node.parent);
    }

    /**
     * 将节点右旋转
     * @param node 要旋转的节点
     */
    private void rightRotate(Node<E> node) {
        Node<E> p = node.left;
        // 更新父节点
        p.parent = node.parent;
        if (node.isLeftChild()) {
            node.parent.left = p;
        } else if (node.isRightChild()) {
            node.parent.right = p;
        } else {
            // node为根节点
            root = p;
        }
        node.parent = p;
        if (p.right != null) {
            p.right.parent = node;
        }

        // 更改左右子节点
        node.left = p.right;
        p.right = node;

        // 更新高度
        updateHeight(node);
        updateHeight(node.parent);
    }

    /**
     * 更新某个节点的高度
     * @param node 要更新高度的节点
     */
    private void updateHeight(Node<E> node) {
        ((AVLNode<E>) node).updateHeight();
    }

    /**
     * 判断节点是否平衡
     * <br/>AVL树的节点平衡的条件：平衡因子小于等于1
     * @param node 要判断的节点
     * @return 是否平衡
     */
    private boolean isBalanced(Node<E> node) {
        return Math.abs(((AVLNode<E>) node).balanceFactor()) <= 1;
    }

    private static class AVLNode<E> extends Node<E> {
        /**
         * 节点的高度
         * <br/>每次创建一个新节点，其高度默认值就是1
         */
        private int height = 1;

        private AVLNode(E e, Node<E> parent) {
            super(e, parent);
        }

        /**
         * 获取平衡因子
         * <br/>平衡因子：左右子树的高度差
         * @return 平衡因子
         */
        private int balanceFactor() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            return leftHeight - rightHeight;
        }

        /**
         * 更新自己的高度
         * <br/>节点的高度就是左右节点高度最大值再加1
         */
        private void updateHeight() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            height = Math.max(leftHeight, rightHeight) + 1;
        }

        /**
         * 获取当前节点高度更高的左子节点或右子节点
         * @return 左右子节点
         */
        private Node<E> tallerChild() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            return leftHeight >= rightHeight ? left : right;
        }
    }
}
