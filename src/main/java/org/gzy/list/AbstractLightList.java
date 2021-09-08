package org.gzy.list;

/**
 * @author GaoZiYang
 * @since 2021年07月14日 17:13:29
 */
public abstract class AbstractLightList<E> implements ILightList<E> {
    /**
     * 元素数量
     */
    protected int size;

    protected AbstractLightList() { }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(E e) {
        return indexOf(e) != -1;
    }

    @Override
    public void add(E e) {
        add(size, e);
    }

    @Override
    public boolean remove(E e) {
        int index = indexOf(e);
        if (index == -1) return false;
        remove(index);
        return true;
    }

    protected void rangeCheck(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("索引越界，索引：" + index + "，数量：" + size);
    }

    protected void rangeCheckForAdd(int index) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("索引越界，索引：" + index + "，数量：" + size);
    }
}
