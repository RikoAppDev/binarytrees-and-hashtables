package hashtable;

import java.util.List;

import static hashtable.HashTableChaining.*;

public class HashTableLinearProbing {
    private static final Entry deletedEntry = new Entry("DELETED", "DELETED");

    private Entry[] table;
    private int capacity;
    private final float loadFactor;
    private int size;

    public HashTableLinearProbing(int capacity, float loadFactor) {
        this.capacity = capacity;
        this.loadFactor = loadFactor;
        size = 0;
        table = new Entry[capacity];
    }

    public void insert(List<Entry> data) {
        for (Entry e : data) {
            insertEntry(e);
        }
    }

    private void insertEntry(Entry e) {
        if (size >= loadFactor * capacity) {
            resize();
        }

        int i = hash(e);
        while (table[i] != null && table[i] != deletedEntry && !table[i].getKey().equals(e.getKey())) {
            i = (i + 1) % capacity;
        }
        table[i] = e;
        size++;
    }

    private void resize() {
        capacity *= 2;
        Entry[] newTable = new Entry[capacity];

        for (Entry entry : table) {
            if (entry != null) {
                int i = hash(entry);
                while (newTable[i] != null) {
                    i = (i + 1) % capacity;
                }
                newTable[i] = entry;
            }
        }

        table = newTable;
    }

    public void search(List<Entry> data) {
        for (Entry e : data) {
            searchEntry(e);
        }
    }

    private boolean searchEntry(Entry e) {
        int i = hash(e);
        while (table[i] != null) {
            if (table[i].getKey().equals(e.getKey())) {
                return true;
            }
            i = (i + 1) % capacity;
        }

        return false;
    }

    public void delete(List<Entry> data) {
        for (Entry e : data) {
            deleteEntry(e);
        }
    }

    private boolean deleteEntry(Entry e) {
        int i = hash(e);

        while (table[i] != null) {
            if (table[i].getKey().equals(e.getKey())) {
                table[i] = deletedEntry;
                size--;
                return true;
            }
            i = (i + 1) % capacity;
        }

        return false;
    }

    private int hash(Entry e) {
        return Math.abs(e.getKey().hashCode() % capacity);
    }

    public void printHashTableContent() {
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) {
                System.out.println(i + ".");
            } else {
                System.out.println(i + ". " + table[i].getKey() + " " + table[i].getValue());
            }
        }
    }

    public void insertValue(Entry entry) {
        insertEntry(entry);
    }

    public boolean searchValue(Entry entry) {
        return searchEntry(entry);
    }

    public boolean deleteValue(Entry entry) {
        return deleteEntry(entry);
    }
}
