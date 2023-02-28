package class36_SBTree_SkipList;

import java.util.ArrayList;

public class SkipListMap_sample {
    public static class SkipListNode<K extends Comparable<K>, V> {
        public K key;
        public V val;

        public ArrayList<SkipListNode<K, V>> nextNodes;

        public SkipListNode(K k, V v)  {
            key = k;
            val = v;
            nextNodes = new ArrayList<>();
        }

        //node里面的key是否比otherKey小
        // 头节点认为是最小
        public boolean isKeyLess(K otherKey) {
            return otherKey != null && (key == null || key.compareTo(otherKey) < 0);
        }

        public boolean isKeyEqual(K otherKey) {
            return (key == null && otherKey == null)
                    || (key != null && otherKey != null && key.compareTo(otherKey) == 0);
        }
    }


    public static class SkipListMap<K extends Comparable<K>, V> {
        private static final double PROBABILITY = 0.5;
        private SkipListNode<K, V> head;
        private int size;
        private int maxLevel;

        public SkipListMap() {
            head = new SkipListNode<>(null, null);
            head.nextNodes.add(null); //第0层
            size = 0;
            maxLevel = 0;
        }

        //从最高层，一路找下去
        //最终找到第0层的 < key 的最右节点
        private SkipListNode<K, V> mostRightLessNodeInTree(K key) {
            if (key == null) {
                return null;
            }
            int level = maxLevel;
            SkipListNode<K, V> cur = head;
            while (level >= 0) {
                cur = mostRightLessNodeInLevel(key, cur, level--);
            }
            return cur;
        }

        //在level层，如何往右移动
        //现在来到的节点是cur，来到了cur的level层，在level层上，找到 < key 的最后一个节点并返回
        private SkipListNode<K, V> mostRightLessNodeInLevel(K key, SkipListNode<K, V> cur, int level) {
            SkipListNode<K, V> next = cur.nextNodes.get(level); //跳到同层的下一个节点
            while (next != null && next.isKeyLess(key)) {
                cur = next;
                next = cur.nextNodes.get(level);
            }
            return cur;
        }

        public boolean containsKey(K key) {
            if (key == null) {
                return false;
            }
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null && next.isKeyEqual(key);
        }

        //不存在 新增
        //存在 改value
        public void put(K key, V value) {
            if (key == null) {
                return;
            }

            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> find = less.nextNodes.get(0);
            if (find != null && find.isKeyEqual(key)) { //有节点且存在
                find.val = value;
            } else { //否则
                size++;
                int newNodeLevel = 0;
                while (Math.random() < PROBABILITY) { //以0.5概率随机决定新节点的高度
                    newNodeLevel++;
                }
                //如果新节点高度大于头节点的高度，在头节点上挂好新的层
                while (newNodeLevel > maxLevel) {
                    head.nextNodes.add(null);
                    maxLevel++;
                }

                SkipListNode<K, V> newNode = new SkipListNode<>(key, value);
                //初始化新节点，各层指向null
                for (int i = 0; i <= newNodeLevel; i++) {
                    newNode.nextNodes.add(null);
                }
                //从上往下插入新节点
                int level = maxLevel;
                SkipListNode<K, V> pre = head;
                while (level >= 0) {
                    pre = mostRightLessNodeInLevel(key, pre, level);
                    if (level <= newNodeLevel) { //当新节点层数不小于当前层时，才在这一层加这个节点
                        newNode.nextNodes.set(level, pre.nextNodes.get(level)); //给新节点挂上在level层的后继节点
                        pre.nextNodes.set(level, newNode); //pre的后继节点设成新节点
                    }
                    level--;
                }
            }
        }

        public V get(K key) {
            if (key == null) {
                return null;
            }
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null && next.isKeyEqual(key) ? next.val : null;
        }

        public void remove(K key) {
            if (containsKey(key)) {
                size--;
                int level = maxLevel;
                SkipListNode<K, V> pre = head;
                while (level >= 0) {
                    pre = mostRightLessNodeInLevel(key, pre, level);
                    SkipListNode<K, V> next = pre.nextNodes.get(level);
                    //如果pre的下一个就是要删除的key
                    if (next != null && next.isKeyEqual(key)) {
                        pre.nextNodes.set(level, next.nextNodes.get(level));
                    }
                    //在level中只有一个节点了，就是默认头节点head
                    if (level != 0 && pre == head && pre.nextNodes.get(level) == null) {
                        head.nextNodes.remove(level);
                        maxLevel--;
                    }
                    level--;
                }
            }
        }

        public K firstKey() {
            return head.nextNodes.get(0) != null ? head.nextNodes.get(0).key : null;
        }

        public K lastKey() {
            int level = maxLevel;
            SkipListNode<K,V> cur = head;
            //从head节点，最高层开始，往下蹦，最终cur是整棵树的最右节点
            while (level >= 0) {
                SkipListNode<K, V> next = cur.nextNodes.get(level);
                //找到当前层的最右节点
                while (next != null) {
                    cur = next;
                    next = cur.nextNodes.get(level);
                }
                level--;
            }
            return cur.key;
        }

        public K ceilingKey(K key) {
            if (key == null) {
                return null;
            }
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null ? next.key : null;
        }

        public K floorKey(K key) {
            if (key == null) {
                return null;
            }
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null && next.isKeyEqual(key) ? next.key : less.key;
        }

        public int size() {
            return size;
        }
    }

    //for test
    private static void printAll(SkipListMap<String ,String> obj) {
        for (int i = obj.maxLevel; i >= 0; i--) {
            System.out.println("Level " + i + " : ");
            SkipListNode<String, String> cur = obj.head;
            while (cur.nextNodes.get(i) != null) {
                SkipListNode<String, String> next = cur.nextNodes.get(i);
                System.out.print("(" + next.key + " , " + next.val + ") ");
                cur = next;
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        SkipListMap<String, String> test = new SkipListMap<>();
        printAll(test);
        System.out.println("========================");
        test.put("A", "10");
//        System.out.println("Size : " + test.size);
        printAll(test);
        System.out.println("========================");
        test.remove("A");
        printAll(test);
        System.out.println("========================");
        test.put("E", "E");
        test.put("B", "B");
        test.put("A", "A");
        test.put("F", "F");
        test.put("C", "C");
        test.put("D", "D");
        printAll(test);
        System.out.println("========================");
        System.out.println(test.containsKey("B"));
        System.out.println(test.containsKey("T"));
        System.out.println(test.firstKey());
        System.out.println(test.lastKey());
        System.out.println(test.floorKey("D"));
        System.out.println(test.floorKey("E"));
        System.out.println("=========================");
        test.remove("D");
        printAll(test);
        System.out.println("=========================");
        System.out.println(test.floorKey("D"));
        System.out.println(test.ceilingKey("D"));
    }
}
