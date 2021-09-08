package org.gzy.set;

import com.sun.istack.internal.Nullable;
import org.gzy.tree.BinaryTree;
import org.gzy.tree.RedBlackTree;

import java.util.function.Consumer;

/**
 * 基于 {@link org.gzy.tree.RedBlackTree} 实现的集合
 * @author GaoZiYang
 * @since 2021年08月30日 22:15:45
 */
public class LightTreeSet<E> implements ILightSet<E> {
    private final RedBlackTree<E> tree = new RedBlackTree<>();

    @Override
    public int size() {
        return tree.size();
    }

    @Override
    public boolean isEmpty() {
        return tree.isEmpty();
    }

    @Override
    public void clear() {
        tree.clear();
    }

    @Override
    public boolean contains(E e) {
        return tree.contains(e);
    }

    @Override
    public void add(E e) {
        tree.add(e);
    }

    @Override
    public void remove(E e) {
        tree.remove(e);
    }

    @Override
    public void traversal(@Nullable Consumer<E> consumer) {
        // 使用中序遍历（从小到大遍历）
        tree.inorderTraversal(consumer);
    }
}
