# **Indexer**


## **Introduction**

The Indexer project is a word frequency analysis tool designed to process text files, count word occurrences, and determine document relevance using the TF-IDF (Term Frequency-Inverse Document Frequency) algorithm. It includes support for various operations, such as listing the most frequent words, counting occurrences of specific terms, and ranking documents by relevance for a search query.

## **TF-IDF Concept**

The TF-IDF algorithm is a widely used method in information retrieval and text mining to evaluate the importance of a word in a document relative to a collection of documents (corpus). The computation is broken into two main components:


## **Requirements**

- Ensure JDK 17 or later is installed.
- The program is compatible with Linux, but it can be adapted for other operating systems as long as platform-specific libraries are avoided.

## **How to Run**

1. Clone this repository.
2. Place the text files you wish to process in the root folder.
4. Compile the project:
   
   4.1. Preferred method (using globbing if supported by your environment):
   
   ```bash
   javac -d out -sourcepath src src/main/**/*.java

   4.2. Alternative method (for environments where globbing does not work):

   ```bash
   javac -d out -sourcepath src src/main/com/indexer/enums/*.java src/main/com/indexer/models/*.java src/main/com/indexer/sorting_algorithms/*.java src/main/com/indexer/structures/*.java src/main/com/indexer/utils/*.java src/main/com/indexer/*.java

5. Execute the program using:

   ``` java -classpath ./out com.indexer.Main --[OPTION] [PARAMETER 1] [PARAMETER 2] ```

## **Sinopse**

```
java -classpath ./out com.indexer.Main --freq N FILE
java -classpath ./out com.indexer.Main --freq-word WORD FILE
java -classpath ./out com.indexer.Main --search TERM FILE [FILE ...]
```

## **Description**

```
The indexer program performs a word count on text documents. Based on this count, it allows users to search for the number of occurrences of a specific word in a document or identify the most relevant documents for a given search term.

The program converts all letters to lowercase and ignores characters like numbers and punctuation.

- When executed with the --freq option, the indexer displays the occurrence count of the N most frequent words in the specified document, in descending order of occurrences.
- When executed with the --freq-word option, the indexer displays the occurrence count of a specific word in the specified document.
- Finally, when executed with the --search option, the indexer lists the most relevant documents for a given search term using the TF-IDF (Term Frequency-Inverse Document Frequency) calculation.
```

## **Options**

```
--freq N FILE
Displays the occurrence count of the N most frequent words in FILE, in descending order of occurrences.

--freq-word WORD FILE
Displays the number of occurrences of WORD in FILE.

--search TERM FILE [FILE ...]
Lists the most relevant FILES found for the search TERM. The list is presented in descending order of relevance. TERM may contain multiple words. In this case, it should be enclosed in quotes.

```

## **Key Features**
- Case Insensitivity: Converts all characters to lowercase.
- Character Filtering: Excludes numbers, punctuation, and words with fewer than two characters.
- Sorting Options: Implements multiple sorting algorithms for customizable performance.
