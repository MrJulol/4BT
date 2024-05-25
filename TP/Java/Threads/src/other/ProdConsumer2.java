package other;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class ProdConsumer2 {
    static int LIST_SIZE = 10;
    static LinkedList<Integer> list;
    static ReentrantLock lock;
    static Condition full;
    static Condition empty;

    public static void main(String[] args) {
        list = new LinkedList<>();
        lock = new ReentrantLock();
        full = lock.newCondition();
        empty = lock.newCondition();


        new Thread(new Consumer()).start();
        new Thread(new Producer()).start();


    }

    public static void consume(int i) {
        System.out.println(i);
    }

    public static int produce() {
        return new Random().nextInt(100);
    }

    static class Producer implements Runnable {

        @Override
        public void run() {
            lock.lock();
            if (list.size() >= LIST_SIZE) {
                System.out.println("list is full: waiting now");
                try {
                    empty.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            while (list.size() < LIST_SIZE) {
                list.push(produce());
            }
            System.out.println("list is now full");
            full.signalAll();
            lock.unlock();
        }
    }

    static class Consumer implements Runnable {

        @Override
        public void run() {
            lock.lock();
            if (list.isEmpty()) {
                System.out.println("list is empty: waiting now");
                try {
                    full.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            list.forEach(ProdConsumer2::consume);
            list.clear();
            System.out.println("List is now empty");
            empty.signalAll();
            lock.unlock();
        }
    }

}



