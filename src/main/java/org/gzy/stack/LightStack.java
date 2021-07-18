package org.gzy.stack;

import org.gzy.list.ILightList;
import org.gzy.list.arrayList.LightArrayList;

import java.io.Serializable;

/**
 * 栈
 * @author GaoZiYang
 * @since 2021年07月18日 12:37:15
 */
public class LightStack<E> implements ILightStack<E>, Cloneable, Serializable {
    /**
     * 使用组合关系关联动态数组
     */
    private ILightList<E> lightList = new LightArrayList<>();

    @Override
    public int size() {
        return lightList.size();
    }

    @Override
    public boolean isEmpty() {
        return lightList.isEmpty();
    }

    @Override
    public void push(E e) {
        lightList.add(e);
    }

    @Override
    public E pop() {
        return lightList.size() > 0 ? lightList.remove(lightList.size() - 1) : null;
    }

    @Override
    public E peek() {
        return lightList.size() > 0 ? lightList.get(lightList.size() - 1) : null;
    }

    @Override
    public void clear() {
        lightList.clear();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("|栈顶|\n");
        for (int i = lightList.size() - 1; i >= 0; i--) {
            sb.append("| " + lightList.get(i) + " |");
            sb.append("\n");
        }
        sb.append("|栈底|");
        return sb.toString();
    }
}
