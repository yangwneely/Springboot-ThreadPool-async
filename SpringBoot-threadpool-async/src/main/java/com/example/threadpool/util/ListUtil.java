package com.example.threadpool.util;


import java.util.ArrayList;
import java.util.List;

/**
 * 列表工具类
 * 提供列表相关的工具方法
 */
public class ListUtil {

    /**
     * 将列表按指定大小分割成多个子列表
     * @param <T> 列表元素类型
     * @param sourceList 源列表
     * @param partitionSize 每个分区的大小
     * @return 分割后的子列表集合
     * @throws IllegalArgumentException 如果partitionSize小于等于0
     */
    public static <T> List<List<T>> partition(List<T> sourceList, int partitionSize) {
        if (partitionSize <= 0) {
            throw new IllegalArgumentException("分区大小必须大于0");
        }

        if (sourceList == null || sourceList.isEmpty()) {
            return new ArrayList<>();
        }

        List<List<T>> partitions = new ArrayList<>();
        int size = sourceList.size();

        for (int i = 0; i < size; i += partitionSize) {
            int end = Math.min(size, i + partitionSize);
            partitions.add(new ArrayList<>(sourceList.subList(i, end)));
        }

        return partitions;
    }

    /**
     * 重载方法：使用默认分区大小1000
     * @param <T> 列表元素类型
     * @param sourceList 源列表
     * @return 分割后的子列表集合
     */
    public static <T> List<List<T>> partition(List<T> sourceList) {
        return partition(sourceList, 1000);
    }
}

