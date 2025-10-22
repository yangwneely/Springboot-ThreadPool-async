package com.example.threadpool.executors.a_newfixedthreadpool;

import java.util.Random;
import java.util.concurrent.*;

//固定数量的线程池

/**
 * 使用submit可以执行有返回值的任务或者无返回值的任务；
 * 而executor只能执行无返回值的任务
 */
public class a_NewFixedThreadPoolMain {
    public static void main(String[] args) throws Exception {
        ExecutorService threadPool = Executors.newFixedThreadPool(2);

        //添加任务方式1 submit无返回
        threadPool.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        });

        //添加任务方式2 execute无返回
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        });

        //添加如无方式3 submit有返回
        Future<Integer> result = threadPool.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int num = new Random().nextInt();
                System.out.println("随机数"+num);
                return num;
            }
        });

        System.out.println("返回结果"+result.get());

    }
}
