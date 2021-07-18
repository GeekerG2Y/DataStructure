package org.gzy.stack;

/**
 * @author GaoZiYang
 * @since 2021年07月18日 12:41:08
 */
public class LightStackTest {
    public static void main(String[] args) {
        ILightStack<Integer> lightStack = new LightStack<>();
        lightStack.push(1);
        lightStack.push(2);
        lightStack.push(3);
        lightStack.push(4);
        lightStack.push(5);
        System.out.println(lightStack);
    }
}
