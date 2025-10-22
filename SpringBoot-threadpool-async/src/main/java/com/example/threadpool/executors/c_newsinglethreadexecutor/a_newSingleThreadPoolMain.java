package com.example.threadpool.executors.c_newsinglethreadexecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 单线程的线程池又什么意义？
 * <p>
 * 1. 复用线程。
 * 2. 单线程的线程池提供了任务队列和拒绝策略（newSingleThreadScheduledExecutor当任务队列满了之后（Integer.MAX_VALUE），新来的任务就会拒绝策略）
 */
public class a_newSingleThreadPoolMain {

    public static void main(String[] args) {

        //单线程线程池 同 e_newsinglethreadscheduledexecutor 下 a_newSingleThreadScheduledExecutorMain
        ExecutorService threadpool2 = Executors.newSingleThreadScheduledExecutor();
        threadpool2.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程名称：" + Thread.currentThread().getName());
            }
        });
    }
}
