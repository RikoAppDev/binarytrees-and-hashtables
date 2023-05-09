package hashtable;

import java.util.ArrayList;
import java.util.List;

public class HashTableChaining {
    public static class Entry {
        private final String key;
        private final String value;

        public Entry(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    private List<List<Entry>> bucketList = new ArrayList<>();
    private int capacity;
    private final float loadFactor;
    private int size;

    public HashTableChaining(int capacity, float loadFactor) {
        this.capacity = capacity;
        this.loadFactor = loadFactor;
        size = 0;
        for (int i = 0; i < capacity; i++) {
            bucketList.add(new ArrayList<>());
        }
    }

    public void insert(List<Entry> data) {
        for (Entry e : data) {
            insertEntry(e);
        }
    }

    private void insertEntry(Entry e) {
        if ((float) size / capacity >= loadFactor) {
            resize();
        }

        int i = hash(e);
        List<Entry> bucket = bucketList.get(i);

        for (Entry entry : bucket) {
            if (entry.getKey().equals(e.getKey())) {
                return;
            }
        }

        bucket.add(e);
        size++;
    }

    private void resize() {
        capacity *= 2;
        List<List<Entry>> newBucketList = new ArrayList<>();

        for (int i = 0; i < capacity; i++) {
            newBucketList.add(new ArrayList<>());
        }

        for (List<Entry> bucket : bucketList) {
            for (Entry entry : bucket) {
                int i = hash(entry);
                newBucketList.get(i).add(entry);
            }
        }

        bucketList = newBucketList;
    }

    public void search(List<Entry> data) {
        for (Entry e : data) {
            searchEntry(e);
        }
    }

    private boolean searchEntry(Entry e) {
        int i = hash(e);
        List<Entry> entryList = bucketList.get(i);
        for (Entry entry : entryList) {
            if (entry.getKey().equals(e.getKey())) {
                return true;
            }
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
        List<Entry> entryList = bucketList.get(i);

        for (int j = 0; j < entryList.size(); j++) {
            Entry entry = entryList.get(j);
            if (entry.getKey().equals(e.getKey())) {
                entryList.remove(entryList.get(j));
                size--;
                return true;
            }
        }
        return false;
    }

    private int hash(Entry e) {
        return Math.abs(e.getKey().hashCode() % capacity);
    }

    public void printHashTableContent() {
        for (int i = 0; i < bucketList.size(); i++) {
            System.out.print(i + ". ");
            for (int j = 0; j < bucketList.get(i).size(); j++) {
                System.out.print(bucketList.get(i).get(j).getKey() + " " + bucketList.get(i).get(j).getValue() + "; ");
            }
            System.out.println();
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
