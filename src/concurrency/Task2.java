package concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Task2 {
    public static void main(String[] args) throws InterruptedException {
        FooBar fooBar = new FooBar(500);
        Thread thread1 = new Thread(() -> {
            try {
                fooBar.foo(() -> System.out.print("foo"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                fooBar.bar(() -> System.out.print("bar"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread2.start();
        thread1.start();


        thread1.join();
        thread2.join();

    }
}

//class FooBar {
//    private int n;
//    private Lock lock = new ReentrantLock();
//    private volatile boolean fooTurn = true;
//
//    public FooBar(int n) {
//        this.n = n;
//    }
//
//    private void waitAndTakeTurn(Lock lock, boolean fooTurn) {
//        boolean lockTaken = false;
//        while (true) {
//            try {
//                if (this.fooTurn == fooTurn) {
//                    lockTaken = lock.tryLock();
//                }
//            } finally {
//                if (lockTaken) {
//                    return;
//                }
//            }
//        }
//    }
//
//    public void foo(Runnable printFoo) throws InterruptedException {
//        for (int i = 0; i < n; i++) {
//
//            waitAndTakeTurn(lock, true);
//            // printFoo.run() outputs "foo". Do not change or remove this line.
//            printFoo.run();
//            lock.unlock();
//            fooTurn = false;
//
//        }
//    }
//
//    public void bar(Runnable printBar) throws InterruptedException {
//        for (int i = 0; i < n; i++) {
//            waitAndTakeTurn(lock, false);
//            // printBar.run() outputs "bar". Do not change or remove this line.
//            printBar.run();
//            lock.unlock();
//            fooTurn = true;
//        }
//    }
//}

class FooBar {
    private int n;
    private boolean fooTurn = true;

    public FooBar(int n) {
        this.n = n;
    }
    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            synchronized (this){
                if (!fooTurn){
                    wait();
                }
                // printFoo.run() outputs "foo". Do not change or remove this line.
                printFoo.run();
                fooTurn = false;
                notify();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            synchronized (this){
                if (fooTurn){
                    wait();
                }
                // printBar.run() outputs "bar". Do not change or remove this line.
                printBar.run();
                fooTurn = true;
                notify();
            }
        }
    }
}

