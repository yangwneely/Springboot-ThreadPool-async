package com.example.threadpool.executors.b_newcachedthreadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 优点：线程池会根据任务数量创建线程池，并且在一定时间内可以重复使用这些线程，产生相应的线程池。
 * 缺点：适用于短时间有大量任务的场景，它的缺点是可能会占用很多资源。
 */
public class a_newCachedThreadPoolMain {
    public static void main(String[] args) {
        ExecutorService threadpool = Executors.newCachedThreadPool();

        //添加任务 方式1
        for (int i = 0; i < 20; i++) {
            int finalI = i;
            threadpool.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("i:" + finalI + "|线程名称：" + Thread.currentThread().getName());
                }
            });
        }

    }
}
