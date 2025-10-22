package com.example.threadpool.executors.e_newsinglethreadscheduledexecutor;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class a_newSingleThreadScheduledExecutorMain {

    public static void main(String[] args) {
        //定时任务单线程
        ScheduledExecutorService threadpool = Executors.newSingleThreadScheduledExecutor();
        threadpool.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("执行时间：" + LocalDateTime.now());
            }
        }, 2, TimeUnit.SECONDS);


        /**
         * 单线程的线程池又什么意义？
         *
         *  1. 复用线程。
         *  2. 单线程的线程池提供了任务队列和拒绝策略（newSingleThreadScheduledExecutor当任务队列满了之后（Integer.MAX_VALUE），新来的任务就会拒绝策略）
         */
        //单线程线程池 同c_newsinglethreadexecutor 下 a_newSingleThreadPoolMain
        ExecutorService threadpool2 = Executors.newSingleThreadScheduledExecutor();
        threadpool2.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程名称：" + Thread.currentThread().getName());
            }
        });
    }
}
