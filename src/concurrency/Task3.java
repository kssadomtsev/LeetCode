package concurrency;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

public class Task3 {
    public static void main(String[] args) throws InterruptedException {
        ZeroEvenOdd zeroEvenOdd = new ZeroEvenOdd(2);
        Thread thread1 = new Thread(() -> {
            try {
                zeroEvenOdd.zero(System.out::print);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                zeroEvenOdd.even(System.out::print);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread3 = new Thread(() -> {
            try {
                zeroEvenOdd.odd(System.out::print);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread2.start();
        thread1.start();
        thread3.start();

        thread2.join();
        thread1.join();
        thread3.join();
    }
}

class ZeroEvenOdd {
    private int n;
    private Semaphore semaphoreZero;
    private Semaphore semaphoreOdd;
    private Semaphore semaphoreEven;

    public ZeroEvenOdd(int n) {
        this.n = n;
        semaphoreZero = new Semaphore(1);
        semaphoreOdd = new Semaphore(1);
        semaphoreEven = new Semaphore(1);
        try {
            semaphoreOdd.acquire();
            semaphoreEven.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            semaphoreZero.acquire();
            printNumber.accept(0);
            if (i % 2 == 0) {
                semaphoreOdd.release();
            } else {
                semaphoreEven.release();
            }
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 0; i < n ; i++) {
            semaphoreOdd.acquire();
            i++;
            printNumber.accept(i);
            semaphoreZero.release();
        }
    }


    public void even(IntConsumer printNumber) throws InterruptedException {

        for (int i = 1; i < n ; i++) {
            semaphoreEven.acquire();
            i++;
            printNumber.accept(i);
            semaphoreZero.release();
        }
    }


}
