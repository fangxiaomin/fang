package com.fang.user.JUC.cas;

import org.bouncycastle.pqc.crypto.newhope.NHOtherInfoGenerator;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author:fxm
 * @createTime:2021/12/31 17:24
 */

class clickNumber {
    int number = 0;
    public synchronized void add_synchronized(){
        number++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();
    public void add_atomicInteger(){
        atomicInteger.incrementAndGet();
    }

    AtomicLong atomicLong = new AtomicLong();
    public void add_atomicLong(){
        atomicLong.incrementAndGet();
    }

    LongAdder longAdder = new LongAdder();
    public void add_longAdder(){
        longAdder.increment();
    }

    LongAccumulator longAccumulator =  new LongAccumulator((x,y) -> x+y,0);
    public void add_longAccumulator(){
        longAccumulator.accumulate(1);
    }

}

public class LongAdderCalcDemo {

    public static final int SIZE_THREAD = 50;
    public static final int _1W = 10000;

    public static void main(String[] args) throws InterruptedException {
        clickNumber clickNumber = new clickNumber();
        long startTime ;
        long endTime ;
        CountDownLatch countDownLatch1 = new CountDownLatch(SIZE_THREAD);
        CountDownLatch countDownLatch2 = new CountDownLatch(SIZE_THREAD);
        CountDownLatch countDownLatch3 = new CountDownLatch(SIZE_THREAD);
        CountDownLatch countDownLatch4 = new CountDownLatch(SIZE_THREAD);
        CountDownLatch countDownLatch5 = new CountDownLatch(SIZE_THREAD);


        startTime = System.currentTimeMillis();
        for (int i = 1; i <= SIZE_THREAD ; i++) {
            new Thread(() ->{
                try {
                    for (int j = 1; j <= 100*_1W; j++) {
                        clickNumber.add_synchronized();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch1.countDown();
                }
            },String.valueOf(i)).start();
        }
        countDownLatch1.await();
        endTime = System.currentTimeMillis();
        System.out.println("costTime: "+ (endTime - startTime) +"毫秒"+"\t synchronized "+ "\t" + clickNumber.number);


        startTime = System.currentTimeMillis();
        for (int i = 1; i <= SIZE_THREAD ; i++) {
            new Thread(() ->{
                try {
                    for (int j = 1; j <= 100*_1W; j++) {
                        clickNumber.add_atomicInteger();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch2.countDown();
                }
            },String.valueOf(i)).start();
        }
        countDownLatch2.await();
        endTime = System.currentTimeMillis();
        System.out.println("costTime: "+ (endTime - startTime) +"毫秒"+"\t add_atomicInteger "+ "\t" + clickNumber.atomicInteger.get());


        startTime = System.currentTimeMillis();
        for (int i = 1; i <= SIZE_THREAD ; i++) {
            new Thread(() ->{
                try {
                    for (int j = 1; j <= 100*_1W; j++) {
                        clickNumber.add_atomicLong();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch3.countDown();
                }
            },String.valueOf(i)).start();
        }
        countDownLatch3.await();
        endTime = System.currentTimeMillis();
        System.out.println("costTime: "+ (endTime - startTime) +"毫秒"+"\t add_atomicLong "+ "\t" + clickNumber.atomicLong.get());


        startTime = System.currentTimeMillis();
        for (int i = 1; i <= SIZE_THREAD ; i++) {
            new Thread(() ->{
                try {
                    for (int j = 1; j <= 100*_1W; j++) {
                        clickNumber.add_longAdder();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch4.countDown();
                }
            },String.valueOf(i)).start();
        }
        countDownLatch4.await();
        endTime = System.currentTimeMillis();
        System.out.println("costTime: "+ (endTime - startTime) +"毫秒"+"\t add_longAdder "+ "\t" + clickNumber.longAdder.longValue());


        startTime = System.currentTimeMillis();
        for (int i = 1; i <= SIZE_THREAD ; i++) {
            new Thread(() ->{
                try {
                    for (int j = 1; j <= 100*_1W; j++) {
                        clickNumber.add_longAccumulator();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch5.countDown();
                }
            },String.valueOf(i)).start();
        }
        countDownLatch5.await();
        endTime = System.currentTimeMillis();
        System.out.println("costTime: "+ (endTime - startTime) +"毫秒"+"\t longAccumulator "+ "\t" + clickNumber.longAccumulator.get());



    }

}
