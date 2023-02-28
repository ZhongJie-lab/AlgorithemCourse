package class7_8_heap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

//加强堆，相比Java自带的堆类型(PriorityQueue)，可以删除任意位置的结点
public class Code07_HeapGreater<T> {
    private ArrayList<T> heap;
    private HashMap<T, Integer> indexMap; //反向索引表
    private int heapSize;
    private Comparator<? super T> comp;

    public Code07_HeapGreater(Comparator<T> comparator) {
        heap = new ArrayList<>();
        indexMap = new HashMap<>();
        heapSize = 0;
        comp = comparator;
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    public int size() {
        return heapSize;
    }

    public boolean contains(T obj) {
        return indexMap.containsKey(obj);
    }

    public void push(T obj) {
        heap.add(obj);
        indexMap.put(obj, heapSize);
        shiftUp(heapSize++);
    }

    public T pop() {
        T res = heap.get(0);
        swap(0, heapSize - 1);
        indexMap.remove(res);
        heap.remove(--heapSize);
        shiftDown(0);
        return  res;
    }

    public T peek() {
        return heap.get(0);
    }

    //把要删除的节点和最后一个简单交换，然后调整堆
    public void remove(T obj) {
        T last = heap.get(heapSize - 1);
        int index = indexMap.get(obj);
        indexMap.remove(obj);
        heap.remove(--heapSize);

        if (obj != last) {
            heap.set(index, last);
            indexMap.put(last, index);
            resign(last);
        }
    }

    //节点obj破坏了堆结构，调整堆
    public void resign(T obj) {
        shiftUp(indexMap.get(obj));
        shiftDown(indexMap.get(obj));
    }

    private void shiftUp(int ind) {
        while (ind > 0 && comp.compare(heap.get(ind), heap.get((ind - 1) / 2)) < 0) {
            swap(ind,(ind - 1) / 2);
        }
    }

    private void shiftDown(int ind) {
        int left = ind * 2 + 1;
        while (left < heapSize) {
            int larger = left + 1 < heapSize && comp.compare(heap.get(left + 1), heap.get(left)) < 0 ? left + 1 : left;
            larger = comp.compare(heap.get(larger), heap.get(ind)) < 0 ? larger : ind;
            if (larger == ind) break;
            swap(ind, larger);
            ind = larger;
            left = ind * 2 + 1;
        }
    }

    private void swap(int i, int j) {
        T o1 = heap.get(i);
        T o2 = heap.get(j);

        heap.set(i, o2);
        heap.set(j, o1);
        indexMap.put(o1, j);
        indexMap.put(o2, i);
    }


}
