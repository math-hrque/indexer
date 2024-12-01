package com.indexer.structures;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

import com.indexer.enums.SortAlgorithm;
import com.indexer.models.HashBucket;
import com.indexer.models.HashBucketNode;
import com.indexer.models.HashEntry;
import com.indexer.utils.TFIDFCalculator;

public class WordFrequencyIndexer {
    private HashBucket[] buckets;
    private String fileName;
    private int totalWords;
    private int totalWordCount;
    private double tfIdfScore;
    private static final int INITIAL_BUCKET_SIZE = 16;
    private static final int LOAD_FACTOR_THRESHOLD = 8;
    private static SortAlgorithm defaultSortAlgorithm = SortAlgorithm.QUICKSORT;

    public WordFrequencyIndexer() {
        this.buckets = new HashBucket[INITIAL_BUCKET_SIZE];
    }

    /**
     * Populates the indexer with word frequencies from the specified file.
     *
     * @param fileName the name of the file to process
     * @throws IOException if the file cannot be read
     */
    public synchronized void populateFromFile(String fileName) throws IOException {
        this.fileName = fileName;
        Path filePath = Paths.get("docs", fileName);
    
        try (Stream<String> lines = Files.lines(filePath, StandardCharsets.UTF_8)) {
            lines.flatMap(line -> Stream.of(line.toLowerCase().split("[^a-zA-Z]+")))
                 .filter(word -> word.length() >= 2 && word.matches("[a-zA-Z]+"))
                 .forEach(word -> put(word, 1));
        }
    }

    private synchronized void put(String key, int value) {
        if (totalWords / buckets.length >= LOAD_FACTOR_THRESHOLD) {
            resize();
        }

        int index = getIndex(key);
        if (buckets[index] == null) {
            buckets[index] = new HashBucket();
        }
        buckets[index].insert(new HashEntry(key, value));
        totalWords++;
    }

    private int getIndex(String key) {
        return Math.abs(hashCode(key) % buckets.length);
    }

    private int hashCode(String s) {
        int hash = 0;
        int prime = 31;
        for (int i = 0; i < s.length(); i++) {
            hash = prime * hash + s.charAt(i);
        }
        return hash;
    }

    private synchronized void resize() {
        HashBucket[] oldBuckets = buckets;
        buckets = new HashBucket[oldBuckets.length * 2];
        totalWords = 0;

        for (HashBucket bucket : oldBuckets) {
            if (bucket != null) {
                HashBucketNode currentNode = bucket.getHead();
                while (currentNode != null) {
                    put(currentNode.getData().getKey(), currentNode.getData().getValue());
                    currentNode = currentNode.getNext();
                }
            }
        }
    }

    /**
     * Returns the top N most frequent words in the indexer.
     *
     * @param n the number of top frequencies to retrieve
     * @return an array of the most frequent words
     */
    public HashEntry[] getTopFrequencies(int n) {
        return getTopFrequencies(n, defaultSortAlgorithm);
    }

    public HashEntry[] getTopFrequencies(int n, SortAlgorithm sortAlgorithm) {
        HashEntry[] allEntries = toArray();
        sortAlgorithm.sort(allEntries);
        return n > allEntries.length ? allEntries : Arrays.copyOf(allEntries, n);
    }

    private HashEntry[] toArray() {
        HashEntry[] entries = new HashEntry[totalWords];
        int i = 0;
        for (HashBucket bucket : buckets) {
            if (bucket == null) continue;
            HashBucketNode currentNode = bucket.getHead();
            while (currentNode != null) {
                entries[i++] = currentNode.getData();
                currentNode = currentNode.getNext();
            }
        }
        return entries;
    }

    /**
     * Retrieves the frequency of a specific word in the indexed data.
     *
     * This method calculates the hash index of the provided word and checks
     * the corresponding bucket for its occurrence. If the word is found,
     * its frequency is returned; otherwise, it returns 0.
     *
     * @param word the word whose frequency needs to be retrieved
     * @return the frequency of the word if found, or 0 if the word is not present
     */
    public int getWordFrequency(String word) {
        int index = getIndex(word);
        if (buckets[index] == null) return 0;

        HashBucketNode current = buckets[index].getHead();
        while (current != null) {
            if (current.getData().getKey().equals(word)) {
                return current.getData().getValue();
            }
            current = current.getNext();
        }
        return 0;
    }

    /**
     * Calculates the TF-IDF score for a given set of terms.
     *
     * @param terms the search terms
     * @param totalDocuments the total number of documents
     * @param documentsWithTerm the number of documents containing the terms
     * @return the calculated TF-IDF score
     */
    public synchronized double calculateTFIDF(String[] terms, int totalDocuments, int documentsWithTerm) {
        double[] termFrequencies = new double[terms.length];
        boolean containsAllTerms = true;

        for (int i = 0; i < terms.length; i++) {
            int frequency = getWordFrequency(terms[i]);
            termFrequencies[i] = TFIDFCalculator.calculateTermFrequency(frequency, totalWordCount);
            if (frequency == 0) {
                containsAllTerms = false;
            }
        }

        if (containsAllTerms) {
            this.tfIdfScore = TFIDFCalculator.calculateTFIDF(
                termFrequencies, totalDocuments, documentsWithTerm
            );
        } else {
            this.tfIdfScore = 0.0;
        }

        return this.tfIdfScore;
    }

    public String getFileName() {
        return fileName;
    }

    public double getTFIDFScore() {
        return tfIdfScore;
    }
}
