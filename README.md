# **Indexer**  
_A Word Frequency Analysis Tool with TF-IDF Ranking and Sorting Algorithms_

## **Introduction**

The `Indexer` project is a tool designed for analyzing word frequencies in text files and determining document relevance based on the **TF-IDF** (Term Frequency-Inverse Document Frequency) algorithm. It supports the following key features:
- Listing the most frequent words in a document,
- Counting occurrences of specific terms across documents, and
- Ranking documents by relevance to a search query.

In addition to the core TF-IDF algorithm, the tool also supports benchmarking and experimenting with various sorting and ranking algorithms, offering flexibility to choose the most optimal solution for different datasets and use cases.

---

## **TF-IDF Concept**

The **TF-IDF** algorithm evaluates the importance of a word in a document relative to a collection of documents (corpus). It is composed of two main components:

### **1. Term Frequency (TF)**

Measures how often a term *t* appears in a document *d*. It is calculated as:

**TF(t, d) = (Number of times t appears in d) ÷ (Total number of words in d)**

### **2. Inverse Document Frequency (IDF)**

Measures the importance of a term across a collection of documents *D*. Rare terms that appear in fewer documents are considered more important. It is calculated as:

**IDF(t, D) = log10(Total number of documents in D ÷ (1 + Number of documents containing t))**

The "+1" ensures safe division in case a term appears in all documents.

### **3. TF-IDF Score**

The **TF-IDF** score is the product of **TF** and **IDF**, providing a relevance score for a term in a document in relation to the entire corpus:

**TF-IDF(t, d, D) = TF(t, d) × IDF(t, D)**

For multi-word terms or phrases, the average **TF-IDF** score for each word is computed.

---

### **TF-IDF Example**  
Consider a corpus of 5 documents, where the term "data" appears in all but one of them. The term "machine" only appears in one document.

- **TF for "data"**: If it appears 3 times in a 100-word document, **TF** = 0.03.
- **IDF for "data"**: Given that "data" appears in 4 out of 5 documents, **IDF** ≈ 0.096.
- **TF-IDF for "data"**: `TF-IDF = 0.03 × 0.096 ≈ 0.00288`.

- **TF for "machine"**: If it appears once in a 100-word document, **TF** = 0.01.
- **IDF for "machine"**: Given that "machine" appears in only 1 document, **IDF** ≈ 0.6990.
- **TF-IDF for "machine"**: `TF-IDF = 0.01 × 0.6990 ≈ 0.00699`.

In this example, **"machine"** has a higher **TF-IDF** score due to its rarity across the corpus.

---

## **Requirements**

- **JDK 17** or later.
- The program is compatible with Linux but can be adapted for other operating systems.

---

## **How to Run**

1. Clone this repository:
    ```bash
    git clone https://github.com/math-hrque/indexer.git
    ```

2. Place the text files you wish to process inside the `docs` folder.  
   You can download the required test documents from the following [Google Drive folder](https://drive.google.com/drive/folders/1aZGTSMqYiJIILG9ScFal6f7S_SqkiC8D?usp=drive_link).  
   The folder `indexer_test_documents` contains the following 5 text files:

   - **traveling_salesman_problem.txt** (64MB)
   - **quantum_computing.txt** (128MB)
   - **ai_ml.txt** (256MB)
   - **history_of_computing.txt** (512MB)
   - **cloud_computing_and_big_data.txt** (1GB)

   Download the files you need, and place them in the `docs` folder inside your project directory.

3. Compile the project:

   3.1. Preferred method (using globbing if supported by your environment):
   ```bash
   javac -d out -sourcepath src src/main/**/*.java
   ```
   
   3.2. Alternative method (for environments where globbing does not work):
   ```bash
   javac -d out -sourcepath src src/main/com/indexer/enums/*.java src/main/com/indexer/models/*.java src/main/com/indexer/sorting_algorithms/*.java src/main/com/indexer/structures/*.java src/main/com/indexer/utils/*.java src/main/com/indexer/*.java
   ```

4. Execute the program using:
   ```
   java -classpath ./out com.indexer.Main --[OPTION] [PARAMETER 1] [PARAMETER 2]
   ```

---

## **Usage Examples**

- List frequent words:
   ```bash
   java -classpath ./out com.indexer.Main --freq 10 history_of_computing.txt
   ```
   Outputs the 10 most frequent words in history_of_computing.txt.
  
- Word frequency in a file:
   ```bash
   java -classpath ./out com.indexer.Main --freq-word "deep" ai_ml.txt
   ```
   Displays the number of occurrences of the word "deep" in ai_ml.txt.
  
- Search relevant documents:
   ```bash
   java -classpath ./out com.indexer.Main --search "Hamiltonian cycle" traveling_salesman_problem.txt quantum_computing.txt
   ```
   Ranks traveling_salesman_problem.txt and quantum_computing.txt based on relevance to the search term "Hamiltonian cycle".

---

## **Key Features**

- **Custom HashBucket Implementation**: Instead of relying on Java's built-in `HashMap` or `HashTable`, this project implements a custom `HashBucket` structure. This allows for fine-tuned control over the handling of hash collisions, resizing, and insertion logic. The custom structure ensures that the program can efficiently scale when processing large datasets by dynamically adjusting the number of buckets as needed.

- **Case Insensitivity**: All input is converted to lowercase, making the analysis case-insensitive, which is essential for consistent word frequency analysis.

- **Character Filtering**: Excludes non-alphabetic characters and words with fewer than two characters to ensure clean and meaningful data for processing.

- **Sorting Options**: Multiple sorting algorithms are available to rank words or documents, providing flexibility in terms of performance optimization and dataset-specific requirements.

---

## **Custom HashBucket Implementation**

This project implements its own hash bucket structure instead of using Java’s built-in `HashMap`. The `HashBucket` is designed to handle key-value pairs efficiently and supports the following features:

- **Collision Resolution**: Implements separate chaining for handling hash collisions, where each bucket stores a linked list of entries.
- **Dynamic Resizing**: The hash table grows in size when the number of entries exceeds a defined threshold, ensuring that operations remain efficient as the dataset grows.
- **Efficient Lookup**: Each word's frequency is stored in a `HashEntry`, and the linked list structure ensures that even with hash collisions, lookups remain efficient.

---

## Sorting Algorithms and Performance

The project uses different sorting algorithms to rank words or documents based on frequency. The choice of algorithm can directly impact execution time, depending on the size and distribution of the dataset.

Below are the sorting algorithms implemented in the project, their time complexities, and explanations for the **best case** and **worst case** scenarios for each.

| **Algorithm**       | **Best Case**   | **Average Case**    | **Worst Case**     |
|---------------------|-----------------|---------------------|--------------------|
| **Heap Sort**       | O(n log n)      | O(n log n)          | O(n log n)         |
| **Merge Sort**      | O(n log n)      | O(n log n)          | O(n log n)         |
| **Quick Sort**      | O(n log n)      | O(n log n)          | O(n²)              |
| **Shell Sort**      | O(n log n)      | O(n log n)          | O(n²)              |

### **1. HeapSort**

- **Best Case (O(n log n))**: **HeapSort** performs in **O(n log n)** in the best case because the algorithm always requires this time to build the heap and sort the elements, regardless of the initial order of the data. It does not benefit from any specific arrangement of elements.
- **Worst Case (O(n log n))**: Similarly, the **worst case** for **HeapSort** is also **O(n log n)**. Since the algorithm always constructs the heap and performs operations on it with the same time complexity, the worst-case performance is the same as the best case.

### **2. MergeSort**

- **Best Case (O(n log n))**: In **MergeSort**, the best case occurs when the data is already partially sorted or can be efficiently divided. However, the time complexity remains **O(n log n)** because the algorithm always divides the data recursively and merges them, regardless of the initial order.
- **Worst Case (O(n log n))**: The **worst case** is the same as the best case, **O(n log n)**. **MergeSort** consistently divides the dataset and merges the subarrays, leading to a predictable and stable performance regardless of the data's initial state.

### **3. QuickSort**

- **Best Case (O(n log n))**: The **best case** for **QuickSort** occurs when the **pivot** chosen divides the data evenly. In this scenario, the array is split into two equal parts (or close to equal), leading to a **O(n log n)** time complexity. 
  
  - **Example**: If the pivot is chosen optimally (such as the median), **QuickSort** will divide the array into roughly two halves at each recursive step, achieving the best-case performance.

- **Worst Case (O(n²))**: The **worst case** occurs when the **pivot** is consistently the largest or smallest element, which results in unbalanced partitions. In this case, **QuickSort** degrades to **O(n²)** because only one element is removed per partition, causing many recursive calls and making the algorithm inefficient.

  - **Example**: For a **sorted** or **reverse-sorted** array, if the pivot is always chosen as the first or last element, the partitions will be uneven, leading to poor performance.

### **4. ShellSort**

- **Best Case (O(n log n))**: The **best case** for **ShellSort** occurs when the data is already well-distributed according to the gap sequence. This allows the algorithm to perform efficiently, and in many cases, it can outperform **Insertion Sort**. The best-case time complexity can be close to **O(n log n)** depending on the gap sequence used.
  
  - **Example**: If the data is already partially sorted and the gap sequence is effective (e.g., using a sequence like the **Hibbard sequence**), **ShellSort** will perform much better than the simple **Insertion Sort**, approaching **O(n log n)** time complexity.

- **Worst Case (O(n²))**: The **worst case** for **ShellSort** occurs when the gap sequence is ineffective, and the data is poorly distributed. In this scenario, **ShellSort** degrades to a performance similar to **Insertion Sort**, which has a time complexity of **O(n²)**.
  
  - **Example**: If the array is unordered and the gap sequence is poorly chosen (e.g., using a simple sequence like a gap of 1), **ShellSort** may end up performing many comparisons and swaps, leading to a quadratic runtime.

---

### **Performance Impact**

The choice of sorting algorithm can significantly influence the program’s execution time, especially when processing large datasets or datasets with highly variable word frequencies.

- **HeapSort** and **MergeSort** offer stable performance with **O(n log n)** time complexity in the best, average, and worst cases. These algorithms are ideal when consistent performance is needed, especially for large datasets.
- **QuickSort** generally performs very well with **O(n log n)** average time complexity, but can degrade to **O(n²)** in the worst case, particularly when the pivot selection is poor (e.g., in nearly sorted or reverse-sorted arrays). Using strategies like random pivots or median-of-three selection can mitigate the worst-case scenario, ensuring that **QuickSort** performs better in most cases.
- **ShellSort** can outperform simpler algorithms like **Insertion Sort** in some cases, especially for smaller or partially sorted datasets, but its worst-case performance is **O(n²)**, making it less suitable for large datasets or highly unsorted data.

For applications processing **large datasets** or datasets where efficient partitioning is difficult, **MergeSort** or **HeapSort** are more predictable and reliable. If performance is critical and the data distribution is known or can be controlled (e.g., by using optimal pivot strategies), **QuickSort** might be the best choice. **ShellSort** can be a good alternative for smaller datasets or when a well-tuned gap sequence can be applied to improve its performance.

---

## **Acknowledgements**

- The **TF-IDF** algorithm is widely used in text analysis and information retrieval. It was first introduced by **Karen Spärck Jones** in 1972 and has since become a foundational technique in the field of information retrieval.
- The sorting algorithms implemented in this project, such as **QuickSort** and **MergeSort**, are standard in computer science for sorting data and ranking results.

---

## **Further Reading**

- [TF-IDF on Wikipedia (English)](https://en.wikipedia.org/wiki/Tf–idf#Term_frequency)
- [TF-IDF on Wikipedia (Portuguese)](https://pt.wikipedia.org/wiki/Tf–idf)
- [Introduction to Sorting Algorithms](https://en.wikipedia.org/wiki/Sorting_algorithm)
