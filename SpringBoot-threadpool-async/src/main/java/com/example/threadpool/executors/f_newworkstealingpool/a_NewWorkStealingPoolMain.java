package com.example.threadpool.executors.f_newworkstealingpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建⼀个抢占式执⾏的线程池（任务执⾏顺序不确定）
 * 根据当前CPU⽣成线程池
 */
public class a_NewWorkStealingPoolMain {
    public static void main(String[] args) {
        ExecutorService threadpool = Executors.newWorkStealingPool();
        for (int i = 0; i < 10; i++) {
            threadpool.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程名：" + Thread.currentThread().getName());
                }
            });

            while(!threadpool.isTerminated()){

            }
        }
    }
}
