package com.indexer.sorting_algorithms;

import com.indexer.models.HashEntry;

public class HeapSort {

    /**
     * Sorts an array of HashEntry objects using Heap Sort.
     *
     * @param arrayToSort the array to sort
     * @return the sorted array
     */
    public static HashEntry[] heapSort(HashEntry[] arrayToSort) {
        int n = arrayToSort.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arrayToSort, n, i);
        }

        for (int i = n - 1; i >= 0; i--) {
            swap(arrayToSort, 0, i);
            heapify(arrayToSort, i, 0);
        }

        return arrayToSort;
    }

    private static void heapify(HashEntry[] arrayToSort, int size, int rootIndex) {
        int largest = rootIndex;
        int left = 2 * rootIndex + 1;
        int right = 2 * rootIndex + 2;

        if (left < size && arrayToSort[left].getValue() > arrayToSort[largest].getValue()) {
            largest = left;
        }

        if (right < size && arrayToSort[right].getValue() > arrayToSort[largest].getValue()) {
            largest = right;
        }

        if (largest != rootIndex) {
            swap(arrayToSort, rootIndex, largest);
            heapify(arrayToSort, size, largest);
        }
    }

    private static void swap(HashEntry[] arrayToSort, int i, int j) {
        HashEntry temp = arrayToSort[i];
        arrayToSort[i] = arrayToSort[j];
        arrayToSort[j] = temp;
    }
}
