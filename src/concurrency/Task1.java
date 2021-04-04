package concurrency;

public class Task1 {
    public static void main(String[] args) throws InterruptedException {
        Foo foo = new Foo();

        Thread thread1 = new Thread(() -> {
            try {
                foo.first(()-> System.out.print("first"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                foo.second(()-> System.out.print("second"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread3 = new Thread(() -> {
            try {
                foo.third(()-> System.out.print("third"));
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

class Foo {
    private Object lock;
    private boolean oneDone;
    private boolean twoDone;

    public Foo() {
        lock = new Object();
        oneDone = false;
        twoDone = false;
    }

    public void first(Runnable printFirst) throws InterruptedException {
        synchronized (lock) {
            printFirst.run();
            oneDone = true;
            lock.notifyAll();
        }
    }

    public void second(Runnable printSecond) throws InterruptedException {
        synchronized (lock) {
            while (!oneDone) {
                lock.wait();
            }
            printSecond.run();
            twoDone = true;
            lock.notifyAll();
        }
    }

    public void third(Runnable printThird) throws InterruptedException {
        synchronized (lock) {
            while (!twoDone) {
                lock.wait();
            }
            printThird.run();
        }
    }
}
