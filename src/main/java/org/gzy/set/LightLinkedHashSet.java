package org.gzy.set;

import com.sun.istack.internal.Nullable;
import org.gzy.map.LightHashMap;
import org.gzy.map.LightLinkedHashMap;

import java.util.function.Consumer;

/**
 * 基于 {@link org.gzy.map.LightLinkedHashMap} 实现的Set
 * @author GaoZiYang
 * @since 2021年09月08日 21:41:41
 */
public class LightLinkedHashSet<E> implements ILightSet<E> {
    private final LightLinkedHashMap<E, Object> map = new LightLinkedHashMap<>();

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean contains(E e) {
        return map.containsKey(e);
    }

    @Override
    public void add(E e) {
        map.put(e, null);
    }

    @Override
    public void remove(E e) {
        map.remove(e);
    }

    @Override
    public void traversal(@Nullable Consumer<E> consumer) {
        map.traversal(node -> consumer.accept(node.getKey()));
    }
}
