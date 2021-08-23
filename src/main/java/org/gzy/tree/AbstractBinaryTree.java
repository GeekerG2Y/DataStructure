package org.gzy.tree;

import org.gzy.queue.queue.LightQueue;
import org.gzy.tree.printer.BinaryTreeInfo;

/**
 * 二叉树的抽象类
 * @author GaoZiYang
 * @since 2021年08月23日 00:09:48
 */
@SuppressWarnings({"unchecked", "unused"})
public abstract class AbstractBinaryTree<E> implements BinaryTree, BinaryTreeInfo {
    /**
     * 元素数量
     */
    protected int size;
    /**
     * 根节点
     */
    protected Node<E> root;

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
        root = null;
        size = 0;
    }

    /**
     * 元素节点
     * @param <E> 元素类型
     */
    protected static class Node<E> {
        /**
         * 节点元素
         */
        protected E element;

        /**
         * 左节点
         */
        protected Node<E> left;

        /**
         * 右节点
         */
        protected Node<E> right;

        /**
         * 父节点
         */
        protected Node<E> parent;

        protected Node(E e, Node<E> parent) {
            this.element = e;
            this.parent = parent;
        }

        /**
         * 判断是否为叶子节点
         * @return 是否为叶子节点
         */
        protected boolean isLeaf() {
            return left == null && right == null;
        }
    }

    /**
     * 元素非空检测
     * @param e 要检测的元素
     */
    protected void checkElementNotNull(E e) {
        if (e == null) throw new IllegalArgumentException("元素不能为空！");
    }

    @Override
    public int height() {
        return height(root);
    }

    private int height(Node<E> node) {
        if (node == null) return 0;
        return Math.max(height(node.left), height(node.right)) + 1;
    }

    @Override
    public boolean isComplete() {
        if (root == null) return false;

        LightQueue<Node<E>> lightQueue = new LightQueue<>();
        lightQueue.offer(root);
        // 是否只能存在叶子节点
        boolean onlyLeaf = false;
        while (!lightQueue.isEmpty()) {
            Node<E> node = lightQueue.poll();
            // 判断后续的节点是否均为叶子节点
            if (onlyLeaf && !node.isLeaf()) {
                return false;
            }

            if (node.left != null) {
                lightQueue.offer(node);
            } else if (node.right != null) {
                return false;
            }

            if (node.right != null) {
                lightQueue.offer(node.right);
            } else {
                onlyLeaf = true;
            }
        }
        return true;
    }

    /**
     * 获取前驱节点
     * @return 前驱节点
     */
    protected Node<E> predecessor(Node<E> node) {
        if (node == null) return null;

        // 从左子树中寻找前驱节点
        Node<E> p = node.left;
        if (p != null) {
            while (p.right != null) {
                p = p.right;
            }
            return p;
        }

        // 从父节点中寻找前驱节点
        while (node.parent != null && node == node.parent.left) {
            node = node.parent;
        }
        return node.parent;
    }

    /**
     * 获取后继节点
     * @return 后继节点
     */
    protected Node<E> successor(Node<E> node) {
        if (node == null) return null;

        // 从左子树中寻找后继节点
        Node<E> p = node.right;
        if (p != null) {
            while (p.left != null) {
                p = p.left;
            }
            return p;
        }

        // 从父节点中寻找后继节点
        while (node.parent != null && node == node.parent.right) {
            node = node.parent;
        }
        return node.parent;
    }


    /**
     * 前序遍历
     */
    public void preorderTraversal(Handler<Node<E>> handler) {
        if (handler == null) {
            throw new IllegalArgumentException("遍历处理器不能为空！");
        }
        preorderTraversal(root, handler);
    }

    private void preorderTraversal(Node<E> node, Handler<Node<E>> handler) {
        if (node == null) return;

        handler.handle(node);
        preorderTraversal(node.left, handler);
        preorderTraversal(node.right, handler);
    }

    /**
     * 中序遍历
     */
    public void inorderTraversal(Handler<Node<E>> handler) {
        if (handler == null) {
            throw new IllegalArgumentException("遍历处理器不能为空！");
        }
        inorderTraversal(root, handler);
    }

    private void inorderTraversal(Node<E> node, Handler<Node<E>> handler) {
        if (node == null) return;

        inorderTraversal(node.left, handler);
        handler.handle(node);
        inorderTraversal(node.right, handler);
    }

    /**
     * 后序遍历
     */
    public void postorderTraversal(Handler<Node<E>> handler) {
        if (handler == null) {
            throw new IllegalArgumentException("遍历处理器不能为空！");
        }
        postorderTraversal(root, handler);
    }

    private void postorderTraversal(Node<E> node, Handler<Node<E>> handler) {
        if (node == null) return;

        postorderTraversal(node.left, handler);
        postorderTraversal(node.right, handler);
        handler.handle(node);
    }

    /**
     * 层序遍历
     */
    public void levelOrderTraversal(Handler<Node<E>> handler) {
        if (handler == null) {
            throw new IllegalArgumentException("遍历处理器不能为空！");
        }
        if (root == null) return;

        LightQueue<Node<E>> lightQueue = new LightQueue<>();
        lightQueue.offer(root);

        while (!lightQueue.isEmpty()) {
            Node<E> head = lightQueue.poll();
            Node<E> left = head.left;
            Node<E> right = head.right;
            if (left != null) {
                lightQueue.offer(left);
            }
            if (right != null) {
                lightQueue.offer(right);
            }
            handler.handle(head);
        }
    }

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>) node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>) node).right;
    }

    @Override
    public Object string(Object node) {
        return ((Node<E>) node).element;
    }
}
