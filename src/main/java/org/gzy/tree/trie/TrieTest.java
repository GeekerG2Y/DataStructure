package org.gzy.tree.trie;

/**
 * @author GaoZiYang
 * @since 2021年09月13日 17:31:26
 */
public class TrieTest {
    public static void main(String[] args) {
        ITrie<Integer> trie = new Trie<>();
        trie.add("张三", 100);
        System.out.println(trie.startWith("三"));
        System.out.println(trie.remove("张三"));
        System.out.println(trie.get("张三"));
    }
}
