package org.gzy.map;

import com.sun.istack.internal.Nullable;
import org.gzy.queue.ILightQueue;
import org.gzy.queue.queue.LightQueue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 基于哈希表实现的映射
 * @author GaoZiYang
 * @since 2021年09月01日 16:06:34
 */
@SuppressWarnings("all")
public class LightHashMap<K, V> implements ILightMap<K, V> {
    /**
     * 元素个数
     */
    private int size;
    /**
     * 元素比较器
     */
    private final Comparator<K> comparator;
    /**
     * 红黑树节点的链表
     */
    private Node<K, V>[] table;
    /**
     * 容量
     */
    private int capacity;
    /**
     * 负载因子
     * <br/>公式：节点总数量/哈希桶数组长度
     */
    private float loadFactor;

    /**
     * 默认容量
     */
    private static final int DEFAULT_CAPACITY = 16;
    /**
     * 默认负载因子
     */
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    public LightHashMap() {
        this(DEFAULT_CAPACITY, null);
    }

    public LightHashMap(int capacity, Comparator<K> comparator) {
        this(capacity, DEFAULT_LOAD_FACTOR, comparator);
    }

    public LightHashMap(int capacity, float loadFactor, Comparator<K> comparator) {
        this.comparator = comparator;
        this.loadFactor = loadFactor;
        this.table = new Node[capacity];
    }

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
        if (size == 0) return;
        size = 0;
        Arrays.fill(table, null);
    }

    @Override
    public V put(K key, V value) {
        resize();

        int index = index(key);
        Node<K, V> root = table[index];
        if (root == null) {
            root = createNode(key, value, null);
            table[index] = root;
            size++;

            fixAfterPut(root);
            return value;
        }

        // 发生哈希冲突，添加新节点到红黑树中
        Node<K, V> node = root;
        Node<K, V> parent;
        int cmp;
        int h1 = hash(key);
        // 判断该节点是否已经遍历整棵树搜索过，如果遍历整棵树都没找到，那么后续就无需再遍历了
        boolean isSearched = false;
        // 寻找插入元素的父节点
        do {
            parent = node;
            Node<K, V> result;
            K k2 = node.key;
            int h2 = node.hash;
            // 这里大量的if-else判断主要是为了通过一套规则来决定不同元素的节点走向，通过哈希值可以快速判断其走向（查找时同理）
            if (h1 > h2) {
                cmp = 1;
            } else if (h1 < h2) {
                cmp = -1;
            } else if (Objects.equals(key, k2)){
                cmp = 0;
            } else if (key != null && k2 != null
                    && (key.getClass() == k2.getClass())
                    && key instanceof Comparable
                    && (cmp = ((Comparable<K>) key).compareTo(k2)) != 0) {
                // 如果compareTo的结果为0，不代表它们相等，这里不做任何操作
            } else if (!isSearched) {
                if (node.left != null && ((result = node(node.left.key)) != null)
                        || node.right != null && ((result = node(node.right.key)) != null)) {
                    node = result;
                    cmp = 0;
                } else {
                    // 走到这里表示node节点的左右子树中都未找到相同的节点，因此只能根据内存地址来比较，还需要把isSearched标识置为true
                    isSearched = true;
                    cmp = System.identityHashCode(key) - System.identityHashCode(k2);
                }
            } else {
                cmp = System.identityHashCode(key) - System.identityHashCode(k2);
            }

            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else {
                // 相等直接替换并返回原有值
                V origin = node.value;
                node.key = key;
                node.value = value;
                return origin;
            }
        } while (node != null);

        Node<K, V> newNode = createNode(key, value, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;

        fixAfterPut(newNode);
        return null;
    }

    @Override
    public V get(K key) {
        Node<K, V> node = node(key);
        return node == null ? null : node.value;
    }

    @Override
    public V remove(K key) {
        return remove(node(key));
    }

    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        if (size == 0) return false;

        // 对每个索引上的红黑树进行层序遍历查找值
        ILightQueue<Node<K, V>> queue = new LightQueue<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) continue;

            queue.offer(table[i]);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if (Objects.equals(node.value, value)) return true;

                Node<K, V> left = node.left, right = node.right;
                if (left != null) {
                    queue.offer(left);
                }
                if (right != null) {
                    queue.offer(right);
                }
            }
        }
        return false;
    }

    /**
     * 遍历所有元素（从小到大）
     * @param consumer 遍历每个元素时的操作
     */
    public void traversal(@Nullable Consumer<Node<K, V>> consumer) {
        for (int i = 0; i < table.length; i++) {
            Node<K, V> root = table[i];
            if (root == null) continue;

            // 使用层序遍历保证遍历顺序是从小到大
            LightQueue<Node<K, V>> queue = new LightQueue<>();
            queue.offer(root);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                if (consumer != null) consumer.accept(node);
            }
        }
    }

    /**
     * 根据元素的键查找对应的节点
     * @param key 要查找的元素的键
     * @return 元素节点
     */
    protected Node<K, V> node(K key) {
        // 从根节点开始比较
        Node<K, V> node = table[index(key)];

        // 查找结果
        Node<K, V> result;
        int h1 = hash(key);
        int cmp;
        while (node != null) {
            K k2 = node.key;
            int h2 = hash(k2);
            if (h1 > h2) {
                node = node.right;
            } else if (h1 < h2) {
                node = node.left;
            } else if (Objects.equals(key, k2)) {
                return node;
            } else if (key != null && k2 != null
                    && (key.getClass() == k2.getClass())
                    && key instanceof Comparable
                    && (cmp = ((Comparable<K>) key).compareTo(k2)) != 0) {
                node = cmp > 0 ? node.right : node.left;
            } else if (node.left != null && ((result = node(node.left.key)) != null)) { // 哈希值相等并且不具备可比较性，所以只能扫描左右子树
                return result;
            } else {
                node = node.right;
            }
        }
        return null;
    }

    /**
     * 扩容
     */
    private void resize() {
        if (((float) size / table.length) <= loadFactor) return;

        // 扩容为原来的两倍
        Node<K, V>[] oldTable = table;
        table = new Node[oldTable.length << 1];

        ILightQueue<Node<K, V>> queue = new LightQueue<>();
        for (int i = 0; i < oldTable.length; i++) {
            if (oldTable[i] == null) continue;

            queue.offer(oldTable[i]);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();

                Node<K, V> left = node.left, right = node.right;
                if (left != null) {
                    queue.offer(left);
                }
                if (right != null) {
                    queue.offer(right);
                }

                // 移动节点
                move(node);
            }
        }
    }

    /**
     * 将节点移动到新索引位置上
     * @param newNode 要移动的节点
     */
    private void move(Node<K, V> newNode) {
        K key = newNode.key;
        V value = newNode.value;
        // 清理节点的旧关系，并且设置新节点的颜色为红色
        newNode.parent = newNode.left = newNode.right = null;
        newNode.color = Color.RED;

        int index = index(key);
        Node<K, V> root = table[index];
        if (root == null) {
            root = newNode;
            table[index] = root;
            fixAfterPut(root);
            return;
        }

        // 发生哈希冲突，添加新节点到红黑树中
        Node<K, V> node = root;
        Node<K, V> parent;
        int cmp;
        int h1 = hash(key);
        // 判断该节点是否已经遍历整棵树搜索过，如果遍历整棵树都没找到，那么后续就无需再遍历了
        boolean isSearched = false;
        // 寻找插入元素的父节点
        do {
            parent = node;
            Node<K, V> result;
            K k2 = node.key;
            int h2 = node.hash;
            // 这里大量的if-else判断主要是为了通过一套规则来决定不同元素的节点走向，通过哈希值可以快速判断其走向（查找时同理）
            if (h1 > h2) {
                cmp = 1;
            } else if (h1 < h2) {
                cmp = -1;
            } else if (key != null && k2 != null
                    && (key.getClass() == k2.getClass())
                    && key instanceof Comparable
                    && (cmp = ((Comparable<K>) key).compareTo(k2)) != 0) {
                // 如果compareTo的结果为0，不代表它们相等，这里不做任何操作
            } else {
                cmp = System.identityHashCode(key) - System.identityHashCode(k2);
            }

            if (cmp > 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        } while (node != null);

        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        newNode.parent = parent;

        fixAfterPut(newNode);
    }

    /**
     * 创建元素节点
     * @param key 元素的键
     * @param value 元素的值
     * @param parent 父节点
     * @return 元素节点
     */
    protected Node<K, V> createNode(K key, V value, Node<K, V> parent) {
        return new Node<>(key, value, parent, key == null ? 0 : key.hashCode());
    }

    /**
     * 根据元素的键获取其在哈希表中对应的索引
     * <br/>如果键为null，默认存储在首位置
     * @param key 元素的键
     * @return 索引
     */
    private int index(K key) {
        // 替代取模操作
        return hash(key) & (table.length - 1);
    }

    /**
     * 对元素的键进行哈希操作
     * @param key 元素的键
     * @return 哈希值
     */
    private int hash(K key) {
        int hash;
        // 哈希值默认为16位，但是为了防止hashCode()方法的实现生成大于16位的哈希值，所以将其高16位与低16位进行扰动计算
        return key == null ? 0 : (hash = key.hashCode()) ^ (hash >>> 16);
    }

    /**
     * 检查元素的键是否为空
     * @param key 元素的键
     */
    private void checkKeyNotNull(K key) {
        if (key == null) throw new IllegalArgumentException("Key不能为空！");
    }

    /**
     * 删除元素节点
     * @param node 要删除的元素节点
     * @return 删除元素的值
     */
    protected V remove(Node<K, V> node) {
        if (node == null) return null;
        size--;

        // 被删除元素的值
        Node<K, V> origin = node;
        V removedValue = origin.value;
        // 节点的度为2
        if (node.left != null && node.right != null) {
            // 找到后继节点
            Node<K, V> successor = successor(node);
            // 使用后继节点的值来替代原有值
            node.key = successor.key;
            node.value = successor.value;
            node.hash = successor.hash;
            // 删除后继节点，因为后继节点一定是度为0或1的节点，所以可以走下面的删除逻辑
            node = successor;

            // 仅LinkedHashMap需要实现此方法
            swapBeforeRemove(origin, node);
        }

        // 节点的度为1或0
        Node<K, V> replacement = node.left != null ? node.left : node.right;
        if (replacement != null) {
            // 节点的度为1的情况
            replacement.parent = node.parent;
            // 更改父节点的左右指针
            if (node.isLeftChild()) {
                node.parent.left = replacement;
            } else if (node.isRightChild()) {
                node.parent.right = replacement;
            } else {
                // node为根节点
                table[index(node.key)] = replacement;
            }

            fixAfterRemove(replacement);
        } else if (node.parent == null) {
            // 节点是叶子节点（即度为0）并且同时也是根节点，等价于判断：node == root
            table[index(node.key)] = null;
        } else {
            // 节点是叶子节点（即度为0）但是有父节点
            if (node.isLeftChild()) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }

            fixAfterRemove(node);
        }

        afterRemove(node);
        return removedValue;
    }

    /**
     * 删除后的操作
     * @param node 删除的节点或者用以取代删除节点的节点
     */
    protected void afterRemove(Node<K, V> node) {
        // 空方法
    }

    /**
     * 用于 {@link org.gzy.map.LightLinkedHashMap} 的删除前置操作，让子类 {@link org.gzy.map.LightLinkedHashMap} 来实现
     * <br/>主要作用是交换显示被删除的节点和真正被删除的节点，有利于删除后双向链表的指针交换
     * @param origin 显式被删除的节点
     * @param removedNode 真正被删除的节点
     */
    protected void swapBeforeRemove(Node<K, V> origin, Node<K, V> removedNode) {
        // 空方法
    }

    /**
     * 获取后继节点
     * @return 后继节点
     */
    private Node<K, V> successor(Node<K, V> node) {
        if (node == null) return null;

        // 从左子树中寻找后继节点
        Node<K, V> p = node.right;
        if (p != null) {
            while (p.left != null) {
                p = p.left;
            }
            return p;
        }

        // 从父节点中寻找后继节点
        while (node.isRightChild()) {
            node = node.parent;
        }
        return node.parent;
    }

    /**
     * 添加后的修复操作
     * @param node 添加的节点
     */
    private void fixAfterPut(Node<K, V> node) {
        // 如果添加元素的父节点为黑色时，无需任何处理
        Node<K, V> parent = node.parent;
        if (parent == null) {
            // 如果添加的元素为根节点或上溢至根节点，直接染成黑色即可
            color(node, Color.BLACK);
            return;
        }
        if (isBlack(parent)) return;

        Node<K, V> uncle = parent.sibling();
        Node<K, V> grandparent = color(parent.parent, Color.RED);
        // 如果叔叔节点为红色，将父节点和叔叔节点染为黑色
        if (isRed(uncle)) {
            color(parent, Color.BLACK);
            color(uncle, Color.BLACK);

            // 祖父节点需要向上合并（上溢），就类似于向上添加一个新节点，因此将其染成红色
            fixAfterPut(grandparent);
            return;
        }

        // 叔叔节点不是红色
        if (parent.isLeftChild()) {
            if (node.isLeftChild()) {
                // LL的情况
                color(parent, Color.BLACK);
                rightRotate(grandparent);
            } else {
                // LR的情况
                color(node, Color.BLACK);
                leftRotate(parent);
                rightRotate(grandparent);
            }
        } else {
            if (node.isRightChild()) {
                // RR的情况
                color(parent, Color.BLACK);
                leftRotate(grandparent);
            } else {
                // RL的情况
                color(node, Color.BLACK);
                rightRotate(parent);
                leftRotate(grandparent);
            }
        }
    }

    /**
     * 删除后的修复操作
     * @param node 删除的节点或者用以取代删除节点的节点
     */
    private void fixAfterRemove(Node<K, V> node) {
        /*
            这里有两种情况：
            1.删除的是红色节点。
            2.删除的时度为1的黑色节点。
            这里将这两种情况做了一个统一处理，即使删除的是红色节点也将其进行染黑处理，而因为它是要被删除的，所以什么颜色对其无任何影响
         */
        if (isRed(node)) {
            // 如果用以替代的节点（即删除节点的子节点）是红色，直接将其染黑即可
            color(node, Color.BLACK);
            return;
        }

        // 删除的是度为0的黑色节点
        if (node.parent == null) {
            return;
        }
        /*
            判断被删除节点是左是右有两种情况：
            1.因为在执行该方法之前，删除节点父节点的子节点指针已经被清空了，所以通过isRightChild或isLeftChild方法是无法判断的
            2.如果是父节点发生下溢调用的次方法，那么它的子节点指针并不会被清空，所以要通过isRightChild或isLeftChild方法来判断
         */
        Node<K, V> parent = node.parent;
        boolean isNodeRight = parent.right == null || node.isRightChild();
        Node<K, V> sibling = isNodeRight ? parent.left : parent.right;
        // 被删除节点是左还是右的处理逻辑是对称的
        if (isNodeRight) {
            // 被删除的节点在右边，兄弟节点在左边的情况
            if (isRed(sibling)) {
                // 先处理兄弟节点为红色的情况，需要先将其转换为黑色（统一为黑色的情况）
                color(sibling, Color.BLACK);
                color(parent, Color.RED);
                rightRotate(parent);
                sibling = parent.left;
            }

            Color parentColor = isBlack(parent) ? Color.BLACK : Color.RED;
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点左右子节点均为黑色节点
                color(parent, Color.BLACK);
                color(sibling, Color.RED);
                if (parentColor == Color.BLACK) {
                    // 如果父节点也是黑色就会导致父节点也发生下溢，需要对递归调用该方法
                    fixAfterRemove(parent);
                }
            } else {
                // 兄弟节点至少有一个红色子节点
                if (isBlack(sibling.left)) {
                    leftRotate(sibling);
                    // 兄弟节点发生改变了
                    sibling = parent.left;
                }

                color(sibling, parentColor);
                color(parent, Color.BLACK);
                color(sibling.left, Color.BLACK);
                rightRotate(parent);
            }
        } else {
            // 被删除的节点在左边，兄弟节点在右边的情况
            if (isRed(sibling)) {
                // 先处理兄弟节点为红色的情况，需要先将其转换为黑色（统一为黑色的情况）
                color(sibling, Color.BLACK);
                color(parent, Color.RED);
                leftRotate(parent);
                sibling = parent.right;
            }

            Color parentColor = isBlack(parent) ? Color.BLACK : Color.RED;
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点左右子节点均为黑色节点
                color(parent, Color.BLACK);
                color(sibling, Color.RED);
                if (parentColor == Color.BLACK) {
                    // 如果父节点也是黑色就会导致父节点也发生下溢，需要对递归调用该方法
                    fixAfterRemove(parent);
                }
            } else {
                // 兄弟节点至少有一个红色子节点
                if (isBlack(sibling.right)) {
                    rightRotate(sibling);
                    // 兄弟节点发生改变了
                    sibling = parent.right;
                }

                color(sibling, parentColor);
                color(parent, Color.BLACK);
                color(sibling.right, Color.BLACK);
                leftRotate(parent);
            }
        }
    }

    /**
     * 将节点左旋转
     * @param node 要旋转的节点
     */
    private void leftRotate(Node<K, V> node) {
        Node<K, V> p = node.right;
        // 更新父节点
        p.parent = node.parent;
        if (node.isLeftChild()) {
            node.parent.left = p;
        } else if (node.isRightChild()){
            node.parent.right = p;
        } else {
            // node为根节点
            table[index(node.key)] = p;
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
    private void rightRotate(Node<K, V> node) {
        Node<K, V> p = node.left;
        // 更新父节点
        p.parent = node.parent;
        if (node.isLeftChild()) {
            node.parent.left = p;
        } else if (node.isRightChild()) {
            node.parent.right = p;
        } else {
            // node为根节点
            table[index(node.key)] = p;
        }
        node.parent = p;
        if (p.right != null) {
            p.right.parent = node;
        }

        // 更改左右子节点
        node.left = p.right;
        p.right = node;
    }

    /**
     * 元素节点
     * @param <K> 元素键的类型
     * @param <V> 元素值的类型
     */
    public static class Node<K, V> {
        /**
         * 元素的键
         */
        protected K key;
        /**
         * 元素的值
         */
        protected V value;
        /**
         * Key的哈希值
         */
        protected int hash;
        /**
         * 节点颜色
         */
        protected Color color = Color.RED;
        /**
         * 左节点
         */
        protected Node<K, V> left;
        /**
         * 右节点
         */
        protected Node<K, V> right;
        /**
         * 父节点
         */
        protected Node<K, V> parent;

        protected Node(K key, V value, Node<K, V> parent) {
            this(key, value, parent, 0);
        }

        protected Node(K key, V value, Node<K, V> parent, int hash) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.hash = hash;
        }

        /**
         * 判断是否为叶子节点
         * @return 是否为叶子节点
         */
        protected boolean isLeaf() {
            return left == null && right == null;
        }

        /**
         * 判断是否为父节点的左子节点
         * @return 是否为父节点的左子节点
         */
        protected boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        /**
         * 判断是否为父节点的右子节点
         * @return 是否为父节点的右子节点
         */
        protected boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        /**
         * 获取兄弟节点
         * @return 兄弟节点
         */
        protected Node<K, V> sibling() {
            if (isLeftChild()) {
                return parent.right;
            } else if (isRightChild()) {
                return parent.left;
            } else {
                return null;
            }
        }

        /**
         * 获取叔叔节点，即父节点的兄弟节点
         * @return 叔叔节点
         */
        protected Node<K, V> uncle() {
            if (parent == null) return null;
            return parent.sibling();
        }

        /* =============== getter/setter =============== */
        public void setKey(K key) {
            this.key = key;
        }

        public K getKey() {
            return key;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Node(" + key + "," + value + ")";
        }
    }

    /**
     * 为节点染色
     * @param node 要染色的节点
     * @param color 要设置的颜色
     * @return 染色后的节点
     */
    private Node<K, V> color(Node<K, V> node, Color color) {
        if (node == null) return null;

        node.color = color;
        return node;
    }

    /**
     * 获取节点的颜色，如果为空默认为黑色
     * @param node 要获取颜色的节点
     * @return 颜色
     */
    private Color colorOf(Node<K, V> node) {
        return node == null ? Color.BLACK : node.color;
    }

    /**
     * 判断是否为红色节点
     * @param node 要判断的节点
     * @return 是否为红色
     */
    private boolean isRed(Node<K, V> node) {
        return colorOf(node) == Color.RED;
    }

    /**
     * 判断是否为黑色节点
     * @param node 要判断的节点
     * @return 是否为黑色
     */
    private boolean isBlack(Node<K, V> node) {
        return colorOf(node) == Color.BLACK;
    }

    /**
     * 红黑树节点颜色
     */
    private enum Color {
        /**
         * 红色节点
         */
        RED,
        /**
         * 黑色节点
         */
        BLACK
    }
}
