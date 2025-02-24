import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Hashing2{

    private static final String FILENAME = "listHashing.txt";
    private static final int NUM_THREADS = 10;

    public static int hash(String s, int m, int a) {
        int h = 0;
        for (char c : s.toCharArray()) {
            h = (a * h + (int) c) % m;
        }
        return h;
    }

    public static List<String> readNamesFromFile(String filename) {
        List<String> names = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    names.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return names;
    }

    public static int countCollisions(List<String> names, int m, int a) {
        int[] hashTable = new int[m];
        int collisions = 0;
        for (String name : names) {
            int hashValue = hash(name, m, a);
            if (hashTable[hashValue] != 0) {
                collisions++;
            } else {
                hashTable[hashValue] = 1;
            }
        }
        return collisions;
    }

    public static class HashingTask implements Callable<Result> {
        private final List<String> names;
        private final int mStart;
        private final int mEnd;

        public HashingTask(List<String> names, int mStart, int mEnd) {
            this.names = names;
            this.mStart = mStart;
            this.mEnd = mEnd;
        }

        @Override
        public Result call() {
            int bestA = 0, bestM = 0, minCollisions = Integer.MAX_VALUE;

            for (int m = mStart; m <= mEnd; m++) {
                for (int a = 1; a < m; a++) {
                    int collisions = countCollisions(names, m, a);
                    if (collisions < minCollisions) {
                        minCollisions = collisions;
                        bestA = a;
                        bestM = m;
                    }
                }
                System.out.println("Done "+m);
            }

            return new Result(bestA, bestM, minCollisions, mStart, mEnd);
        }
    }

    public static class Result {
        public final int bestA;
        public final int bestM;
        public final int minCollisions;
        public final int mStart;
        public final int mEnd;

        public Result(int bestA, int bestM, int minCollisions, int mStart, int mEnd) {
            this.bestA = bestA;
            this.bestM = bestM;
            this.minCollisions = minCollisions;
            this.mStart = mStart;
            this.mEnd = mEnd;
        }

        @Override
        public String toString() {
            return "Best parameters found: a = " + bestA + ", m = " + bestM +
                    " with " + minCollisions + " collisions in Area: " + mStart + "-" + mEnd;
        }
    }

    public static void main(String[] args) {
        List<String> names = readNamesFromFile(FILENAME);
        System.out.println("Number of entries: " + names.size());

        int mRangeStart = 3327, mRangeEnd = 4096;
        int step = (mRangeEnd - mRangeStart + 1) / NUM_THREADS;

        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        List<Future<Result>> futures = new ArrayList<>();

        for (int i = 0; i < NUM_THREADS; i++) {
            int localRangeStart = mRangeStart + i * step;
            int localRangeEnd = (i == NUM_THREADS - 1) ? mRangeEnd : localRangeStart + step - 1;
            futures.add(executor.submit(new HashingTask(names, localRangeStart, localRangeEnd)));
        }

        int globalBestA = 0, globalBestM = 0, globalMinCollisions = Integer.MAX_VALUE;
        for (Future<Result> future : futures) {
            try {
                Result result = future.get();
                System.out.println(result);
                if (result.minCollisions < globalMinCollisions) {
                    globalMinCollisions = result.minCollisions;
                    globalBestA = result.bestA;
                    globalBestM = result.bestM;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();

        System.out.println("Overall best parameters: a = " + globalBestA + ", m = " + globalBestM +
                " with " + globalMinCollisions + " collisions");
    }
}
