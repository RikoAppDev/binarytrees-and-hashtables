import static hashtable.HashTableChaining.*;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class DataGenerator {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_CYAN = "\u001B[36m";

    private final List<Integer> numberList = new ArrayList<>();
    private final List<Entry> entryList = new ArrayList<>();
    private final String bstFilename;
    private final String htFilename;

    public DataGenerator(int amount, String bstFilename, String htFilename) {
        this.bstFilename = bstFilename;
        this.htFilename = htFilename;
        generateRandomNumbers(amount);
        generateRandomEntries(amount);
    }

    public void generateRandomNumbers(int amount) {
        Instant startTime = Instant.now();
        for (int i = 0; i < amount; i++) {
            numberList.add(i);
        }
        Collections.shuffle(numberList);
        Instant endTime = Instant.now();
        long duration = Duration.between(startTime, endTime).toMillis();

        try {
            FileWriter myWriter = new FileWriter(bstFilename);
            for (int d : numberList) {
                myWriter.write(d + "\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file " + bstFilename);
            System.out.println("Data generation time: " + ANSI_CYAN + duration + "ms" + ANSI_RESET);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void generateRandomEntries(int amount) {
        Random random = new Random();
        Instant startTime = Instant.now();
        for (int i = 0; i < amount; i++) {
            String key = "";
            for (int j = 0; j < 10; j++) {
                key = key.concat(String.valueOf((char) random.nextInt(97, 123)));
            }
            String value = String.valueOf((char) random.nextInt(65, 91));

            int valueLength = random.nextInt(1, 11);
            for (int j = 0; j < valueLength; j++) {
                value = value.concat(String.valueOf((char) random.nextInt(97, 123)));
            }
            entryList.add(new Entry(key, value));
        }
        Collections.shuffle(entryList);
        Instant endTime = Instant.now();
        long duration = Duration.between(startTime, endTime).toMillis();

        try {
            FileWriter myWriter = new FileWriter(htFilename);
            for (Entry d : entryList) {
                myWriter.write(d.getKey() + ";" + d.getValue() + "\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file " + htFilename);
            System.out.println("Data generation time: " + ANSI_CYAN + duration + "ms" + ANSI_RESET);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Amount of data in dataset: ");
        int amount = scanner.nextInt();
        new DataGenerator(amount, "btDataset.txt", "htDataset.txt");
    }
}
