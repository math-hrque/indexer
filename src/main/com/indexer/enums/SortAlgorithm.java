package com.indexer.enums;

import com.indexer.models.HashEntry;
import com.indexer.sorting_algorithms.HeapSort;
import com.indexer.sorting_algorithms.MergeSort;
import com.indexer.sorting_algorithms.QuickSort;
import com.indexer.sorting_algorithms.ShellSort;

public enum SortAlgorithm {
    HEAPSORT {
        @Override
        public HashEntry[] sort(HashEntry[] array) {
            return HeapSort.heapSort(array);
        }
    },
    MERGESORT {
        @Override
        public HashEntry[] sort(HashEntry[] array) {
            return MergeSort.mergeSort(array);
        }
    },
    QUICKSORT {
        @Override
        public HashEntry[] sort(HashEntry[] array) {
            return QuickSort.quickSort(array);
        }
    },
    SHELLSORT {
        @Override
        public HashEntry[] sort(HashEntry[] array) {
            return ShellSort.shellSort(array);
        }
    };
    
    public abstract HashEntry[] sort(HashEntry[] array);
}
