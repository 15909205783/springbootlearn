package com.yang.io.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 2. 常用的几种BlockingQueue：
 * ArrayBlockingQueue（int i）:规定大小的BlockingQueue，其构造必须指定大小。其所含的对象是FIFO顺序排序的。
 * <p>
 * LinkedBlockingQueue（）或者（int i）:大小不固定的BlockingQueue，若其构造时指定大小，生成的BlockingQueue有大小限制，不指定大小，其大小有Integer.MAX_VALUE来决定。其所含的对象是FIFO顺序排序的。
 * <p>
 * PriorityBlockingQueue（）或者（int i）:类似于LinkedBlockingQueue，但是其所含对象的排序不是FIFO，而是依据对象的自然顺序或者构造函数的Comparator决定。
 * <p>
 * SynchronizedQueue（）:特殊的BlockingQueue，对其的操作必须是放和取交替完成。
 * ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue)
 */
public class NewCachedThreadPoolTest {
    private static List lsst = new ArrayList();

    private static synchronized void add() {
        for (int i = 0; i < 10; i++) {
            lsst.add(Thread.currentThread().getName() + i);
            System.out.println(lsst.toString());
        }
    }

    public static void main(String[] args) {
        NewCachedThreadPoolTest poolTest = new NewCachedThreadPoolTest();
        //创建一个可缓存的线程池
//        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
//        poolTest.cachedThreadPool(cachedThreadPool);
//        poolTest.fixedThread();
//        poolTest.scheduledThread();
//        poolTest.singleThread();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        Thread thread1 = new Thread(NewCachedThreadPoolTest::add, "a");

        Runnable thread2 = new Thread(NewCachedThreadPoolTest::add, "b");
        Runnable thread3 = new Thread(NewCachedThreadPoolTest::add, "c");
        Runnable thread4 = new Thread(NewCachedThreadPoolTest::add, "d");
        fixedThreadPool.execute(thread1);
        fixedThreadPool.execute(thread2);
        fixedThreadPool.execute(thread3);
        fixedThreadPool.execute(thread4);
    }

    /**
     * @param cachedThreadPool 线程池
     */
    private void cachedThreadPool(ExecutorService cachedThreadPool) {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cachedThreadPool.execute(() -> {
                //打印正在执行的缓存线程信息
                System.out.println(Thread.currentThread().getName() + "正在执行");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * 创建固定大小的线程池
     */
    private void fixedThread() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            fixedThreadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "正在被执行");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void scheduledThread() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
//        for (int i = 0; i < 10; i++) {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("延迟1秒后每3秒执行一次");
        }, 1, 3, TimeUnit.SECONDS);
//        }
    }

    /**
     * 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
     */
    private void singleThread() {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            singleThreadExecutor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "正在被执行,打印的值是:" + index);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
