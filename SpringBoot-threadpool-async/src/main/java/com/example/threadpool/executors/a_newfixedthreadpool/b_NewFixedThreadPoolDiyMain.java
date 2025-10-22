package com.example.threadpool.executors.a_newfixedthreadpool;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 提供的功能：
 * 1.设置(线程池中)线程的命名规则
 * 2.设置线程的优先级
 * 3.设置线程分组
 * 4.设置线程类型（用户线程、守护线程）
 *
 */
public class b_NewFixedThreadPoolDiyMain {
    public static void main(String[] args) throws Exception {

        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                //！！！！！ 一定要注意：要把任务Runnable设置给新创建的线程（这里才能用到线程中中的线程）
                Thread thread = new Thread(r);
                //设置线程的命名规则
                thread.setName("我的线程名称" + r.hashCode());
                //设置线程的优先级
                thread.setPriority(Thread.MAX_PRIORITY);
                //设置为用户线程
                thread.setDaemon(false);
                return thread;
            }
        };

        ExecutorService threadpool = Executors.newFixedThreadPool(2, threadFactory);
        Future<Integer> result = threadpool.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int num = new Random().nextInt();
                System.out.println(Thread.currentThread().getPriority() + "随机数" + num);
                return num;
            }
        });

        System.out.println("返回结果" + result.get());

    }
}
