package com.indexer.sorting_algorithms;

import java.util.Arrays;

import com.indexer.models.HashEntry;

public class MergeSort {

    /**
     * Sorts an array of HashEntry objects using Merge Sort.
     *
     * @param arrayToSort the array to sort
     * @return the sorted array
     */
    public static HashEntry[] mergeSort(HashEntry[] arrayToSort) {
        if (arrayToSort.length <= 1) {
            return arrayToSort;
        }

        int middle = arrayToSort.length / 2;
        HashEntry[] leftHalf = Arrays.copyOfRange(arrayToSort, 0, middle);
        HashEntry[] rightHalf = Arrays.copyOfRange(arrayToSort, middle, arrayToSort.length);

        leftHalf = mergeSort(leftHalf);
        rightHalf = mergeSort(rightHalf);

        return merge(leftHalf, rightHalf);
    }

    private static HashEntry[] merge(HashEntry[] left, HashEntry[] right) {
        int leftIndex = 0, rightIndex = 0, resultIndex = 0;
        HashEntry[] result = new HashEntry[left.length + right.length];

        while (leftIndex < left.length && rightIndex < right.length) {
            if (left[leftIndex].getValue() >= right[rightIndex].getValue()) {
                result[resultIndex++] = left[leftIndex++];
            } else {
                result[resultIndex++] = right[rightIndex++];
            }
        }

        while (leftIndex < left.length) {
            result[resultIndex++] = left[leftIndex++];
        }

        while (rightIndex < right.length) {
            result[resultIndex++] = right[rightIndex++];
        }

        return result;
    }
}
