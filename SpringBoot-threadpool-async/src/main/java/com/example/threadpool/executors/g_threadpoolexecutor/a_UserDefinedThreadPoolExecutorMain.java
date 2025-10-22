package com.example.threadpool.executors.g_threadpoolexecutor;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadPoolExecutor是底层的类
 * ThreadPoolTaskExecutor 是Spring框架中提供的一个类
 * 它扩展了java.util.concurrent.Executor接口，并提供了更多的配置选项，如队列容量、线程名称前缀、拒绝策略等。
 * ThreadPoolTaskExecutor通常在Spring的配置文件中配置，或者在Spring应用中通过编程方式创建。
 */
public class a_UserDefinedThreadPoolExecutorMain {
    public static void main(String[] args) {
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
//                thread.setName("我的线程名称-"+ r.hashCode());
                thread.setPriority(Thread.MAX_PRIORITY);
                thread.setDaemon(false);
                return thread;
            }
        };

        ThreadPoolExecutor threadpool = new ThreadPoolExecutor(
                5,
                5,
                10,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(99999),
                threadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy());
        //RejectedExecutionHandler （队列）参数
        // AbortPolicy 提示异常，拒绝执行（默认的拒绝策略）
        // DiscardPolicy 忽略最新的任务
        // CallerRunsPolicy 使用调用线程池的线程来执行任务
        // DiscardOldestPolicy 忽略旧任务(队列第一个任务)

        for (int i = 0; i < 40; i++) {
            int finalI = i;
            threadpool.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "执行任务：" + finalI);
                }
            });
        }

        //终止线程池
        threadpool.shutdown();

    }
}
