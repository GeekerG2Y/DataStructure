package org.gzy.tree;

import java.util.Comparator;

/**
 * 平衡二叉搜索树
 * @author GaoZiYang
 * @since 2021年08月28日 21:38:33
 */
public abstract class BalanceBinarySearchTree<E> extends BinarySearchTree<E> {
    public BalanceBinarySearchTree() {
        super();
    }

    public BalanceBinarySearchTree(Comparator<E> comparator) {
        super(comparator);
    }

    /**
     * 将节点左旋转
     * @param node 要旋转的节点
     */
    protected void leftRotate(Node<E> node) {
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
    }

    /**
     * 将节点右旋转
     * @param node 要旋转的节点
     */
    protected void rightRotate(Node<E> node) {
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
    }
}
