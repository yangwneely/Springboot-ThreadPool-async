package com.example.threadpool.util;

import com.example.threadpool.threadpoolexecutor_annotation.model.Person;
import sun.text.normalizer.UTF16;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ParseUtil {

    public static List<Person> parseCsv(String filename) {
        List<Person> personList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "GBK"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // 跳过空行
                if (line.trim().isEmpty()) {
                    continue;
                }

                // 按逗号分割字段
                String[] fields = line.split(",");

                // 验证字段数量
                if (fields.length != 5) {
                    System.err.println("跳过格式错误的行: " + line);
                    continue;
                }

                try {
                    // 解析字段并创建Person对象
                    String name = fields[0].trim();
                    int age = Integer.parseInt(fields[1].trim());
                    String idNo = fields[2].trim();
                    String zhCode = fields[3].trim();
                    String kchCode = fields[4].trim();

                    Person person = new Person(name, age, idNo, zhCode, kchCode);
                    personList.add(person);

                } catch (NumberFormatException e) {
                    System.err.println("年龄格式错误，跳过该行: " + line);
                }

            }
        } catch (IOException e) {
            System.err.println("读取文件时发生错误: " + e.getMessage());
            e.printStackTrace();
        }

        return personList;
    }
}
