package Two;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main3 {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please provide the capacity as a program argument.");
            return;
        }

        int capacity;
        try {
            capacity = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid capacity value. Please provide a valid integer.");
            return;
        }
        List<Item> items = Arrays.asList(
                new Item(1, 1),
                new Item(2, 2),
                new Item(3, 3),
                new Item(4, 4),
                new Item(5, 5),
                new Item(6, 6),
                new Item(7, 7),
                new Item(8, 8),
                new Item(9, 9),
                new Item(10, 10),
                new Item(11, 11),
                new Item(12, 12),
                new Item(13, 13),
                new Item(14, 14),
                new Item(15, 15),
                new Item(16, 16),
                new Item(17, 17),
                new Item(18, 18),
                new Item(19, 19),
                new Item(20, 20)
        );

        if (items.stream().mapToInt(Item::weight).sum() <= capacity) {
            System.out.println("Maximaler Wert im Rucksack: " + items.stream().mapToInt(Item::value).sum());
            return;
        }
        if (items.stream().mapToInt(Item::weight).min().getAsInt() > capacity) {
            System.out.println("Maximaler Wert im Rucksack: 0");
            return;
        }

        ExecutorService executorService = Executors.newFixedThreadPool(20);
        List<Future<Integer>> futures = new ArrayList<>();
        generateSubsets(items, executorService, futures, capacity);

        try {
            int maxValue = 0;
            for (Future<Integer> future : futures) {
                Integer result = future.get();
                maxValue = Math.max(maxValue, result);
            }

            System.out.println("Maximaler Wert im Rucksack: " + maxValue);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    private static void generateSubsets(List<Item> allItems, ExecutorService executorService, List<Future<Integer>> futures, int capacity) {
        List<List<Item>> subsets = new ArrayList<>();
        recursionSubsetCreator(allItems, 0, new ArrayList<>(), subsets, executorService, futures, capacity);
    }

    private static void recursionSubsetCreator(List<Item> allItems, int currentIndex, List<Item> currentSubset, List<List<Item>> allSubsets, ExecutorService executorService, List<Future<Integer>> futures, int capacity) {
        if (currentIndex == allItems.size()) {
            futures.add(executorService.submit(() -> (
                    currentSubset.stream().mapToInt(Item::weight).sum() <= capacity) ? currentSubset.stream().mapToInt(Item::value).sum() : 0)
            );
            return;
        }
        recursionSubsetCreator(allItems, currentIndex + 1, new ArrayList<>(currentSubset), allSubsets, executorService, futures, capacity);
        currentSubset.add(allItems.get(currentIndex));
        recursionSubsetCreator(allItems, currentIndex + 1, new ArrayList<>(currentSubset), allSubsets, executorService, futures, capacity);
        currentSubset.remove(currentSubset.size() - 1);
    }

    public record Item(int weight, int value) {
        @Override
        public String toString() {
            return "Item{" + weight + "," + value + "}";
        }
    }
}