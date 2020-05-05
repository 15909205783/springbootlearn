package com.yang.io.thread;

import java.util.ArrayList;
import java.util.List;

public class SychronizedTest {
   private static List lsst = new ArrayList();

    public static void main(String[] args) {
        SychronizedTest sychronizedTest = new SychronizedTest();
        Thread thread1 = new Thread(sychronizedTest::add, "a");

        Thread thread2 = new Thread(sychronizedTest::add, "b");
        thread1.start();
        thread2.start();
        System.out.println(lsst.toString());
    }

    private  synchronized void add() {
        for (int i = 0; i < 10; i++) {
            lsst.add(Thread.currentThread().getName() + i);
            System.out.println(lsst.toString());
        }
    }
}
