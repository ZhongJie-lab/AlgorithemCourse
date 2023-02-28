package class35_AVL;

public class AVLTreeMap_sample {
    public static class AVLNode<K extends Comparable<K>, V> {
        public K k;
        public V v;
        public AVLNode<K, V> l;
        public AVLNode<K, V> r;
        public int h;

        public AVLNode(K key, V value) {
            k = key;
            v = value;
            h = 1;
        }
    }

    public static class AVLTreeMap<K extends Comparable<K>, V> {
        private AVLNode<K, V> root;
        private int size;

        public AVLTreeMap() {
            root = null;
            size = 0;
        }

        public int size() {
            return size;
        }

        public boolean containsKey(K key) {
            if (key == null) {
                return false;
            }
            AVLNode<K, V> lastNode = findLastIndex(key);

            return lastNode != null && key.compareTo(lastNode.k) == 0;
        }

        public V get(K key) {
            if (key == null) {
                return null;
            }
            AVLNode<K, V> lastNode = findLastIndex(key);
            if (lastNode != null && key.compareTo(lastNode.k) == 0) {
                return lastNode.v;
            }
            return null;
        }

        public void put(K key, V value) {
            if (key == null) {
                return;
            }

            AVLNode<K, V> lastNode = findLastIndex(key);
            if (lastNode != null && key.compareTo(lastNode.k) == 0) { //如果已经存子，更新value
                lastNode.v = value;
            } else {
                size++;
                root = add(root, key, value);
            }
        }

        public void remove(K key) {
            if (key == null) {
                return;
            }
            if (containsKey(key)) {
                size--;
               root = delete(root, key);
            }
        }

        //最左节点
        public K firstKey() {
            if (root == null) {
                return null;
            }
            AVLNode<K, V> cur = root;
            while (cur.l != null) {
                cur = cur.l;
            }
            return cur.k;
        }

        //最右节点
        public K lastKey() {
            if (root == null) {
                return null;
            }
            AVLNode<K, V> cur = root;
            while (cur.r != null) {
                cur = cur.r;
            }
            return cur.k;
        }

        public K floorKey(K key) {
            if (key == null) {
                return null;
            }
            AVLNode<K, V> lastNoBigIndex = findLastNoBigIndex(key);
            return lastNoBigIndex == null ? null : lastNoBigIndex.k;
        }

        public K ceilingKey(K key) {
            if (key == null) {
                return null;
            }
            AVLNode<K, V> lastNoSmallIndex = findLastNoSmallIndex(key);
            return lastNoSmallIndex == null ? null : lastNoSmallIndex.k;
        }

        //左旋 - 头节点往左倒
        private AVLNode<K, V> leftRotate(AVLNode<K, V> cur) {
            AVLNode<K, V> right = cur.r;
            cur.r = right.l;
            right.l = cur;
            //cur， right有改动，重新计算的高度
            cur.h = Math.max(cur.l != null ? cur.l.h : 0, cur.r != null ? cur.r.h : 0) + 1;
            right.h = Math.max(right.l != null ? right.l.h : 0, right.r != null ? right.r.h : 0) + 1;
            return right;
        }

        //右旋 - 头节点往右倒
        private AVLNode<K, V> rightRotate(AVLNode<K, V> cur) {
            AVLNode<K, V> left = cur.l;
            cur.l = left.r;
            left.r = cur;
            cur.h = Math.max(cur.l != null ? cur.l.h : 0, cur.r != null ? cur.r.h : 0) + 1;
            left.h = Math.max(left.l != null ? left.l.h : 0, left.r != null ? left.r.h : 0) + 1;
            return left;
        }

        //调整整棵树为AVL树
        private AVLNode<K, V> maintain(AVLNode<K, V> cur) {
            if (cur == null) {
                return null;
            }
            int leftHeight = cur.l != null ? cur.l.h : 0;
            int rightHeight = cur.r != null ? cur.r.h : 0;
            if (Math.abs(leftHeight - rightHeight) > 1) {
                if (leftHeight > rightHeight) {
                    int llHeight = cur.l != null && cur.l.l != null ? cur.l.l.h : 0;
                    int lrHeight = cur.l != null && cur.l.r != null ? cur.l.r.h : 0;
                    if (llHeight >= lrHeight) { //LL 或 LR，按LL型调整 (llHeight == lrHeight 即是LL也是LR)
                        cur = rightRotate(cur);
                    } else { //LR
                        cur.l = leftRotate(cur.l);
                        cur = rightRotate(cur);
                    }
                } else {
                    int rlHeight = cur.r != null && cur.r.l != null ? cur.r.l.h : 0;
                    int rrHeight = cur.r != null && cur.r.r != null ? cur.r.r.h : 0;
                    if (rrHeight >= rlHeight) { //RR 或 RL，按RR型调整 (rrHeight == rlHeight 即是RR也是RL)
                        cur = leftRotate(cur);
                    } else { //RL
                        cur.r = rightRotate(cur.r);
                        cur = leftRotate(cur);
                    }
                }
            }
            return cur;
        }

        //在cur这棵树上，加入节点<k, v>， 返回cur这棵树的新头节点
        private AVLNode<K, V> add(AVLNode<K, V> cur, K key, V value) {
            if (cur == null) {
                return new AVLNode<>(key, value);
            } else {
                if (key.compareTo(cur.k) < 0) {
                    cur.l = add(cur.l, key, value);
                } else {
                    cur.r = add(cur.r, key, value);
                }
                //重新计算高度
                cur.h = Math.max(cur.l != null ? cur.l.h : 0, cur.r != null ? cur.r.h : 0) + 1;
                return maintain(cur);
            }
        }

        //在cur这棵树上，删除节点key， 返回cur这棵树的新头节点
        private AVLNode<K, V> delete(AVLNode<K, V> cur, K key) {
            if (key.compareTo(cur.k) < 0) {
               cur.l = delete(cur.l, key);
            } else if (key.compareTo(cur.k) > 0) {
                cur.r = delete(cur.r, key);
            } else { //cur就是要删的节点
                if (cur.l == null && cur.r == null) {
                    cur = null;
                } else if (cur.l == null && cur.r != null) {
                    cur = cur.r;
                } else if (cur.l != null && cur.r == null) {
                    cur = cur.l;
                } else {
                    //右子树上，找到最左节点
                    AVLNode<K, V> des = cur.r;
                    while (des.l != null) {
                        des = des.l;
                    }
                    //在右子树上删除最左结点
                    cur.r = delete(cur.r, des.k);
                    //des替代cur
                    des.l = cur.l;
                    des.r = cur.r;
                    cur = des;
                }
            }
            if (cur != null) {
                cur.h = Math.max(cur.l != null ? cur.l.h : 0, cur.r != null ? cur.r.h : 0) + 1;
            }
            return maintain(cur);
        }

        //找到key节点并返回，如果找不到，返回最后一个节点
        private AVLNode<K, V> findLastIndex(K key) {
            AVLNode<K, V> cur = root;
            AVLNode<K, V> pre = root;
            while (cur != null) {
                pre = cur;
                if (key.compareTo(cur.k) == 0) {
                    break;
                } else if (key.compareTo(cur.k) < 0) {
                    cur = cur.l;
                } else {
                    cur = cur.r;
                }
            }
            return pre;
        }

        //找到 >= key的最左的结点 距离key最近的
        private AVLNode<K, V> findLastNoSmallIndex(K key) {
            AVLNode<K, V> cur = root;
            AVLNode<K, V> ans = null;
            while (cur != null) {
                if (key.compareTo(cur.k) == 0) {
                    ans = cur;
                    break;
                } else if (key.compareTo(cur.k) < 0) {
                    ans = cur;
                    cur = cur.l;
                } else {
                    cur = cur.r;
                }
            }
            return ans;
        }

        //找到 <= key的最右结点 距离key最近的
        private AVLNode<K, V> findLastNoBigIndex(K key) {
            AVLNode<K, V> cur = root;
            AVLNode<K, V> ans = null;
            while (cur != null) {
                if (key.compareTo(cur.k) == 0) {
                    ans = cur;
                    break;
                } else if (key.compareTo(cur.k) < 0) {
                    cur = cur.l;
                } else {
                    ans = cur;
                    cur = cur.r;
                }
            }
            return ans;
        }
    }
}
