package com.example.threadpool.threadpoolexecutor_annotation.service.impl;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.example.threadpool.threadpoolexecutor_annotation.model.Person;
import com.example.threadpool.threadpoolexecutor_annotation.service.IAsyncService;
import com.example.threadpool.util.ListUtil;
import com.example.threadpool.util.ParseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 本文的策略是将数据全部查出再进行分段,防止OOM
 */
@Service(value = "asyncService")
@Slf4j
public class AsyncServiceImpl implements IAsyncService {
    @Value("${filepath}")
    private String filepath;

    @Autowired
    private DataSource dataSource;

    @Async(value = "asyncServiceExecutor")  //最小单元放在方法上
    @Override
    public void executeAsyncCarKind(List<Person> personList, CountDownLatch countDownLatch, int batch) {

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            DynamicDataSourceContextHolder.push("master");//动态切换数据源
            System.out.println("异步方法开始执行,批次为"+batch);
            String sql = "INSERT INTO business_user ( name, age, id_no, zh_code, kch_code ) VALUES ( ?, ?, ?, ?, ? )";
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);//取消自动提交
            int i = 0;
            for (Person person : personList) {
                ps.setString(1, (person.getName()));
                ps.setInt(2, person.getAge());
                ps.setString(3, person.getIdNo());
                ps.setString(4, person.getZhCode());
                ps.setString(5, person.getKchCode());
                ps.addBatch();
                if (i % 500 == 0) {
                    ps.executeBatch();//将容器中的sql语句提交
                    ps.clearBatch();//清空容器，为下一次打包做准备
                }
                i++;
            }
            //为防止有sql语句漏提交【如i结束时%500！=0的情况】，需再次提交sql语句
            ps.executeBatch();
            ps.clearBatch();
            conn.commit();
            System.out.println("异步方法执行完毕,批次为"+batch);
        } catch (Exception e) {
            log.error("批量插入数据异常", e);
        } finally {
            countDownLatch.countDown();
            DynamicDataSourceContextHolder.poll();
            close(conn, ps);
        }
    }

    @Override
    public void asyncMethod() {

        Long startTime1 = new Date().getTime();
        log.info("解析CSV开始时间（ms）：" + startTime1);
        List<Person> list = ParseUtil.parseCsv(filepath);
        Long endTime1 = new Date().getTime();
        log.info("解析CSV结束时间（ms）：" + endTime1 + ",总耗时ms:" + (endTime1 - startTime1));

        removeAll(); //清除表中所有数据
        CountDownLatch countDownLatch;
        try {
            List<List<Person>> partition = ListUtil.partition(list, 5000);
            countDownLatch = new CountDownLatch(partition.size());
            Long startTime = new Date().getTime();
            log.info("批量插入数据库开始 ===>" );
            for (int i = 0; i < partition.size(); i++) {
                executeAsyncCarKind(partition.get(i), countDownLatch, i);

            }
            Long endTime = new Date().getTime();
            log.info("批量插入数据库结束,总耗时ms:" + (endTime - startTime));
        } finally {

        }
    }

    /**
     * JDBC数据库连接关闭
     *
     * @param conn 连接
     * @param ps   预编译
     */
    private void close(Connection conn, PreparedStatement ps) {
        try {
            if (ps != null) ps.close();
        } catch (Exception e) {
            log.error("关闭数据库连接异常", e);
        }
        try {
            if (conn != null) conn.close();
        } catch (Exception e) {
            log.error("关闭数据库连接异常", e);
        }
    }

    public void removeAll(){
        DynamicDataSourceContextHolder.push("master");//切换数据源
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            String sql = "truncate table business_user";
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            ps = conn.prepareStatement(sql);
            ps.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            DynamicDataSourceContextHolder.poll();
            close(conn,ps);
        }
    }
}
