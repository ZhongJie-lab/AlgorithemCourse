package class13_14_greedy;

/*
给定一个字符串str，只由‘X’和‘.’两种字符构成。
‘X’表示墙，不能放灯，也不需要点亮
‘.’表示居民点，可以放灯，需要点亮
如果灯放在i位置，可以让i-1，i和i+1三个位置被点亮
返回如果点亮str中所有需要点亮的位置，至少需要几盏灯
 */
public class Light {
    public static int minLight2(String road) {
        if (road == null || road.length() == 0) return 0;

        char[] str = road.toCharArray();
        int light = 0;

        for (int i = 0; i < str.length;) { //步长先不设置
            if (str[i] == 'x') {
                i++;
            } else {
                light++; //每种情况都要放一盏灯，每种情况下灯放在不同的位置
                if (i == str.length - 1) {//i是最后一个，i位置放灯
                    break;
                } else { //有 i + 1位置，而且i 是 .
                    if (str[i + 1] == 'x') { // i+1是x，i位置放灯
                        i += 2;
                    } else {  //i+1是. i+2是x的话i+1放灯；i+2是.的话i+1放灯
                        i += 3;
                    }
                }
            }
        }

        return light;
    }


    public static void main(String[] args) {
        String road = ".......x..xx......xxxxxxx";
        System.out.println(Light.minLight2(road));
    }
}
