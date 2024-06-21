# DSA Assignment 1 - Search in dynamic sets

In this project, we explore and compare the efficiency of different data structures for search operations, insertion,
and deletion in dynamic sets. We have implemented four distinct data structures and evaluated their performance in
various scenarios.

## Implemented Data Structures

### 1. AVL Tree 🌳

An AVL tree is a self-balancing binary search tree where the difference between heights of left and right subtrees
cannot be more than one for all nodes. The AVL tree ensures O(log n) time complexity for search, insertion, and deletion
operations.

### 2. Splay Tree 🌲

A Splay Tree is a self-adjusting binary search tree with the additional property that recently accessed elements are
quick to access again. This tree performs splaying, bringing the accessed node to the root, and can achieve O(log n)
amortized time complexity.

### 3. Hash Table with Chaining 🔗

This hash table resolves collisions using chaining, where each bucket is a linked list. If multiple elements hash to the
same bucket, they are added to the list at that bucket. This implementation includes dynamic resizing of the table to
maintain efficiency.

### 4. Hash Table with Linear Probing 📏

In this hash table, collisions are resolved using linear probing. When a collision occurs, the algorithm probes linearly
through the array to find the next empty slot. This implementation also supports dynamic resizing to ensure optimal
performance.

## Features

- **AVL Tree**: Maintains balance through rotations, ensuring efficient operations.
- **Splay Tree**: Self-adjusting to frequently accessed elements.
- **Hash Table with Chaining**: Efficient handling of collisions using linked lists.
- **Hash Table with Linear Probing**: Resolves collisions by finding the next available slot.

## Technical Documentation 📑

The technical documentation provides detailed characteristics and functionalities of each implemented data structure. It
includes a comprehensive testing program to evaluate the performance of each structure.

### Testing and Evaluation

- **Correctness Verification**: Ensuring the correctness through rigorous testing and comparison with standard
  implementations.
- **Performance Analysis**: Evaluating the efficiency of each data structure in different scenarios.
- **Testing Scenarios**: Documenting the situations where each implementation is most effective.

### Performance Metrics

- **Insertion**: Measuring the time taken to insert elements.
- **Deletion**: Evaluating the efficiency of deletion operations.
- **Search**: Assessing the speed and efficiency of search operations.

## How to Use 🏃

1. **Generate Datasets:**
   Make sure to generate datasets using the `DataGenerator` class. This will create the necessary data for testing and
   evaluation.
2. **Generate Datasets:**
   To run performance tests use class `Measure` class.
3. **Follow the documentation:**
   Documentation is
   accessible [here](https://github.com/RikoAppDev/binarytrees-and-hashtables/blob/main/Dokumentacia_DSA_P1.pdf)
