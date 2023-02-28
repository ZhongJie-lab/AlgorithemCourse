package class7_8_heap;

/**
 * Heap是完全二叉树，可以用数组来表示
 * 数组中，结点i的父结点在 (i - 1)/2
 * 数组中，结点i的左孩子在2i + 1， 右孩子在2i + 2
 *
 * 每棵子树的最大值都是头节点的就是大顶堆
 * 每棵子树的最小值都是头节点的就是小顶堆
 */

//大顶堆
public class Code01_MyMaxHeap {
    private int[] heap;
    private int limit;
    private int heapSize;

    public Code01_MyMaxHeap(int limit) {
        heap = new int[limit];
        this.limit = limit;
        heapSize = 0;
    }

    public boolean isFull() {
        return heapSize == limit;
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    public void push(int num) {
        if (heapSize == limit) {
            throw new RuntimeException("heap is full");
        }
        heap[heapSize] = num;
        heapSize++;
        heapInsert(heap, heapSize);
    }

    public int pop() {
        if (heapSize == 0) {
            throw new RuntimeException("heap is empty");
        }
        int ans = heap[0];
        swap(heap, 0, --heapSize);
        heapify(heap, 0, heapSize);

        return ans;
    }

    //pos是堆最末尾的位置，往上调整，和父节点比较
    private void heapInsert(int[] heap, int pos) {
        while(pos > 0 && heap[pos] > heap[(pos - 1)/2]) {
            swap(heap, pos, (pos - 1) / 2);
            pos = (pos - 1) / 2;
        }
    }


    //pos位置的元素需要调整，往下探索，下沉
    private void heapify(int[] heap, int pos, int heapSize) {
        int left = pos * 2 + 1; //左孩子; 右孩子 left + 1
        while (left < heapSize) {
            //确定哪个孩子的值更大
            int larger = left + 1 < heapSize && heap[left + 1] > heap[left] ? left + 1 : left;
            //和大的孩子的值比较
            larger = heap[larger] > heap[pos] ? larger : pos;
            if (larger == pos) break; //不用再下沉了，父是更大的，下面结点值只会更小
            swap(heap, pos, larger);
            pos = larger;
            left = pos * 2 + 1;
        }
    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
