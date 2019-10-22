package com.quyc.learn.javabasic.Algorithm.Collection;

import com.quyc.learn.javabasic.Algorithm.sort.*;
import com.quyc.learn.javabasic.Algorithm.sort.fastsort.QuickSort;
import com.quyc.learn.javabasic.Algorithm.sort.fastsort.ThreeWayQuickSort;
import com.quyc.learn.javabasic.Algorithm.sort.heapsort.HeapSort;

import java.util.*;

/**
 * Created by quyuanchao on 2019-2-26 11:41.
 * <p>Title: com.review.Algorithm.Collection</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class Collection {

    public static void main(String[] args) {
//        List arrayList = new ArrayList(500000);
//        List<Integer> arrayList = new LinkedList<>();
//        int count = 500000;
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < count; i++) {
//            arrayList.add(i);
//        }
//        long end = System.currentTimeMillis();
//        System.out.println("start-end = " + (start - end));
//        start = System.currentTimeMillis();
//        removeEvensVar(arrayList);
//        end = System.currentTimeMillis();
//        System.out.println("start-end = " + (start - end));
        testSortMethod();
    }

    public static void removeEvensVar(List<Integer> list) {
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() % 2 == 0) {
                iterator.remove();
            }
        }

    }

    public static void testSortMethod() {
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            integers.add(i);
        }
        Collections.shuffle(integers);
        int[] ints = new int[1000000];
        for (int i = 0; i < integers.size(); i++) {
            ints[i] = integers.get(i);
        }
        // java工具类排序
        long start = System.currentTimeMillis();
        Arrays.sort(ints);
        long end = System.currentTimeMillis();
        System.out.println("Arrays.sort(int) 快速排序 " + (end - start));
        Object[] objects = integers.toArray();
        start = System.currentTimeMillis();
        Arrays.sort(objects);
        end = System.currentTimeMillis();
        System.out.println("Arrays.sort(object) 归并排序 " + (end - start));
        // 希尔排序
        Sort<Integer> shellSort = new Shell<>();
        Integer[] integers1 = new Integer[1000000];
        integers.toArray(integers1);
        start = System.currentTimeMillis();
        shellSort.sort(integers1);
        end = System.currentTimeMillis();
        System.out.println("希尔排序 " + (end - start));
        // 选择排序
        Selection<Integer> integerSelection = new Selection<>();
        integers1 = new Integer[1000000];
        integers.toArray(integers1);
        start = System.currentTimeMillis();
//        integerSelection.sort(integers1);
        end = System.currentTimeMillis();
        System.out.println("选择排序 " + (end - start));
        // 冒泡排序
        Bubble<Integer> integerBubble = new Bubble<>();
        integers1 = new Integer[1000000];
        integers.toArray(integers1);
        start = System.currentTimeMillis();
//        integerBubble.sort(integers1);
        end = System.currentTimeMillis();
        System.out.println("冒泡排序 " + (end - start));
        // 插入排序
        Insertion<Integer> integerInsertion = new Insertion<>();
        integers1 = new Integer[1000000];
        integers.toArray(integers1);
        start = System.currentTimeMillis();
//        integerInsertion.sort(integers1);
        end = System.currentTimeMillis();
        System.out.println("插入排序 " + (end - start));
        // 归并排序
        MergeSort<Integer> integerMergeSort = new Up2DownMergeSort<>();
        integers1 = new Integer[1000000];
        integers.toArray(integers1);
        start = System.currentTimeMillis();
        integerMergeSort.sort(integers1);
        end = System.currentTimeMillis();
        System.out.println("自上而下归并排序 " + (end - start));
        integerMergeSort = new Down2UpMergeSort<>();
        integers1 = new Integer[1000000];
        integers.toArray(integers1);
        start = System.currentTimeMillis();
        integerMergeSort.sort(integers1);
        end = System.currentTimeMillis();
        System.out.println("自下而上归并排序 " + (end - start));
        // 快速排序
        QuickSort<Integer> integerQuickSort = new QuickSort<>();
        integers1 = new Integer[1000000];
        integers.toArray(integers1);
        start = System.currentTimeMillis();
        integerQuickSort.sort(integers1);
        end = System.currentTimeMillis();
        System.out.println("快速排序 " + (end - start));
        ThreeWayQuickSort<Integer> integerThreeWayQuickSort = new ThreeWayQuickSort<>();
        integers1 = new Integer[1000000];
        integers.toArray(integers1);
        start = System.currentTimeMillis();
        integerThreeWayQuickSort.sort(integers1);
        end = System.currentTimeMillis();
        System.out.println("三向切分快速排序 " + (end - start));
        // 堆排序
        HeapSort<Integer> integerHeapSort = new HeapSort<>();
        integers1 = new Integer[1000000];
        integers.toArray(integers1);
        start = System.currentTimeMillis();
        integerHeapSort.sort(integers1);
        end = System.currentTimeMillis();
        System.out.println("堆排序 " + (end - start));

    }

}
