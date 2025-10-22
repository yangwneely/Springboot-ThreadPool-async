package com.example.threadpool.threadpoolexecutor_annotation.controller;

import com.example.threadpool.threadpoolexecutor_annotation.service.IAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/bus")
public class BusinessController {
    @Autowired
    private IAsyncService asyncService;

    @GetMapping(value = "/addInfo")
    public void handler(){

        asyncService.asyncMethod();
        log.info("调用线程池插入数据成功！！！！！！！");
    }

}
