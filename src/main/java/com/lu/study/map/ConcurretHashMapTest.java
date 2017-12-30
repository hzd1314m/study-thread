package com.lu.study.map;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @autor 10758
 * @system study-thread
 * @Time 2017/12/30
 */
public class ConcurretHashMapTest {

    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        Integer value = concurrentHashMap.put("1", 1);
        if (null == value ) {
            System.out.println(" xxx");
        }
        Integer value2 = concurrentHashMap.put("1", 1);
        if (null != value2) {
            System.out.println(value2);
        }
        Integer value3 = concurrentHashMap.put("1", 2);
        if (null != value3) {
            System.out.println(value3);
            System.out.println(concurrentHashMap.get("1"));
        }

        HashMap<String,Integer> hashMap = new HashMap<>();
        Integer hashMapValue = hashMap.put("1", 1);
        if (null == hashMapValue ) {
            System.out.println(" xxx");
        }
        Integer hashMapValue2 = hashMap.put("1", 1);
        if (null != hashMapValue2) {
            System.out.println(hashMapValue2);
        }
        Integer hashMapValue3 = hashMap.put("1", 2);
        if (null != hashMapValue3) {
            System.out.println(hashMapValue3);
            System.out.println(hashMap.get("1"));
        }

        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("xxxxxx");
            }
        };
        thread.start();
        thread.start();
    }
}
