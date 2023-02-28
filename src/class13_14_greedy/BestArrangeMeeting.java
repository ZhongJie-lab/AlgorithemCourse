package class13_14_greedy;

import java.util.Arrays;
import java.util.Comparator;
/*
一些项目要占用一个会议室宣讲，会议室不能同时容纳两个项目的宣讲。
给你每一个项目开始的时间和结束的时间
你来安排宣讲的日程，要求会议室进行的宣讲的场次最多。
返回最多的宣讲场次。
 */
public class BestArrangeMeeting {
    public static int bestArray(Program[] programs) {
        if (programs == null || programs.length == 0) return 0;
        Arrays.sort(programs, new Comparator<Program>() {
            @Override
            public int compare(Program o1, Program o2) {
                return  o1.end  - o2.end;
            }
        });
        int timeline = 0;
        int ans = 0;
        for (int i = 0; i < programs.length; i++) {
            if (timeline <= programs[i].start) {
                ans++;
                timeline = programs[i].end;
            }
        }
        return ans;
    }

    public static class Program {
        public int start;
        public int end;

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }
    }
}
