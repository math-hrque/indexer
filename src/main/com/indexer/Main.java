package com.indexer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import com.indexer.models.HashEntry;
import com.indexer.structures.WordFrequencyIndexer;
import com.indexer.utils.ArgumentValidator;

public class Main {

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        if (args.length == 0) {
            System.out.println("Usage: [--freq | --freq-word | --search] [parameters]");
            return;
        }

        String option = args[0];
        try {
            switch (option) {
                case "--freq":
                    ArgumentValidator.validateFreqArguments(args);
                    handleFreqOption(args);
                    break;
                case "--freq-word":
                    ArgumentValidator.validateFreqWordArguments(args);
                    handleFreqWordOption(args);
                    break;
                case "--search":
                    ArgumentValidator.validateSearchArguments(args);
                    handleSearchOption(args);
                    break;
                default:
                    System.out.println("Invalid option. Use --freq, --freq-word, or --search.");
            }
        } catch (IllegalArgumentException | IOException e) {
            System.err.println("Error: " + e.getMessage());
        }

        long endTime = System.currentTimeMillis();
        double executionTime = (endTime - startTime) / 1000.0;
        System.out.printf("\n# Execution time: %.3f seconds.%n", executionTime);
    }

    private static void handleFreqOption(String[] args) throws IOException {
        int n = Integer.parseInt(args[1]);
        String fileName = args[2];
    
        WordFrequencyIndexer indexer = new WordFrequencyIndexer();
        indexer.populateFromFile(fileName);
        
        HashEntry[] topFrequencies = indexer.getTopFrequencies(n);
        System.out.println("\n-> The " + n + " most frequent words in the file '" + fileName + "' are:");
        for (int i = 0; i < n; i++) {
            if (i >= topFrequencies.length || topFrequencies[i] == null) {
                System.out.println("\nWarning: the document does not contain more words to display!");
                break;
            }
            System.out.println((i + 1) + ") '" + topFrequencies[i].getKey() + "' occurred " + topFrequencies[i].getValue() + " time(s).");
        }
    }
    

    private static void handleFreqWordOption(String[] args) throws IOException {
        String word = args[1].toLowerCase();
        String fileName = args[2];

        WordFrequencyIndexer wordHashMap = new WordFrequencyIndexer();
        wordHashMap.populateFromFile(fileName);
        int frequency = wordHashMap.getWordFrequency(word);

        System.out.printf("\nThe word '%s' appears %d time(s) in the file '%s'.%n", word, frequency, fileName);
    }

    private static void handleSearchOption(String[] args) throws IOException {
        String[] searchTerms = args[1].toLowerCase().split("\\s+");
        String[] files = Arrays.copyOfRange(args, 2, args.length);

        WordFrequencyIndexer[] indexers = new WordFrequencyIndexer[files.length];
        int documentsWithTerm = 0;

        for (int i = 0; i < files.length; i++) {
            WordFrequencyIndexer indexer = new WordFrequencyIndexer();
            indexer.populateFromFile(files[i]);
            double tfIdf = indexer.calculateTFIDF(searchTerms, files.length, documentsWithTerm);
            if (tfIdf > 0) documentsWithTerm++;
            indexers[i] = indexer;
        }

        Arrays.sort(indexers, Comparator.comparingDouble(WordFrequencyIndexer::getTFIDFScore).reversed());

        System.out.println("\n-> Relevant files in descending order:");
        for (int i = 0; i < indexers.length; i++) {
            System.out.printf("%d) '%s' with relevance %.9f.%n", i + 1,
                    indexers[i].getFileName(), indexers[i].getTFIDFScore());
        }
    }
}
