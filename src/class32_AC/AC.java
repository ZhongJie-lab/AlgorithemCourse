package class32_AC;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AC {
    public static class ACAutomation {
        private Node root;
        public ACAutomation() {
            root = new Node();
        }

        //构建前缀树
        public void insert(String s) {
            char[] str = s.toCharArray();
            Node node = root;
            for (int i = 0; i < str.length; i++) {
                int index = str[i] - 'a';
                if (node.nexts[index] == null) {
                    node.nexts[index] = new Node();
                }
                node = node.nexts[index];
            }
            node.end = s;
        }

        //设置fail指针，宽度优先遍历
        /*
            fail指针的含义：
            如果必须以当前字符结尾，当前形成的路径是str，剩下哪一个字符串的前缀和str的后缀，拥有最大的匹配长度。fail指针就指向那个字符串的最后一个字符所对应的节点。

            fail指针为什么要这样指定？为了尽可能得不要淘汰掉还没有考察过的可能性。
         */
        /*
            节点弹出时设置子节点的fail指针，天然保证了最大匹配长度
            父节点指向子节点的路，父节点的fail指向指向的节点，有没有走向这条路的？
            如果没有，再看父节点的fail指针所指向的节点的fail指针，再考察该节点有没有走向这条路的，直到找到某个节点有走向这条路的，x的fail指针指向它
            或者，一直跳到fail指针指向的是null，x的fail指针指向头节点
         */
        public void build() {
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);

            Node cur = null;
            Node cfail = null; //curr节点的fail指针指向的节点
            while (!queue.isEmpty()) {
                cur = queue.poll();
                //标记父节点所有子节点的fail指针
                for (int i = 0; i < 26; i++) {
                    if (cur.nexts[i] != null) {
                        cur.nexts[i].fail = root; //先设定i号子节点的fail是root，后面再改
                        cfail = cur.fail;
                        while (cfail != null) {
                            if (cfail.nexts[i] != null) { //有路
                                cur.nexts[i].fail = cfail.nexts[i];
                                break;
                            }
                            cfail = cfail.fail;
                        }
                        queue.add(cur.nexts[i]);
                    }
                }
            }
        }

        //大文章 content，统计敏感词
        public List<String> containWords(String content) {
            char[] str = content.toCharArray();
            Node cur = root;
            Node follow = null;
            int index = 0;
            List<String> ans = new ArrayList<>();

            for (int i = 0; i < content.length(); i++) {
                index = str[i] - 'a';
                //如果当前字符在这条路上没有匹配出来，就顺着fail指针方向走向下条路径，直到匹配到或走到了root
                while (cur.nexts[index] == null && cur != root) {
                    cur = cur.fail;
                }

                //1 限制来到的路径，匹配上了当前字符，然后就可以继续匹配有没有敏感词
                //2 现在来打的节点，就是前缀树的根节点
                cur = cur.nexts[index] != null ? cur.nexts[index] : root;
                follow = cur;
                while (follow != root) {
                    if(follow.endUse) {
                        break; //跳出当前循环，曾经匹配到了
                    }

                    if (follow.end != null) { //收集答案
                        ans.add(follow.end);
                        follow.endUse = true;
                    }
                    follow = follow.fail;
                }
            }
            return ans;
        }
    }

    public static class Node{
        public String end; //如果一个node的end为空，就不是结尾；如果不为空，表示这个点是某个字符串的结尾，end值就是这个字符串
        public boolean endUse; //在查找敏感词时标记。只有上面的end变量不为空的时候，endUse才有意义；表示这个字符串之前没有加入过答案
        public Node fail;
        public Node[] nexts;

        public Node() {
            endUse = false;
            end = null;
            fail = null;
            nexts = new Node[26];
        }
    }


    public static void main(String[] args) {
        ACAutomation ac = new ACAutomation();
        //加入敏感词，准备好前缀树
        ac.insert("dhe");
        ac.insert("he");
        ac.insert("abcdheks");

        //设置fail指针
        ac.build();

        List<String> ans = ac.containWords("abcdhekskdjfafhasldklskdjhwqaeruv");
        ans.forEach(System.out::println);
    }
}
