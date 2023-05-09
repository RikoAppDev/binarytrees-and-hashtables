import binarytree.AVL;
import binarytree.Splay;
import hashtable.HashTableChaining;
import hashtable.HashTableLinearProbing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static hashtable.HashTableChaining.*;

public class Measure {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_CYAN = "\u001B[36m";

    private long insertDuration;
    private long searchDuration;
    private long deleteDuration;

    private long totalInsertTime;
    private long totalSearchTime;
    private long totalDeleteTime;

    private long avgInsertTime;
    private long avgSearchTime;
    private long avgDeleteTime;
    private long minInsertTime;
    private long minSearchTime;
    private long minDeleteTime;
    private long maxInsertTime;
    private long maxSearchTime;
    private long maxDeleteTime;

    private int measureCount;

    public void insertAvl(int input, AVL avl) {
        long startAvl = System.nanoTime();
        avl.insertValue(input);
        long endAvl = System.nanoTime();
        insertDuration = endAvl - startAvl;
    }

    public void insertSplay(int input, Splay splay) {
        long startSplay = System.nanoTime();
        splay.insertValue(input);
        long endSplay = System.nanoTime();
        insertDuration = endSplay - startSplay;
    }

    public void insertHTChaining(Entry entry, HashTableChaining tableChaining) {
        long startHTC = System.nanoTime();
        tableChaining.insertValue(entry);
        long endHTC = System.nanoTime();
        insertDuration = endHTC - startHTC;
    }

    public void insertHTLProbing(Entry entry, HashTableLinearProbing tableLinearProbing) {
        long startHTL = System.nanoTime();
        tableLinearProbing.insertValue(entry);
        long endHTL = System.nanoTime();
        insertDuration = endHTL - startHTL;
    }

    public void searchAvl(int input, AVL avl) {
        long startAvl = System.nanoTime();
        avl.searchValue(input);
        long endAvl = System.nanoTime();
        searchDuration = endAvl - startAvl;
    }

    public void searchSplay(int input, Splay splay) {
        long startSplay = System.nanoTime();
        splay.searchValue(input);
        long endSplay = System.nanoTime();
        searchDuration = endSplay - startSplay;
    }

    public void searchHTChaining(Entry entry, HashTableChaining tableChaining) {
        long startHTC = System.nanoTime();
        tableChaining.searchValue(entry);
        long endHTC = System.nanoTime();
        searchDuration = endHTC - startHTC;
    }

    public void searchHTLProbing(Entry entry, HashTableLinearProbing tableLinearProbing) {
        long startHTL = System.nanoTime();
        tableLinearProbing.searchValue(entry);
        long endHTL = System.nanoTime();
        searchDuration = endHTL - startHTL;
    }

    public void deleteAvl(int input, AVL avl) {
        long startAvl = System.nanoTime();
        avl.deleteValue(input);
        long endAvl = System.nanoTime();
        deleteDuration = endAvl - startAvl;
    }

    public void deleteSplay(int input, Splay splay) {
        long startSplay = System.nanoTime();
        splay.deleteValue(input);
        long endSplay = System.nanoTime();
        deleteDuration = endSplay - startSplay;
    }

    public void deleteHTChaining(Entry entry, HashTableChaining tableChaining) {
        long startHTC = System.nanoTime();
        tableChaining.deleteValue(entry);
        long endHTC = System.nanoTime();
        deleteDuration = endHTC - startHTC;
    }

    public void deleteHTLProbing(Entry entry, HashTableLinearProbing tableLinearProbing) {
        long startHTL = System.nanoTime();
        tableLinearProbing.deleteValue(entry);
        long endHTL = System.nanoTime();
        deleteDuration = endHTL - startHTL;
    }

    public void createAvl(List<Integer> data, AVL avl) {
        avl.insert(data);
    }

    public void createSplay(List<Integer> data, Splay splay) {
        splay.insert(data);
    }

    public void createHTChaining(List<Entry> data, HashTableChaining tableChaining) {
        tableChaining.insert(data);
    }

    public void createHTLProbing(List<Entry> data, HashTableLinearProbing tableLinearProbing) {
        tableLinearProbing.insert(data);
    }

    public void startSingleMeasureAvl(int data, AVL avl, int dataSize, int datasetNumber) {
        List<Long> insertTime = new ArrayList<>();
        List<Long> searchTime = new ArrayList<>();
        List<Long> deleteTime = new ArrayList<>();
        long sumInsert = 0;
        long sumSearch = 0;
        long sumDelete = 0;

        insertAvl(data, avl);
        searchAvl(data, avl);
        deleteAvl(data, avl);

        insertTime.add(insertDuration);
        sumInsert += insertDuration;
        minInsertTime = insertDuration;
        maxInsertTime = insertDuration;

        searchTime.add(searchDuration);
        sumSearch += searchDuration;
        minSearchTime = searchDuration;
        maxSearchTime = searchDuration;

        deleteTime.add(deleteDuration);
        sumDelete += deleteDuration;
        minDeleteTime = deleteDuration;
        maxDeleteTime = deleteDuration;

        for (int i = 1; i < measureCount; i++) {
            insertAvl(data, avl);
            searchAvl(data, avl);
            deleteAvl(data, avl);

            insertTime.add(insertDuration);
            searchTime.add(searchDuration);
            deleteTime.add(deleteDuration);
            sumInsert += insertDuration;
            sumSearch += searchDuration;
            sumDelete += deleteDuration;

            if (insertDuration < minInsertTime) {
                minInsertTime = insertDuration;
            } else if (insertDuration > maxInsertTime) {
                maxInsertTime = insertDuration;
            }
            if (searchDuration < minSearchTime) {
                minSearchTime = searchDuration;
            } else if (searchDuration > maxSearchTime) {
                maxSearchTime = searchDuration;
            }
            if (deleteDuration < minDeleteTime) {
                minDeleteTime = deleteDuration;
            } else if (deleteDuration > maxDeleteTime) {
                maxDeleteTime = deleteDuration;
            }
        }

        totalInsertTime = sumInsert;
        totalSearchTime = sumSearch;
        totalDeleteTime = sumDelete;
        avgInsertTime = sumInsert / insertTime.size();
        avgSearchTime = sumSearch / searchTime.size();
        avgDeleteTime = sumDelete / deleteTime.size();

        printAvlMeasure(dataSize, datasetNumber, true);
    }

    public void startSingleMeasureSplay(int data, Splay splay, int dataSize, int datasetNumber) {
        List<Long> insertTime = new ArrayList<>();
        List<Long> searchTime = new ArrayList<>();
        List<Long> deleteTime = new ArrayList<>();
        long sumInsert = 0;
        long sumSearch = 0;
        long sumDelete = 0;

        insertSplay(data, splay);
        searchSplay(data, splay);
        deleteSplay(data, splay);

        insertTime.add(insertDuration);
        sumInsert += insertDuration;
        minInsertTime = insertDuration;
        maxInsertTime = insertDuration;

        searchTime.add(searchDuration);
        sumSearch += searchDuration;
        minSearchTime = searchDuration;
        maxSearchTime = searchDuration;

        deleteTime.add(deleteDuration);
        sumDelete += deleteDuration;
        minDeleteTime = deleteDuration;
        maxDeleteTime = deleteDuration;

        for (int i = 1; i < measureCount; i++) {
            insertSplay(data, splay);
            searchSplay(data, splay);
            deleteSplay(data, splay);

            insertTime.add(insertDuration);
            searchTime.add(searchDuration);
            deleteTime.add(deleteDuration);
            sumInsert += insertDuration;
            sumSearch += searchDuration;
            sumDelete += deleteDuration;

            if (insertDuration < minInsertTime) {
                minInsertTime = insertDuration;
            } else if (insertDuration > maxInsertTime) {
                maxInsertTime = insertDuration;
            }
            if (searchDuration < minSearchTime) {
                minSearchTime = searchDuration;
            } else if (searchDuration > maxSearchTime) {
                maxSearchTime = searchDuration;
            }
            if (deleteDuration < minDeleteTime) {
                minDeleteTime = deleteDuration;
            } else if (deleteDuration > maxDeleteTime) {
                maxDeleteTime = deleteDuration;
            }
        }

        totalInsertTime = sumInsert;
        totalSearchTime = sumSearch;
        totalDeleteTime = sumDelete;
        avgInsertTime = sumInsert / insertTime.size();
        avgSearchTime = sumSearch / searchTime.size();
        avgDeleteTime = sumDelete / deleteTime.size();

        printSplayMeasure(dataSize, datasetNumber, true);
    }

    public void startSingleMeasureHTC(Entry entry, HashTableChaining tableChaining, int dataSize, int datasetNumber) {
        List<Long> insertTime = new ArrayList<>();
        List<Long> searchTime = new ArrayList<>();
        List<Long> deleteTime = new ArrayList<>();
        long sumInsert = 0;
        long sumSearch = 0;
        long sumDelete = 0;

        insertHTChaining(entry, tableChaining);
        searchHTChaining(entry, tableChaining);
        deleteHTChaining(entry, tableChaining);

        insertTime.add(insertDuration);
        sumInsert += insertDuration;
        minInsertTime = insertDuration;
        maxInsertTime = insertDuration;

        searchTime.add(searchDuration);
        sumSearch += searchDuration;
        minSearchTime = searchDuration;
        maxSearchTime = searchDuration;

        deleteTime.add(deleteDuration);
        sumDelete += deleteDuration;
        minDeleteTime = deleteDuration;
        maxDeleteTime = deleteDuration;

        for (int i = 1; i < measureCount; i++) {
            insertHTChaining(entry, tableChaining);
            searchHTChaining(entry, tableChaining);
            deleteHTChaining(entry, tableChaining);

            insertTime.add(insertDuration);
            searchTime.add(searchDuration);
            deleteTime.add(deleteDuration);
            sumInsert += insertDuration;
            sumSearch += searchDuration;
            sumDelete += deleteDuration;

            if (insertDuration < minInsertTime) {
                minInsertTime = insertDuration;
            } else if (insertDuration > maxInsertTime) {
                maxInsertTime = insertDuration;
            }
            if (searchDuration < minSearchTime) {
                minSearchTime = searchDuration;
            } else if (searchDuration > maxSearchTime) {
                maxSearchTime = searchDuration;
            }
            if (deleteDuration < minDeleteTime) {
                minDeleteTime = deleteDuration;
            } else if (deleteDuration > maxDeleteTime) {
                maxDeleteTime = deleteDuration;
            }
        }

        totalInsertTime = sumInsert;
        totalSearchTime = sumSearch;
        totalDeleteTime = sumDelete;
        avgInsertTime = sumInsert / insertTime.size();
        avgSearchTime = sumSearch / searchTime.size();
        avgDeleteTime = sumDelete / deleteTime.size();

        printHTCMeasure(dataSize, datasetNumber, true);
    }

    public void startSingleMeasureHTL(Entry entry, HashTableLinearProbing tableLinearProbing, int dataSize, int datasetNumber) {
        List<Long> insertTime = new ArrayList<>();
        List<Long> searchTime = new ArrayList<>();
        List<Long> deleteTime = new ArrayList<>();
        long sumInsert = 0;
        long sumSearch = 0;
        long sumDelete = 0;

        insertHTLProbing(entry, tableLinearProbing);
        searchHTLProbing(entry, tableLinearProbing);
        deleteHTLProbing(entry, tableLinearProbing);

        insertTime.add(insertDuration);
        sumInsert += insertDuration;
        minInsertTime = insertDuration;
        maxInsertTime = insertDuration;

        searchTime.add(searchDuration);
        sumSearch += searchDuration;
        minSearchTime = searchDuration;
        maxSearchTime = searchDuration;

        deleteTime.add(deleteDuration);
        sumDelete += deleteDuration;
        minDeleteTime = deleteDuration;
        maxDeleteTime = deleteDuration;

        for (int i = 1; i < measureCount; i++) {
            insertHTLProbing(entry, tableLinearProbing);
            searchHTLProbing(entry, tableLinearProbing);
            deleteHTLProbing(entry, tableLinearProbing);

            insertTime.add(insertDuration);
            searchTime.add(searchDuration);
            deleteTime.add(deleteDuration);
            sumInsert += insertDuration;
            sumSearch += searchDuration;
            sumDelete += deleteDuration;

            if (insertDuration < minInsertTime) {
                minInsertTime = insertDuration;
            } else if (insertDuration > maxInsertTime) {
                maxInsertTime = insertDuration;
            }
            if (searchDuration < minSearchTime) {
                minSearchTime = searchDuration;
            } else if (searchDuration > maxSearchTime) {
                maxSearchTime = searchDuration;
            }
            if (deleteDuration < minDeleteTime) {
                minDeleteTime = deleteDuration;
            } else if (deleteDuration > maxDeleteTime) {
                maxDeleteTime = deleteDuration;
            }
        }

        totalInsertTime = sumInsert;
        totalSearchTime = sumSearch;
        totalDeleteTime = sumDelete;
        avgInsertTime = sumInsert / insertTime.size();
        avgSearchTime = sumSearch / searchTime.size();
        avgDeleteTime = sumDelete / deleteTime.size();

        printHTLMeasure(dataSize, datasetNumber, true);
    }

    public void startFullMeasureAvl(List<Integer> data, AVL avl, int datasetNumber) {
        List<Long> insertTime = new ArrayList<>();
        List<Long> searchTime = new ArrayList<>();
        List<Long> deleteTime = new ArrayList<>();
        long sumInsert = 0;
        long sumSearch = 0;
        long sumDelete = 0;

        Instant insertStart = Instant.now();
        avl.insert(data);
        Instant insertEnd = Instant.now();
        insertDuration = Duration.between(insertStart, insertEnd).toMillis();

        insertTime.add(insertDuration);
        sumInsert += insertDuration;
        minInsertTime = insertDuration;
        maxInsertTime = insertDuration;

        Instant searchStart = Instant.now();
        avl.search(data);
        Instant searchEnd = Instant.now();
        searchDuration = Duration.between(searchStart, searchEnd).toMillis();

        searchTime.add(searchDuration);
        sumSearch += searchDuration;
        minSearchTime = searchDuration;
        maxSearchTime = searchDuration;

        Instant deleteStart = Instant.now();
        avl.delete(data);
        Instant deleteEnd = Instant.now();
        deleteDuration = Duration.between(deleteStart, deleteEnd).toMillis();

        deleteTime.add(deleteDuration);
        sumDelete += deleteDuration;
        minDeleteTime = deleteDuration;
        maxDeleteTime = deleteDuration;

        for (int i = 1; i < measureCount; i++) {
            insertStart = Instant.now();
            avl.insert(data);
            insertEnd = Instant.now();
            insertDuration = Duration.between(insertStart, insertEnd).toMillis();

            searchStart = Instant.now();
            avl.search(data);
            searchEnd = Instant.now();
            searchDuration = Duration.between(searchStart, searchEnd).toMillis();

            deleteStart = Instant.now();
            avl.delete(data);
            deleteEnd = Instant.now();
            deleteDuration = Duration.between(deleteStart, deleteEnd).toMillis();

            insertTime.add(insertDuration);
            searchTime.add(searchDuration);
            deleteTime.add(deleteDuration);
            sumInsert += insertDuration;
            sumSearch += searchDuration;
            sumDelete += deleteDuration;

            if (insertDuration < minInsertTime) {
                minInsertTime = insertDuration;
            } else if (insertDuration > maxInsertTime) {
                maxInsertTime = insertDuration;
            }
            if (searchDuration < minSearchTime) {
                minSearchTime = searchDuration;
            } else if (searchDuration > maxSearchTime) {
                maxSearchTime = searchDuration;
            }
            if (deleteDuration < minDeleteTime) {
                minDeleteTime = deleteDuration;
            } else if (deleteDuration > maxDeleteTime) {
                maxDeleteTime = deleteDuration;
            }
        }

        totalInsertTime = sumInsert;
        totalSearchTime = sumSearch;
        totalDeleteTime = sumDelete;
        avgInsertTime = sumInsert / insertTime.size();
        avgSearchTime = sumSearch / searchTime.size();
        avgDeleteTime = sumDelete / deleteTime.size();

        printAvlMeasure(data.size(), datasetNumber, false);
    }

    public void startFullMeasureSplay(List<Integer> data, Splay splay, int datasetNumber) {
        List<Long> insertTime = new ArrayList<>();
        List<Long> searchTime = new ArrayList<>();
        List<Long> deleteTime = new ArrayList<>();
        long sumInsert = 0;
        long sumSearch = 0;
        long sumDelete = 0;

        Instant insertStart = Instant.now();
        splay.insert(data);
        Instant insertEnd = Instant.now();
        insertDuration = Duration.between(insertStart, insertEnd).toMillis();

        insertTime.add(insertDuration);
        sumInsert += insertDuration;
        minInsertTime = insertDuration;
        maxInsertTime = insertDuration;

        Instant searchStart = Instant.now();
        splay.search(data);
        Instant searchEnd = Instant.now();
        searchDuration = Duration.between(searchStart, searchEnd).toMillis();

        searchTime.add(searchDuration);
        sumSearch += searchDuration;
        minSearchTime = searchDuration;
        maxSearchTime = searchDuration;

        Instant deleteStart = Instant.now();
        splay.delete(data);
        Instant deleteEnd = Instant.now();
        deleteDuration = Duration.between(deleteStart, deleteEnd).toMillis();

        deleteTime.add(deleteDuration);
        sumDelete += deleteDuration;
        minDeleteTime = deleteDuration;
        maxDeleteTime = deleteDuration;

        for (int i = 1; i < measureCount; i++) {
            insertStart = Instant.now();
            splay.insert(data);
            insertEnd = Instant.now();
            insertDuration = Duration.between(insertStart, insertEnd).toMillis();

            searchStart = Instant.now();
            splay.search(data);
            searchEnd = Instant.now();
            searchDuration = Duration.between(searchStart, searchEnd).toMillis();

            deleteStart = Instant.now();
            splay.delete(data);
            deleteEnd = Instant.now();
            deleteDuration = Duration.between(deleteStart, deleteEnd).toMillis();

            insertTime.add(insertDuration);
            searchTime.add(searchDuration);
            deleteTime.add(deleteDuration);
            sumInsert += insertDuration;
            sumSearch += searchDuration;
            sumDelete += deleteDuration;

            if (insertDuration < minInsertTime) {
                minInsertTime = insertDuration;
            } else if (insertDuration > maxInsertTime) {
                maxInsertTime = insertDuration;
            }
            if (searchDuration < minSearchTime) {
                minSearchTime = searchDuration;
            } else if (searchDuration > maxSearchTime) {
                maxSearchTime = searchDuration;
            }
            if (deleteDuration < minDeleteTime) {
                minDeleteTime = deleteDuration;
            } else if (deleteDuration > maxDeleteTime) {
                maxDeleteTime = deleteDuration;
            }
        }

        totalInsertTime = sumInsert;
        totalSearchTime = sumSearch;
        totalDeleteTime = sumDelete;
        avgInsertTime = sumInsert / insertTime.size();
        avgSearchTime = sumSearch / searchTime.size();
        avgDeleteTime = sumDelete / deleteTime.size();

        printSplayMeasure(data.size(), datasetNumber, false);
    }

    public void startFullMeasureHTC(List<Entry> data, HashTableChaining tableChaining, int datasetNumber) {
        List<Long> insertTime = new ArrayList<>();
        List<Long> searchTime = new ArrayList<>();
        List<Long> deleteTime = new ArrayList<>();
        long sumInsert = 0;
        long sumSearch = 0;
        long sumDelete = 0;

        Instant insertStart = Instant.now();
        tableChaining.insert(data);
        Instant insertEnd = Instant.now();
        insertDuration = Duration.between(insertStart, insertEnd).toMillis();

        insertTime.add(insertDuration);
        sumInsert += insertDuration;
        minInsertTime = insertDuration;
        maxInsertTime = insertDuration;

        Instant searchStart = Instant.now();
        tableChaining.search(data);
        Instant searchEnd = Instant.now();
        searchDuration = Duration.between(searchStart, searchEnd).toMillis();

        searchTime.add(searchDuration);
        sumSearch += searchDuration;
        minSearchTime = searchDuration;
        maxSearchTime = searchDuration;

        Instant deleteStart = Instant.now();
        tableChaining.delete(data);
        Instant deleteEnd = Instant.now();
        deleteDuration = Duration.between(deleteStart, deleteEnd).toMillis();

        deleteTime.add(deleteDuration);
        sumDelete += deleteDuration;
        minDeleteTime = deleteDuration;
        maxDeleteTime = deleteDuration;

        for (int i = 1; i < measureCount; i++) {
            insertStart = Instant.now();
            tableChaining.insert(data);
            insertEnd = Instant.now();
            insertDuration = Duration.between(insertStart, insertEnd).toMillis();

            searchStart = Instant.now();
            tableChaining.search(data);
            searchEnd = Instant.now();
            searchDuration = Duration.between(searchStart, searchEnd).toMillis();

            deleteStart = Instant.now();
            tableChaining.delete(data);
            deleteEnd = Instant.now();
            deleteDuration = Duration.between(deleteStart, deleteEnd).toMillis();

            insertTime.add(insertDuration);
            searchTime.add(searchDuration);
            deleteTime.add(deleteDuration);
            sumInsert += insertDuration;
            sumSearch += searchDuration;
            sumDelete += deleteDuration;

            if (insertDuration < minInsertTime) {
                minInsertTime = insertDuration;
            } else if (insertDuration > maxInsertTime) {
                maxInsertTime = insertDuration;
            }
            if (searchDuration < minSearchTime) {
                minSearchTime = searchDuration;
            } else if (searchDuration > maxSearchTime) {
                maxSearchTime = searchDuration;
            }
            if (deleteDuration < minDeleteTime) {
                minDeleteTime = deleteDuration;
            } else if (deleteDuration > maxDeleteTime) {
                maxDeleteTime = deleteDuration;
            }
        }

        totalInsertTime = sumInsert;
        totalSearchTime = sumSearch;
        totalDeleteTime = sumDelete;
        avgInsertTime = sumInsert / insertTime.size();
        avgSearchTime = sumSearch / searchTime.size();
        avgDeleteTime = sumDelete / deleteTime.size();

        printHTCMeasure(data.size(), datasetNumber, false);
    }

    public void startFullMeasureHTL(List<Entry> data, HashTableLinearProbing tableLinearProbing, int datasetNumber) {
        List<Long> insertTime = new ArrayList<>();
        List<Long> searchTime = new ArrayList<>();
        List<Long> deleteTime = new ArrayList<>();
        long sumInsert = 0;
        long sumSearch = 0;
        long sumDelete = 0;

        Instant insertStart = Instant.now();
        tableLinearProbing.insert(data);
        Instant insertEnd = Instant.now();
        insertDuration = Duration.between(insertStart, insertEnd).toMillis();

        insertTime.add(insertDuration);
        sumInsert += insertDuration;
        minInsertTime = insertDuration;
        maxInsertTime = insertDuration;

        Instant searchStart = Instant.now();
        tableLinearProbing.search(data);
        Instant searchEnd = Instant.now();
        searchDuration = Duration.between(searchStart, searchEnd).toMillis();

        searchTime.add(searchDuration);
        sumSearch += searchDuration;
        minSearchTime = searchDuration;
        maxSearchTime = searchDuration;

        Instant deleteStart = Instant.now();
        tableLinearProbing.delete(data);
        Instant deleteEnd = Instant.now();
        deleteDuration = Duration.between(deleteStart, deleteEnd).toMillis();

        deleteTime.add(deleteDuration);
        sumDelete += deleteDuration;
        minDeleteTime = deleteDuration;
        maxDeleteTime = deleteDuration;

        for (int i = 1; i < measureCount; i++) {
            insertStart = Instant.now();
            tableLinearProbing.insert(data);
            insertEnd = Instant.now();
            insertDuration = Duration.between(insertStart, insertEnd).toMillis();

            searchStart = Instant.now();
            tableLinearProbing.search(data);
            searchEnd = Instant.now();
            searchDuration = Duration.between(searchStart, searchEnd).toMillis();

            deleteStart = Instant.now();
            tableLinearProbing.delete(data);
            deleteEnd = Instant.now();
            deleteDuration = Duration.between(deleteStart, deleteEnd).toMillis();

            insertTime.add(insertDuration);
            searchTime.add(searchDuration);
            deleteTime.add(deleteDuration);
            sumInsert += insertDuration;
            sumSearch += searchDuration;
            sumDelete += deleteDuration;

            if (insertDuration < minInsertTime) {
                minInsertTime = insertDuration;
            } else if (insertDuration > maxInsertTime) {
                maxInsertTime = insertDuration;
            }
            if (searchDuration < minSearchTime) {
                minSearchTime = searchDuration;
            } else if (searchDuration > maxSearchTime) {
                maxSearchTime = searchDuration;
            }
            if (deleteDuration < minDeleteTime) {
                minDeleteTime = deleteDuration;
            } else if (deleteDuration > maxDeleteTime) {
                maxDeleteTime = deleteDuration;
            }
        }

        totalInsertTime = sumInsert;
        totalSearchTime = sumSearch;
        totalDeleteTime = sumDelete;
        avgInsertTime = sumInsert / insertTime.size();
        avgSearchTime = sumSearch / searchTime.size();
        avgDeleteTime = sumDelete / deleteTime.size();

        printHTLMeasure(data.size(), datasetNumber, false);
    }

    private void printAvlMeasure(int dataSize, int i, boolean nanos) {
        System.out.println("-> dataset " + (i + 1) + "(" + ANSI_CYAN + (dataSize) + ANSI_RESET + ")" + " AVL total time: " + ANSI_CYAN + (totalInsertTime + totalSearchTime + totalDeleteTime) + ((nanos) ? "nanos" : "ms") + ANSI_RESET);
        System.out.println("\tAVL insert ==> " +
                "min duration: " + ANSI_GREEN + minInsertTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | avg duration: " + ANSI_YELLOW + avgInsertTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | max duration: " + ANSI_RED + maxInsertTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | total time for " + ANSI_CYAN + measureCount + ANSI_RESET + " repeats: " + ANSI_CYAN + totalInsertTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET);
        System.out.println("\tAVL search ==> " +
                "min duration: " + ANSI_GREEN + minSearchTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | avg duration: " + ANSI_YELLOW + avgSearchTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | max duration: " + ANSI_RED + maxSearchTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | total time for " + ANSI_CYAN + measureCount + ANSI_RESET + " repeats: " + ANSI_CYAN + totalSearchTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET);
        System.out.println("\tAVL delete ==> " +
                "min duration: " + ANSI_GREEN + minDeleteTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | avg duration: " + ANSI_YELLOW + avgDeleteTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | max duration: " + ANSI_RED + maxDeleteTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | total time for " + ANSI_CYAN + measureCount + ANSI_RESET + " repeats: " + ANSI_CYAN + totalDeleteTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET);
        System.out.println();
    }

    private void printSplayMeasure(int dataSize, int i, boolean nanos) {
        System.out.println("-> dataset " + (i + 1) + "(" + ANSI_CYAN + (dataSize) + ANSI_RESET + ")" + " Splay total time: " + ANSI_CYAN + (totalInsertTime + totalSearchTime + totalDeleteTime) + ((nanos) ? "nanos" : "ms") + ANSI_RESET);
        System.out.println("\tSplay insert ==> " +
                "min duration: " + ANSI_GREEN + minInsertTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | avg duration: " + ANSI_YELLOW + avgInsertTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | max duration: " + ANSI_RED + maxInsertTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | total time for " + ANSI_CYAN + measureCount + ANSI_RESET + " repeats: " + ANSI_CYAN + totalInsertTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET);
        System.out.println("\tSplay search ==> " +
                "min duration: " + ANSI_GREEN + minSearchTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | avg duration: " + ANSI_YELLOW + avgSearchTime + "ms" + ANSI_RESET +
                " | max duration: " + ANSI_RED + maxSearchTime + "ms" + ANSI_RESET +
                " | total time for " + ANSI_CYAN + measureCount + ANSI_RESET + " repeats: " + ANSI_CYAN + totalSearchTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET);
        System.out.println("\tSplay delete ==> " +
                "min duration: " + ANSI_GREEN + minDeleteTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | avg duration: " + ANSI_YELLOW + avgDeleteTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | max duration: " + ANSI_RED + maxDeleteTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | total time for " + ANSI_CYAN + measureCount + ANSI_RESET + " repeats: " + ANSI_CYAN + totalDeleteTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET);
        System.out.println();
    }

    private void printHTCMeasure(int repeats, int i, boolean nanos) {
        System.out.println("-> dataset " + (i + 1) + "(" + ANSI_CYAN + (repeats) + ANSI_RESET + ")" + " HashTable Chaining total time: " + ANSI_CYAN + (totalInsertTime + totalSearchTime + totalDeleteTime) + ((nanos) ? "nanos" : "ms") + ANSI_RESET);
        System.out.println("\tHashTable Chaining insert ==> " +
                "min duration: " + ANSI_GREEN + minInsertTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | avg duration: " + ANSI_YELLOW + avgInsertTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | max duration: " + ANSI_RED + maxInsertTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | total time for " + ANSI_CYAN + measureCount + ANSI_RESET + " repeats: " + ANSI_CYAN + totalInsertTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET);
        System.out.println("\tHashTable Chaining search ==> " +
                "min duration: " + ANSI_GREEN + minSearchTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | avg duration: " + ANSI_YELLOW + avgSearchTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | max duration: " + ANSI_RED + maxSearchTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | total time for " + ANSI_CYAN + measureCount + ANSI_RESET + " repeats: " + ANSI_CYAN + totalSearchTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET);
        System.out.println("\tHashTable Chaining delete ==> " +
                "min duration: " + ANSI_GREEN + minDeleteTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | avg duration: " + ANSI_YELLOW + avgDeleteTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | max duration: " + ANSI_RED + maxDeleteTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | total time for " + ANSI_CYAN + measureCount + ANSI_RESET + " repeats: " + ANSI_CYAN + totalDeleteTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET);
        System.out.println();
    }

    private void printHTLMeasure(int repeats, int i, boolean nanos) {
        System.out.println("-> dataset " + (i + 1) + "(" + ANSI_CYAN + (repeats) + ANSI_RESET + ")" + " HashTable Linear Probing total time: " + ANSI_CYAN + (totalInsertTime + totalSearchTime + totalDeleteTime) + ((nanos) ? "nanos" : "ms") + ANSI_RESET);
        System.out.println("\tHashTable Linear Probing insert ==> " +
                "min duration: " + ANSI_GREEN + minInsertTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | avg duration: " + ANSI_YELLOW + avgInsertTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | max duration: " + ANSI_RED + maxInsertTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | total time for " + ANSI_CYAN + measureCount + ANSI_RESET + " repeats: " + ANSI_CYAN + totalInsertTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET);
        System.out.println("\tHashTable Linear Probing search ==> " +
                "min duration: " + ANSI_GREEN + minSearchTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | avg duration: " + ANSI_YELLOW + avgSearchTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | max duration: " + ANSI_RED + maxSearchTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | total time for " + ANSI_CYAN + measureCount + ANSI_RESET + " repeats: " + ANSI_CYAN + totalSearchTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET);
        System.out.println("\tHashTable Linear Probing delete ==> " +
                "min duration: " + ANSI_GREEN + minDeleteTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | avg duration: " + ANSI_YELLOW + avgDeleteTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | max duration: " + ANSI_RED + maxDeleteTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET +
                " | total time for " + ANSI_CYAN + measureCount + ANSI_RESET + " repeats: " + ANSI_CYAN + totalDeleteTime + ((nanos) ? "nanos" : "ms") + ANSI_RESET);
        System.out.println();
    }

    public long getInsertDuration() {
        return insertDuration;
    }

    public long getSearchDuration() {
        return searchDuration;
    }

    public long getDeleteDuration() {
        return deleteDuration;
    }

    public void setMeasureCount(int measureCount) {
        this.measureCount = measureCount;
    }

    public static void main(String[] args) {
        List<Integer> btDataset = new ArrayList<>();
        List<Entry> htDataset = new ArrayList<>();

        try {
            Scanner myReader = new Scanner(new File("btDataset.txt"));
            while (myReader.hasNextLine()) {
                int number = Integer.parseInt(myReader.nextLine());
                btDataset.add(number);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            Scanner myReader = new Scanner(new File("htDataset.txt"));
            while (myReader.hasNextLine()) {
                List<String> s = List.of(myReader.nextLine().split(";"));
                htDataset.add(new Entry(s.get(0), s.get(1)));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        Measure measure = new Measure();

        System.out.print("Binary tree => b | Hash table => h >> ");
        String type = scanner.next();
        switch (type) {
            case "b" -> {
                System.out.print("Full test => f | Single test => s | Single full test => sf >> ");
                String testType = scanner.next();

                if (testType.equals("f")) {
                    System.out.print("How many repeats to do >> ");
                    int measureCount = scanner.nextInt();
                    measure.setMeasureCount(measureCount);
                    System.out.print("Dataset size: " + ANSI_CYAN + btDataset.size() + ANSI_RESET + ", interval >> ");
                    int interval = scanner.nextInt();

                    try {
                        FileWriter avlWriter = new FileWriter("avlMeasure.csv");
                        FileWriter splayWriter = new FileWriter("splayMeasure.csv");
                        for (int i = 0; i < btDataset.size() / interval; i++) {
                            measure.startFullMeasureAvl(btDataset.subList(0, interval * (i + 1)), new AVL(), i);
                            avlWriter.write(interval * (i + 1) + "," + measure.avgInsertTime + "," + measure.avgSearchTime + "," + measure.avgDeleteTime + "\n");
                            measure.startFullMeasureSplay(btDataset.subList(0, interval * (i + 1)), new Splay(), i);
                            splayWriter.write(interval * (i + 1) + "," + measure.avgInsertTime + "," + measure.avgSearchTime + "," + measure.avgDeleteTime + "\n");
                        }
                        avlWriter.close();
                        splayWriter.close();
                        System.out.println("Successfully wrote to the file avlMeasure.csv");
                        System.out.println("Successfully wrote to the file splayMeasure.csv");
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                } else if (testType.equals("sf")) {
                    System.out.print("How many datasets to create from btDataset.txt >> ");
                    int count = scanner.nextInt();

                    List<Integer> intervals = new ArrayList<>();
                    for (int i = 0; i < count; i++) {
                        System.out.print("Amount of data in " + (i + 1) + ".dataset from btDataset.txt max size: " + ANSI_CYAN + btDataset.size() + ANSI_RESET + " >> ");
                        intervals.add(scanner.nextInt());
                    }

                    List<AVL> avlList = new ArrayList<>();
                    List<Splay> splayList = new ArrayList<>();
                    for (int i = 0; i < count; i++) {
                        avlList.add(new AVL());
                        splayList.add(new Splay());
                    }

                    for (int i = 0; i < count; i++) {
                        measure.createAvl(btDataset.subList(0, intervals.get(i)), avlList.get(i));
                        measure.createSplay(btDataset.subList(0, intervals.get(i)), splayList.get(i));
                    }

                    System.out.print("Value to insert, search and delete: ");
                    int value = scanner.nextInt();
                    System.out.print("How many repeats to do >> ");
                    int measureCount = scanner.nextInt();
                    measure.setMeasureCount(measureCount);

                    try {
                        FileWriter avlWriter = new FileWriter("avlSingleMeasure.csv");
                        FileWriter splayWriter = new FileWriter("splaySingleMeasure.csv");
                        for (int i = 0; i < count; i++) {
                            measure.startSingleMeasureAvl(value, avlList.get(i), intervals.get(i), i);
                            avlWriter.write(intervals.get(i) + "," + measure.avgInsertTime + "," + measure.avgSearchTime + "," + measure.avgDeleteTime + "\n");
                            measure.startSingleMeasureSplay(value, splayList.get(i), intervals.get(i), i);
                            splayWriter.write(intervals.get(i) + "," + measure.avgInsertTime + "," + measure.avgSearchTime + "," + measure.avgDeleteTime + "\n");
                        }
                        avlWriter.close();
                        splayWriter.close();
                        System.out.println("Successfully wrote to the file avlSingleMeasure.csv");
                        System.out.println("Successfully wrote to the file splaySingleMeasure.csv");
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                } else {
                    System.out.print("How many datasets to create from btDataset.txt >> ");
                    int count = scanner.nextInt();

                    List<Integer> intervals = new ArrayList<>();
                    for (int i = 0; i < count; i++) {
                        System.out.print("Amount of data in " + (i + 1) + ".dataset from btDataset.txt max size: " + ANSI_CYAN + btDataset.size() + ANSI_RESET + " >> ");
                        intervals.add(scanner.nextInt());
                    }

                    List<AVL> avlList = new ArrayList<>();
                    List<Splay> splayList = new ArrayList<>();
                    for (int i = 0; i < count; i++) {
                        avlList.add(new AVL());
                        splayList.add(new Splay());
                    }

                    for (int i = 0; i < count; i++) {
                        measure.createAvl(btDataset.subList(0, intervals.get(i)), avlList.get(i));
                        measure.createSplay(btDataset.subList(0, intervals.get(i)), splayList.get(i));
                    }

                    System.out.print("Insert => i | Search => s | Delete => d | EXIT => e >> ");
                    String measureType = scanner.next();

                    while (measureType.equals("i") || measureType.equals("s") || measureType.equals("d")) {
                        switch (measureType) {
                            case "i" -> {
                                System.out.print("Value to insert: ");
                                int value = scanner.nextInt();
                                for (int i = 0; i < count; i++) {
                                    measure.insertAvl(value, avlList.get(i));
                                    long avlInsert = measure.getInsertDuration();
                                    measure.insertSplay(value, splayList.get(i));
                                    long splayInsert = measure.getInsertDuration();
                                    System.out.println("Insert time to dataset" + (i + 1) + "(" + ANSI_CYAN + intervals.get(i) + ANSI_RESET + ")\n\tAVL -> " + ANSI_YELLOW + avlInsert + "nanos" + ANSI_RESET + "\n\tSplay -> " + ANSI_YELLOW + splayInsert + "nanos" + ANSI_RESET);
                                }
                            }
                            case "s" -> {
                                System.out.print("Value to search: ");
                                int value = scanner.nextInt();
                                for (int i = 0; i < count; i++) {
                                    measure.searchAvl(value, avlList.get(i));
                                    long avlSearch = measure.getSearchDuration();
                                    measure.searchSplay(value, splayList.get(i));
                                    long splaySearch = measure.getSearchDuration();
                                    System.out.println("Search time from dataset" + (i + 1) + "(" + ANSI_CYAN + intervals.get(i) + ANSI_RESET + ")\n\tAVL -> " + ANSI_YELLOW + avlSearch + "nanos" + ANSI_RESET + "\n\tSplay -> " + ANSI_YELLOW + splaySearch + "nanos" + ANSI_RESET);
                                }
                            }
                            case "d" -> {
                                System.out.print("Value to delete: ");
                                int value = scanner.nextInt();
                                for (int i = 0; i < count; i++) {
                                    measure.deleteAvl(value, avlList.get(i));
                                    long avlDelete = measure.getDeleteDuration();
                                    measure.deleteSplay(value, splayList.get(i));
                                    long splayDelete = measure.getDeleteDuration();
                                    System.out.println("Delete time from dataset" + (i + 1) + "(" + ANSI_CYAN + intervals.get(i) + ANSI_RESET + ")\n\tAVL -> " + ANSI_YELLOW + avlDelete + "nanos" + ANSI_RESET + "\n\tSplay -> " + ANSI_YELLOW + splayDelete + "nanos" + ANSI_RESET);
                                }
                            }
                        }

                        System.out.print("\nInsert => i | Search => s | Delete => d | EXIT => e >> ");
                        measureType = scanner.next();
                    }
                }
            }
            case "h" -> {
                System.out.print("Full test => f | Single test => s | Single full test => sf >> ");
                String testType = scanner.next();

                if (testType.equals("f")) {
                    System.out.print("How many measures to do >> ");
                    int measureCount = scanner.nextInt();
                    measure.setMeasureCount(measureCount);
                    System.out.print("Dataset size: " + ANSI_CYAN + btDataset.size() + ANSI_RESET + ", interval >> ");
                    int interval = scanner.nextInt();

                    try {
                        FileWriter htcWriter = new FileWriter("htcMeasure.csv");
                        FileWriter htpWriter = new FileWriter("htpMeasure.csv");
                        for (int i = 0; i < htDataset.size() / interval; i++) {
                            measure.startFullMeasureHTC(htDataset.subList(0, interval * (i + 1)), new HashTableChaining(10, 1.5f), i);
                            htcWriter.write(interval * (i + 1) + "," + measure.avgInsertTime + "," + measure.avgSearchTime + "," + measure.avgDeleteTime + "\n");
                            measure.startFullMeasureHTL(htDataset.subList(0, interval * (i + 1)), new HashTableLinearProbing(10, 0.75f), i);
                            htpWriter.write(interval * (i + 1) + "," + measure.avgInsertTime + "," + measure.avgSearchTime + "," + measure.avgDeleteTime + "\n");
                        }
                        htcWriter.close();
                        htpWriter.close();
                        System.out.println("Successfully wrote to the file htcMeasure.csv");
                        System.out.println("Successfully wrote to the file htpMeasure.csv");
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                } else if (testType.equals("sf")) {
                    System.out.print("How many datasets to create from btDataset.txt >> ");
                    int count = scanner.nextInt();

                    List<Integer> intervals = new ArrayList<>();
                    for (int i = 0; i < count; i++) {
                        System.out.print("Amount of data in " + (i + 1) + ".dataset from btDataset.txt max size: " + ANSI_CYAN + btDataset.size() + ANSI_RESET + " >> ");
                        intervals.add(scanner.nextInt());
                    }

                    List<HashTableChaining> tableChainingList = new ArrayList<>();
                    List<HashTableLinearProbing> tableLinearProbingList = new ArrayList<>();
                    for (int i = 0; i < count; i++) {
                        tableChainingList.add(new HashTableChaining(10, 1.5f));
                        tableLinearProbingList.add(new HashTableLinearProbing(10, 0.75f));
                    }

                    for (int i = 0; i < count; i++) {
                        measure.createHTChaining(htDataset.subList(0, intervals.get(i)), tableChainingList.get(i));
                        measure.createHTLProbing(htDataset.subList(0, intervals.get(i)), tableLinearProbingList.get(i));
                    }

                    System.out.print("Data to insert [key value]: ");
                    String key = scanner.next();
                    String value = scanner.next();
                    Entry entry = new Entry(key, value);
                    System.out.print("How many repeats to do >> ");
                    int measureCount = scanner.nextInt();
                    measure.setMeasureCount(measureCount);

                    try {
                        FileWriter htcWriter = new FileWriter("htcSingleMeasure.csv");
                        FileWriter htpWriter = new FileWriter("htpSingleMeasure.csv");
                        for (int i = 0; i < count; i++) {
                            measure.startSingleMeasureHTC(entry, tableChainingList.get(i), intervals.get(i), i);
                            htcWriter.write(intervals.get(i) + "," + measure.avgInsertTime + "," + measure.avgSearchTime + "," + measure.avgDeleteTime + "\n");
                            measure.startSingleMeasureHTL(entry, tableLinearProbingList.get(i), intervals.get(i), i);
                            htpWriter.write(intervals.get(i) + "," + measure.avgInsertTime + "," + measure.avgSearchTime + "," + measure.avgDeleteTime + "\n");
                        }
                        htcWriter.close();
                        htpWriter.close();
                        System.out.println("Successfully wrote to the file htcSingleMeasure.csv");
                        System.out.println("Successfully wrote to the file htpSingleMeasure.csv");
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                } else {
                    System.out.print("How many datasets to create from htDataset.txt >> ");
                    int count = scanner.nextInt();

                    List<Integer> intervals = new ArrayList<>();
                    for (int i = 0; i < count; i++) {
                        System.out.print("Amount of data in " + (i + 1) + ".dataset from htDataset.txt max size: " + ANSI_CYAN + btDataset.size() + ANSI_RESET + " >> ");
                        intervals.add(scanner.nextInt());
                    }

                    List<HashTableChaining> tableChainingList = new ArrayList<>();
                    List<HashTableLinearProbing> tableLinearProbingList = new ArrayList<>();
                    for (int i = 0; i < count; i++) {
                        tableChainingList.add(new HashTableChaining(10, 1.5f));
                        tableLinearProbingList.add(new HashTableLinearProbing(10, 0.75f));
                    }

                    for (int i = 0; i < count; i++) {
                        measure.createHTChaining(htDataset.subList(0, intervals.get(i)), tableChainingList.get(i));
                        measure.createHTLProbing(htDataset.subList(0, intervals.get(i)), tableLinearProbingList.get(i));
                    }

                    System.out.print("Insert => i | Search => s | Delete => d | EXIT => e >> ");
                    String measureType = scanner.next();

                    while (measureType.equals("i") || measureType.equals("s") || measureType.equals("d")) {
                        switch (measureType) {
                            case "i" -> {
                                System.out.print("Data to insert [key value]: ");
                                String key = scanner.next();
                                String value = scanner.next();
                                Entry entry = new Entry(key, value);
                                for (int i = 0; i < count; i++) {
                                    measure.insertHTChaining(entry, tableChainingList.get(i));
                                    long htcInsert = measure.getInsertDuration();
                                    measure.insertHTLProbing(entry, tableLinearProbingList.get(i));
                                    long htlInsert = measure.getInsertDuration();
                                    System.out.println("Insert time to dataset" + (i + 1) + "(" + ANSI_CYAN + intervals.get(i) + ANSI_RESET + ")\n\tHashTable Chaining -> " + ANSI_YELLOW + htcInsert + "nanos" + ANSI_RESET + "\n\tHashTable LinearProbing -> " + ANSI_YELLOW + htlInsert + "nanos" + ANSI_RESET);
                                }
                            }
                            case "s" -> {
                                System.out.print("Data to search [key value]: ");
                                String key = scanner.next();
                                String value = scanner.next();
                                Entry entry = new Entry(key, value);
                                for (int i = 0; i < count; i++) {
                                    measure.searchHTChaining(entry, tableChainingList.get(i));
                                    long htcSearch = measure.getSearchDuration();
                                    measure.searchHTLProbing(entry, tableLinearProbingList.get(i));
                                    long htlSearch = measure.getSearchDuration();
                                    System.out.println("Search time from dataset" + (i + 1) + "(" + ANSI_CYAN + intervals.get(i) + ANSI_RESET + ")\n\tHashTable Chaining -> " + ANSI_YELLOW + htcSearch + "nanos" + ANSI_RESET + "\n\tHashTable LinearProbing -> " + ANSI_YELLOW + htlSearch + "nanos" + ANSI_RESET);
                                }
                            }
                            case "d" -> {
                                System.out.print("Data to delete [key value]: ");
                                String key = scanner.next();
                                String value = scanner.next();
                                Entry entry = new Entry(key, value);
                                for (int i = 0; i < count; i++) {
                                    measure.deleteHTChaining(entry, tableChainingList.get(i));
                                    long htcDelete = measure.getDeleteDuration();
                                    measure.deleteHTLProbing(entry, tableLinearProbingList.get(i));
                                    long htlDelete = measure.getDeleteDuration();
                                    System.out.println("Delete time from dataset" + (i + 1) + "(" + ANSI_CYAN + intervals.get(i) + ANSI_RESET + ")\n\tHashTable Chaining -> " + ANSI_YELLOW + htcDelete + "nanos" + ANSI_RESET + "\n\tHashTable LinearProbing -> " + ANSI_YELLOW + htlDelete + "nanos" + ANSI_RESET);
                                }
                            }
                        }

                        System.out.print("\nInsert => i | Search => s | Delete => d | EXIT => e >> ");
                        measureType = scanner.next();
                    }
                }
            }
            default -> System.out.println("Wrong input!");
        }
    }
}
