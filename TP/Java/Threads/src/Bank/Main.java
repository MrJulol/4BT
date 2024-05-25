package Bank;

import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) {
        Thread bank = new Thread(new BankThread());
        bank.start();
        try {
            bank.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class BankThread implements Runnable {
    final int timeout = 2 * 60 * 1000;
    final SharedResource resource;

    BankThread() {
        this.resource = new SharedResource();
        resource.putUser("asd");
        resource.putUser("123");
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            System.out.println("BANK");
            System.out.println(resource);
            Thread user = new Thread(new ATMThread(new CountDownLatch(1), resource));
            user.start();
            try {
                user.join(timeout);
                System.out.println("TIMEOUT");
                user.interrupt();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            System.out.println("User ended");
        }
        System.out.println(resource);
    }
}

class ATMThread implements Runnable {
    private final ReentrantLock lock;
    private final CountDownLatch countDownLatch;
    private final SharedResource resource;

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("UserThread");
        Thread view = new Thread(new UserView(countDownLatch, resource, lock));
        view.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("TIMEOUT in ATM");
            view.interrupt();
            return;
        }
        lock.lock();
        try {
            System.out.println("Enter username");
            String login = scanner.nextLine();
            if (resource.accounts.containsKey(login)) {
                System.out.println("User logged in");
                resource.nameTransfer = login;
            } else {
                System.out.println("Wrong username");
                view.interrupt();
            }
        } finally {
            countDownLatch.countDown();
            lock.unlock();
            try {
                view.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ATMThread(CountDownLatch countDownLatch, SharedResource resource) {
        this.lock = new ReentrantLock();
        this.countDownLatch = countDownLatch;
        this.resource = resource;
    }
}

class UserView implements Runnable {
    private final CountDownLatch countDownLatch;
    private final SharedResource resource;
    private final ReentrantLock lock;
    private String name;

    UserView(CountDownLatch countDownLatch, SharedResource resource, ReentrantLock lock) {
        this.countDownLatch = countDownLatch;
        this.resource = resource;
        this.lock = lock;
    }

    @Override
    public void run() {
        System.out.println("View start");
        Scanner scanner = new Scanner(System.in);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            System.out.println("TIMEOUT in UserView");
            return;
        }
        lock.lock();
        try {
            this.name = resource.nameTransfer;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        System.out.println("View logged in: " + this.name);
        System.out.println("Enter receiver");
        String receiver = scanner.next();
        System.out.println("Enter amount");
        int amount;
        try {
            amount = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Wrong amount, using 100");
            amount = 100;
        }
        if (resource.accounts.containsKey(receiver)) {
            resource.transfer(this.name, receiver, amount);
        } else {
            System.out.println("USER NOT FOUND");
        }
    }
}

class SharedResource {
    HashMap<String, Integer> accounts;
    String nameTransfer;

    public SharedResource() {
        accounts = new HashMap<>();
    }

    public synchronized void putUser(String name) {
        accounts.put(name, 1000);
    }

    public synchronized void transfer(String sender, String receiver, int amount) {

        if (accounts.get(sender) > amount) {
            accounts.put(receiver, accounts.get(sender) + amount);
            accounts.put(sender, accounts.get(sender) - amount);
            System.out.println("Did transfer successful");
        } else {
            System.out.println("Did not transfer successful");
        }
    }

    @Override
    public String toString() {
        return "SharedResource{" +
                "accounts=" + accounts +
                '}';
    }
}


