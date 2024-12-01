package com.indexer.utils;

import java.util.Arrays;

public class TFIDFCalculator {

    /**
     * Calculates the TF-IDF value for a given set of term frequencies.
     *
     * @param termFrequencies the array of term frequencies for the search terms
     * @param totalDocuments the total number of documents in the corpus
     * @param documentsWithTerm the number of documents containing the terms
     * @return the calculated TF-IDF score
     */
    public static double calculateTFIDF(double[] termFrequencies, int totalDocuments, int documentsWithTerm) {
        double tf = Arrays.stream(termFrequencies).sum();
        double idf = calculateInverseDocumentFrequency(totalDocuments, documentsWithTerm);
        return tf * idf;
    }

    /**
     * Calculates the term frequency (TF) for a specific term.
     *
     * @param termFrequency the number of occurrences of the term in the document
     * @param totalWords the total number of words in the document
     * @return the term frequency as a double
     */
    public static double calculateTermFrequency(int termFrequency, int totalWords) {
        return (double) termFrequency / totalWords;
    }

    /**
     * Calculates the inverse document frequency (IDF) for a specific term.
     *
     * @param totalDocuments the total number of documents in the corpus
     * @param documentsWithTerm the number of documents containing the term
     * @return the inverse document frequency as a double
     */
    private static double calculateInverseDocumentFrequency(int totalDocuments, int documentsWithTerm) {
        return Math.log10((double) totalDocuments / (1 + documentsWithTerm));
    }
}
