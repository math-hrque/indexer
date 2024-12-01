package com.indexer.sorting_algorithms;

import java.util.Comparator;
import java.util.Random;

import com.indexer.models.HashEntry;

public class QuickSort {

    private static final Comparator<HashEntry> COMPARATOR = (entry1, entry2) -> {
        if (entry1 == null || entry2 == null) {
            return 0;
        }
        return Integer.compare(entry2.getValue(), entry1.getValue());
    };
    
    /**
     * Sorts an array of HashEntry objects using Quick Sort.
     *
     * @param arrayToSort the array to sort
     * @return the sorted array
     */
    public static HashEntry[] quickSort(HashEntry[] arrayToSort) {
        shuffle(arrayToSort);
        quickSortRecursive(arrayToSort, 0, arrayToSort.length - 1);
        return arrayToSort;
    }

    private static void shuffle(HashEntry[] arrayToSort) {
        Random rand = new Random();
        for (int i = 0; i < arrayToSort.length - 1; i++) {
            int j = i + rand.nextInt(arrayToSort.length - i);
            swap(arrayToSort, i, j);
        }
    }

    private static void quickSortRecursive(HashEntry[] arrayToSort, int low, int high) {
        if (high <= low) return;
        int partitionIndex = partition(arrayToSort, low, high);
        quickSortRecursive(arrayToSort, low, partitionIndex - 1);
        quickSortRecursive(arrayToSort, partitionIndex + 1, high);
    }

    private static int partition(HashEntry[] arrayToSort, int low, int high) {
        int i = low, j = high + 1;

        while (true) {
            while (COMPARATOR.compare(arrayToSort[++i], arrayToSort[low]) < 0)
                if (i == high) break;

            while (COMPARATOR.compare(arrayToSort[low], arrayToSort[--j]) < 0)
                if (j == low) break;

            if (i >= j) break;
            swap(arrayToSort, i, j);
        }

        swap(arrayToSort, low, j);
        return j;
    }

    private static void swap(HashEntry[] arrayToSort, int i, int j) {
        HashEntry temp = arrayToSort[i];
        arrayToSort[i] = arrayToSort[j];
        arrayToSort[j] = temp;
    }
}
