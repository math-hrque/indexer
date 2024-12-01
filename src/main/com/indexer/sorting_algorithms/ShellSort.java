package com.indexer.sorting_algorithms;

import com.indexer.models.HashEntry;

public class ShellSort {

    /**
     * Sorts an array of HashEntry objects using Shell Sort.
     *
     * @param arrayToSort the array to sort
     * @return the sorted array
     */
    public static HashEntry[] shellSort(HashEntry[] arrayToSort) {
        int n = arrayToSort.length;

        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                HashEntry temp = arrayToSort[i];
                int j;
                for (j = i; j >= gap && arrayToSort[j - gap].getValue() < temp.getValue(); j -= gap) {
                    arrayToSort[j] = arrayToSort[j - gap];
                }
                arrayToSort[j] = temp;
            }
        }

        return arrayToSort;
    }
}
