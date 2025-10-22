package com.example.threadpool.util;


import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;

public class BeanUtil {

    /**
     * 将源列表深拷贝到目标类型的新列表中
     * @param sourceList 源对象列表
     * @param targetClass 目标类类型
     * @return 深拷贝后的新列表
     */
    public static <T> List<T> copyToList(List<?> sourceList, Class<T> targetClass) {
        if (sourceList == null) {
            return null;
        }

        List<T> targetList = new ArrayList<>();

        for (Object source : sourceList) {
            if (source == null) {
                targetList.add(null);
                continue;
            }

            try {
                T target = targetClass.getDeclaredConstructor().newInstance();
                copyProperties(source, target);
                targetList.add(target);
            } catch (Exception e) {
                throw new RuntimeException("拷贝对象失败", e);
            }
        }

        return targetList;
    }

    /**
     * 拷贝对象属性
     * @param source 源对象
     * @param target 目标对象
     */
    private static void copyProperties(Object source, Object target) {
        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();

        Field[] sourceFields = sourceClass.getDeclaredFields();

        for (Field sourceField : sourceFields) {
            try {
                sourceField.setAccessible(true);
                Object value = sourceField.get(source);

                Field targetField;
                try {
                    targetField = targetClass.getDeclaredField(sourceField.getName());
                } catch (NoSuchFieldException e) {
                    continue; // 目标类没有对应字段则跳过
                }

                targetField.setAccessible(true);
                targetField.set(target, value);

            } catch (IllegalAccessException e) {
                throw new RuntimeException("属性拷贝失败: " + sourceField.getName(), e);
            }
        }
    }
}

