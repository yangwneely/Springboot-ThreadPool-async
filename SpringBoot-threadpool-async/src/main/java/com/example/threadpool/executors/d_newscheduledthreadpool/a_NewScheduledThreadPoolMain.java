package com.example.threadpool.executors.d_newscheduledthreadpool;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * scheduleAtFixedRate 是以上⼀次任务的开始时间，作为下次定时任务的参考时间的（参考时间+延迟任务=任务执⾏）。
 * scheduleWithFixedDelay 是以上⼀次任务的结束时间，作为下次定时任务的参考时间的。
 */
public class a_NewScheduledThreadPoolMain {
    public static void main(String[] args) {
        //创建线程池
        ScheduledExecutorService threadpool = Executors.newScheduledThreadPool(5);
        //执行定时任务（延迟3秒执行）只执行一次
        threadpool.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("执行子任务时间：" + LocalDateTime.now());
            }
        }, 3, TimeUnit.SECONDS);


        //2秒后执行定时任务，定时任务每隔4秒执行一次
        threadpool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("间隔执行子任务时间：" + LocalDateTime.now());
            }
        }, 2, 4, TimeUnit.SECONDS);
    }

}
