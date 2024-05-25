package other;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class ProdConsumer3 {
    static int LIST_SIZE = 10;
    static ReentrantLock lock;
    static Condition full;
    static Condition empty;

    public static void main(String[] args) {
        lock = new ReentrantLock();
        full = lock.newCondition();
        empty = lock.newCondition();

        Resource res = new Resource(LIST_SIZE);

        new Thread(() -> {
            lock.lock();
            if (res.list.size() >= LIST_SIZE) {
                System.out.println("List is full: waiting now");
                try {
                    empty.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            while (res.list.size() < LIST_SIZE) {
                res.produce();
            }
            System.out.println("List is now full");
            full.signalAll();
            lock.unlock();
        }).start();


        new Thread(() -> {
            lock.lock();
            if (res.list.isEmpty()) {
                System.out.println("list is empty: waiting now");
                try {
                    full.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            res.list.forEach((res::consume));
            res.list.clear();
            System.out.println("List is now empty");
            empty.signalAll();
            lock.unlock();
        }).start();


    }

}

class Resource {

    private final int LIST_SIZE;

    public Resource(int LIST_SIZE) {
        this.LIST_SIZE = LIST_SIZE;
    }

    public LinkedList<Integer> list = new LinkedList<>();

    synchronized void consume(int i) {
        if(list.size()<LIST_SIZE){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(i);
    }

    synchronized void produce() {
        list.push(new Random().nextInt(100));
        notifyAll();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}



