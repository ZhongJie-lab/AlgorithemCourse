package class4_stackandqueue;

public class ArrayToQueueAndStack {
    class My_Stack {
        private int size;
        private int[] arr;
        private int limit;
        private int index; //新进来的数放的位置

        public My_Stack(int limit) {
            size = 0;
            arr = new int[limit];
            limit = limit;
            index = 0;

        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return size;
        }

        public void push(int value) throws Exception {
            if (index == limit) {
                throw new Exception("stack is full");
            }
            arr[index++] = value;
            size++;
        }

        public int pop() throws Exception {
            if (size == 0) {
                throw new Exception("stack is empty");
            }
            int ans = arr[index - 1];
            size--;
            index--;
            return ans;
        }

        public int peek() throws Exception {
            if (size == 0) {
                throw new Exception("stack is empty");
            }
            return arr[index - 1];
        }
    }

    //循环数组实现队列
    class My_Queue {
        private int size;
        private int[] arr;
        private int limit;
        private int pollInd; //end 从哪里弹出
        private int pushInd; //start 从哪里加入

        public My_Queue(int limit) {
            arr = new int[limit];
            this.size = 0;
            this.limit = limit;
            pollInd = 0;
            pushInd = 0;
        }
        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void push(int value) throws Exception {
            if (size == limit) {
                throw new Exception("Queue is full!");
            }
            arr[pushInd] = value;
            pushInd = getNextInd(pushInd);
            size++;
        }

        public int pop() throws Exception {
            if (size == 0) {
                throw new Exception("Queue is empty!");
            }
            int ans = arr[pollInd];
            pollInd = getNextInd(pollInd);
            size--;
            return ans;
        }

        private int getNextInd(int ind) {
            return ind < limit - 1 ? ind + 1 : 0;
        }
    }
}

