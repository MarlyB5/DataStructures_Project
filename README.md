# Data Structures & Sorting Performance Analysis

A Java project exploring the implementation, testing and performance of different **tree-based data structures and sorting algorithms**.

The project focuses primarily on the implementation of a **Treap** — a hybrid data structure combining the properties of a Binary Search Tree and a Heap — and compares its performance against an AVL Tree and Java's built-in `TreeMap`.

The project also benchmarks several sorting algorithms across different dataset sizes and input distributions to compare their practical performance with their theoretical time complexities.

---

## Project Overview

The main objectives of this project were to:

- Implement a generic Treap data structure from scratch
- Understand how Treaps combine Binary Search Tree and Heap properties
- Compare Treap performance against other tree-based map implementations
- Implement and compare several sorting algorithms
- Benchmark performance using different dataset sizes
- Investigate how input ordering affects algorithm performance
- Compare theoretical complexity with practical experimental results
- Develop experience with performance testing and algorithm analysis

---

# Technologies

- Java
- Java Collections Framework
- JUnit
- Git
- GitHub

---

# Data Structures Compared

The project compares three tree-based map implementations:

| Data Structure | Description |
|---|---|
| Treap | Custom implementation combining a Binary Search Tree with a Heap |
| AVLTreeMap | Custom self-balancing AVL tree implementation |
| TreeMap | Java's built-in ordered map implementation |

---

# Treap

The primary data structure implemented for the project was a **Treap**.

A Treap combines two different data structure properties:

1. **Binary Search Tree property** based on keys
2. **Heap property** based on randomly generated priorities

Each node therefore contains both:

```text
Key
Value
Priority
```

Conceptually:

```text
             (50, priority 10)
                /        \
               /          \
              v            v
      (30, priority 20)   (70, priority 15)
```

The keys follow the Binary Search Tree ordering property:

```text
Left Key < Parent Key < Right Key
```

while priorities maintain the Heap property.

The random priorities help prevent the tree from becoming severely unbalanced under many common input patterns.

---

# Treap Operations

The Treap implementation supports standard map and tree operations.

These include:

- Insertion
- Search
- Deletion
- In-order traversal
- Key/value storage
- Priority-based balancing

---

## Insertion

Insertion begins similarly to a normal Binary Search Tree.

The new node is positioned according to its key.

For example:

```text
Insert 40

        50
       /
      30

becomes:

        50
       /
      30
        \
         40
```

However, Treap nodes also contain priorities.

After insertion, the tree may need to perform rotations to restore the Heap property.

Conceptually:

```text
BST insertion
      |
      v
Insert node
      |
      v
Check priority
      |
      v
Heap property violated?
     / \
   Yes  No
    |    |
 Rotate  Done
    |
    v
Check again
```

This allows the Treap to maintain its expected balanced structure.

---

## Searching

Searching follows the Binary Search Tree property.

For a target key:

```text
Target < Current Key
        |
        v
       Left

Target > Current Key
        |
        v
       Right
```

This means searching does not need to inspect every element in the structure.

For a reasonably balanced Treap, search operations have an expected time complexity of:

```text
O(log n)
```

---

## Deletion

Deletion must preserve both:

- Binary Search Tree ordering
- Heap priority ordering

When a node is removed, rotations can be used to restructure the tree while maintaining the required Treap properties.

---

## In-Order Traversal

The project also evaluates in-order traversal.

An in-order traversal visits:

```text
Left Subtree
     |
     v
Current Node
     |
     v
Right Subtree
```

For a Binary Search Tree based structure, this produces the keys in sorted order.

For example:

```text
        5
       / \
      3   8
     /   / \
    1   6   10
```

In-order traversal produces:

```text
1, 3, 5, 6, 8, 10
```

Because every node must be visited, traversal has a time complexity of:

```text
O(n)
```

---

# Treap vs AVL Tree vs TreeMap

One of the main experiments compares the custom Treap implementation against:

- Custom `AVLTreeMap`
- Java `TreeMap`

The comparison investigates whether the theoretical properties of the different structures are reflected in their practical execution times.

---

## AVL Tree

An AVL Tree is a self-balancing Binary Search Tree.

It maintains a strict balance condition by monitoring the heights of its subtrees and performing rotations when required.

Its major operations generally have:

```text
Search:     O(log n)
Insertion:  O(log n)
Deletion:   O(log n)
```

---

## Java TreeMap

Java's `TreeMap` provides an ordered map implementation from the Java Collections Framework.

It provides logarithmic-time behaviour for major operations such as:

```text
get()
put()
remove()
```

It was included as a useful comparison between our custom data structure implementations and a standard Java library implementation.

---

# Operations Benchmarked

The tree structures were compared using several operations.

## Insertion

Insertion performance was measured for adding elements to each structure.

Both individual and batch insertion behaviour were investigated.

---

## Successful Search

The structures were tested when searching for keys that were known to exist.

Example:

```text
Dataset:

10, 20, 30, 40, 50

Search:

30

Result:

Found
```

---

## Unsuccessful Search

Search performance was also tested for keys that did not exist.

Example:

```text
Dataset:

10, 20, 30, 40, 50

Search:

35

Result:

Not Found
```

This allowed the project to investigate whether successful and unsuccessful searches produced different practical performance characteristics.

---

## Deletion

Deletion performance was measured by removing elements from each data structure.

---

## Traversal

In-order traversal performance was also measured.

This requires visiting every element in the tree and therefore provides a different workload from search, insertion and deletion.

---

# Dataset Sizes

Benchmarks were performed using increasing dataset sizes.

The tested range included datasets from approximately:

```text
100
...
1,000
...
10,000
```

elements.

Testing multiple sizes allowed the project to examine how each implementation scales as the amount of data increases.

---

# Input Types

Performance was tested using different input arrangements.

These included:

### Random Data

```text
42, 7, 91, 15, 63, 2, 78...
```

### Ascending / Sorted Data

```text
1, 2, 3, 4, 5, 6, 7...
```

### Descending / Reverse-Sorted Data

```text
7, 6, 5, 4, 3, 2, 1...
```

### Partially / Nearly Sorted Data

```text
1, 2, 4, 3, 5, 6, 8, 7...
```

Using different input distributions is important because the ordering of data can significantly affect the behaviour of some algorithms and data structures.

---

# Sorting Algorithm Comparison

The second major part of the project compares several sorting approaches.

The algorithms evaluated include:

- TreapSort
- Priority Queue Sort (PQSort)
- Java / Collections Sort
- Quick Sort
- Merge Sort

---

# TreapSort

TreapSort uses the Treap data structure to produce a sorted result.

The general process is:

```text
Unsorted Data
     |
     v
Insert into Treap
     |
     v
Treap Structure
     |
     v
In-Order Traversal
     |
     v
Sorted Data
```

Because an in-order traversal of the Treap visits keys in sorted order, the structure can be used as the basis of a sorting algorithm.

---

# Priority Queue Sort

Priority Queue Sort inserts elements into a priority queue and then repeatedly removes the next ordered element.

Conceptually:

```text
Unsorted Array
      |
      v
Priority Queue
      |
      v
Repeated removal
      |
      v
Sorted Array
```

This provides another data-structure-based approach to sorting.

---

# Quick Sort

Quick Sort uses a divide-and-conquer approach.

A pivot is selected and the dataset is partitioned around it.

```text
             Array
               |
               v
             Pivot
             /   \
            /     \
           v       v
       Smaller    Larger
          |          |
          v          v
       QuickSort  QuickSort
```

Its average time complexity is:

```text
O(n log n)
```

although its performance can depend on factors such as pivot selection and input ordering.

---

# Merge Sort

Merge Sort also follows a divide-and-conquer approach.

The dataset is repeatedly divided into smaller sections before those sections are merged back together in sorted order.

```text
        [8, 3, 5, 1]
             |
        +----+----+
        |         |
      [8,3]     [5,1]
       / \       / \
     [8] [3]   [5] [1]
       \ /       \ /
      [3,8]     [1,5]
          \       /
           \     /
         [1,3,5,8]
```

Merge Sort has a time complexity of:

```text
O(n log n)
```

---

# Java / Collections Sort

Java's standard sorting implementation was included as a baseline against which the custom sorting implementations could be compared.

This helps demonstrate the difference between:

- Implementing algorithms for educational purposes
- Highly optimised library implementations used in production software

---

# Benchmarking Methodology

To make the performance comparison more reliable, benchmarks were run multiple times rather than relying on a single execution.

The general benchmarking process was:

```text
Generate Dataset
      |
      v
Warm-up Runs
      |
      v
Run Algorithm
      |
      v
Measure Execution Time
      |
      v
Repeat Multiple Times
      |
      v
Calculate Average
      |
      v
Compare Results
```

Using repeated measurements reduces the impact of individual timing anomalies.

Warm-up runs were also used before measured runs to reduce the influence of Java Virtual Machine startup and optimisation behaviour.

Where possible, the same datasets were used across implementations to provide a fair comparison.

---

# Testing

Unit testing was used to verify the correctness of the implemented data structures and algorithms.

Tests covered individual methods as well as important edge cases.

Testing was particularly important because benchmark results are only meaningful if each implementation produces correct results.

Areas tested included:

- Treap insertion
- Treap search
- Treap deletion
- Treap traversal
- Tree ordering
- Priority behaviour
- Empty structures
- Single-element structures
- Larger datasets
- Sorting correctness
- Edge cases

---

# Performance Analysis

The purpose of the benchmarks was not simply to determine which implementation was "fastest".

The project instead investigated the relationship between:

```text
Theoretical Complexity
          +
Implementation Details
          +
Input Distribution
          +
Dataset Size
          |
          v
Observed Performance
```

Two algorithms with the same Big-O complexity can still produce different execution times because Big-O describes how performance scales rather than the exact amount of time an implementation requires.

Factors such as:

- Constant factors
- Memory access
- Tree rotations
- Library optimisation
- Input ordering
- Randomisation
- Java Virtual Machine optimisation

can influence practical performance.

---

# Time Complexity Comparison

| Algorithm / Structure | Search | Insert | Delete | Sorting |
|---|---:|---:|---:|---:|
| Treap | Expected O(log n) | Expected O(log n) | Expected O(log n) | O(n log n) expected |
| AVL Tree | O(log n) | O(log n) | O(log n) | — |
| TreeMap | O(log n) | O(log n) | O(log n) | — |
| TreapSort | — | — | — | O(n log n) expected |
| PQSort | — | — | — | O(n log n) |
| Quick Sort | — | — | — | O(n log n) average |
| Merge Sort | — | — | — | O(n log n) |

> The exact practical performance of each implementation depends on the dataset, input ordering, implementation details and runtime environment.

---

# Project Architecture

A simplified structure of the project is:

```text
                   DATA STRUCTURES
                         |
          +--------------+--------------+
          |              |              |
          v              v              v
        Treap       AVLTreeMap       TreeMap
          |
          |
          +------------------------------+
                                         |
                                         v
                                  Benchmarking
                                         |
                                         v
                                      Results


                SORTING ALGORITHMS
                         |
       +---------+-------+-------+---------+
       |         |       |       |         |
       v         v       v       v         v
    TreapSort  PQSort  Quick   Merge   Java Sort
                         Sort    Sort
       |         |       |       |         |
       +---------+-------+-------+---------+
                         |
                         v
                    Benchmarking
                         |
                         v
                       Results
```

---

# Key Concepts Demonstrated

This project demonstrates practical experience with several important computer science concepts.

## Data Structures

- Binary Search Trees
- Treaps
- AVL Trees
- Priority Queues
- Maps
- Tree traversal

## Algorithms

- Searching
- Insertion
- Deletion
- Tree rotations
- Quick Sort
- Merge Sort
- TreapSort
- Priority Queue Sort

## Algorithm Analysis

- Big-O notation
- Average-case complexity
- Performance benchmarking
- Scalability
- Input distribution
- Experimental analysis

## Software Development

- Java generics
- Object-oriented programming
- Unit testing
- Git version control
- Performance measurement
- Experimental design

---

# What I Learned

This project strengthened my understanding of the relationship between **theoretical algorithm complexity and real-world performance**.

Implementing a Treap provided practical experience with maintaining multiple data structure invariants simultaneously, as the structure must satisfy both Binary Search Tree ordering and Heap priority properties.

Comparing the Treap against AVLTreeMap and Java TreeMap also demonstrated that Big-O complexity alone does not completely determine practical performance.

The sorting experiments further demonstrated how dataset size, input ordering, algorithm design and implementation details can influence execution time.

The project also developed my experience with:

- Implementing non-trivial data structures from scratch
- Working with generic Java classes
- Designing unit tests
- Creating repeatable performance experiments
- Analysing benchmark results
- Comparing theoretical and experimental performance

---

# Future Improvements

Possible extensions to the project include:

- Testing significantly larger datasets
- Additional input distributions
- Memory usage profiling
- Visualising benchmark results automatically
- Comparing additional balanced search trees
- Comparing additional sorting algorithms
- Using a dedicated Java benchmarking framework
- Investigating the effects of different Treap priority-generation strategies

---

# Conclusion

This project explores how different data structures and algorithms behave both theoretically and practically.

By implementing a Treap and comparing it with AVLTreeMap and Java TreeMap, the project demonstrates the trade-offs between different approaches to maintaining ordered data.

The sorting comparison extends this analysis by examining how multiple O(n log n) sorting approaches can still behave differently under real-world conditions.

Overall, the project demonstrates practical application of **data structures, sorting algorithms, algorithm analysis, Java programming, unit testing and performance benchmarking**.
