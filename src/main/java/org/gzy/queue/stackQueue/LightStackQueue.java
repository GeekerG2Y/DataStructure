package org.gzy.queue.stackQueue;

import org.gzy.queue.ILightQueue;
import org.gzy.stack.ILightStack;
import org.gzy.stack.LightStack;

/**
 * 用栈实现的队列
 * @author GaoZiYang
 * @since 2021年07月18日 15:04:57
 */
public class LightStackQueue<E> implements ILightQueue<E> {
    /**
     * 入队栈
     */
    private ILightStack<E> inStack = new LightStack<>();

    /**
     * 出队栈
     */
    private ILightStack<E> outStack = new LightStack<>();

    @Override
    public int size() {
        return inStack.size() + outStack.size();
    }

    @Override
    public boolean isEmpty() {
        return inStack.isEmpty() && outStack.isEmpty();
    }

    @Override
    public void offer(E e) {
        inStack.push(e);
    }

    @Override
    public E poll() {
        if (outStack.isEmpty()) {
            while (!inStack.isEmpty()) {
                outStack.push(inStack.pop());
            }
        }
        return outStack.pop();
    }

    @Override
    public E peek() {
        if (!outStack.isEmpty()) return outStack.peek();

        while (!inStack.isEmpty()) {
            outStack.push(inStack.pop());
        }
        return outStack.peek();
    }

    @Override
    public void clear() {
        inStack.clear();
        outStack.clear();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        ILightStack<E> stack = new LightStack<>();
        while (!outStack.isEmpty() || !inStack.isEmpty()) stack.push(poll());
        StringBuilder sb = new StringBuilder();
        sb.append("队尾 [");
        while (!stack.isEmpty()) {
            E e = stack.pop();
            outStack.push(e);
            sb.append(e);
            if (!stack.isEmpty()) sb.append(",");
        }
        sb.append("] 队头");

        while (!stack.isEmpty()) {
            outStack.push(stack.pop());
        }
        return sb.toString();
    }
}
