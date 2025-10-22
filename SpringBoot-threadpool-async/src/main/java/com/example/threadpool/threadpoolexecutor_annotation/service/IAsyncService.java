package com.example.threadpool.threadpoolexecutor_annotation.service;

import com.example.threadpool.threadpoolexecutor_annotation.model.Person;

import java.util.List;
import java.util.concurrent.CountDownLatch;

//@Async(value = "asyncServiceExecutor") 大单元放在类上
public interface IAsyncService {

    void executeAsyncCarKind(List<Person> personList, CountDownLatch countDownLatch, int i);

    void asyncMethod();
}
