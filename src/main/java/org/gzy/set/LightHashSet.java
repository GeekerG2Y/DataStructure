package org.gzy.set;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.gzy.map.ILightMap;
import org.gzy.map.LightHashMap;

import java.util.function.Consumer;

/**
 * 基于 {@link org.gzy.map.LightHashMap} 实现的Set
 * @author GaoZiYang
 * @since 2021年09月08日 21:24:02
 */
public class LightHashSet<E> implements ILightSet<E> {
    private final LightHashMap<E, Object> map = new LightHashMap<>();

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
