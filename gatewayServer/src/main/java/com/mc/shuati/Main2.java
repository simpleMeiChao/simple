package com.mc.shuati;

import org.apache.commons.lang.text.StrBuilder;

import java.util.*;

public class Main2 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String[] arr = in.nextLine().split(";");
        String[] brr = in.nextLine().split(";");

        Map<String, Integer> map = new HashMap<>();
        Map<String, Integer> kMap = new HashMap<>();
        loop(arr, map, kMap);
        loop(brr, map, kMap);
        List<entity> list = new ArrayList<>();
        kMap.forEach((k, v) -> {
            if (v == 2) {
                list.add(new entity(k.substring(0, 5), k.substring(5), map.get(k)));
            }
        });
        list.sort((a, b) -> {
            if (!a.id.equals(b.id)) {
                return a.id.compareTo(b.id);
            } else {
                if (a.sum != b.sum) {
                    return b.sum - a.sum;
                }
                return a.number.compareTo(b.number);
            }
        });

        if (list.size() == 0) {
            System.out.println("NULL");
        } else {
            String id = "";
            StringBuilder sb = new StringBuilder();
            for (entity ent : list) {
                if (!id.equals(ent.id)) {
                    if (sb.length() > 0) {
                        sb = new StringBuilder(sb.substring(0, sb.length() - 1));
                        sb.append("\r\n");
                    }
                    id = ent.id;
                    sb.append(ent.id).append("\r\n");
                }
                sb.append(ent.id + ent.number + ";");
            }
            System.out.println(sb.substring(0, sb.length() - 1));
        }

    }

    private static void loop(String[] arr, Map<String, Integer> map, Map<String, Integer> kMap) {
        for (String s : arr) {
            String[] ay = s.split(",");
            String ay0 = ay[0];
            int ay1 = Integer.parseInt(ay[1]);
            map.put(ay0, map.getOrDefault(ay0, 0) + ay1);
            kMap.put(ay0, kMap.getOrDefault(ay0, 0) + 1);
        }
    }


    static class entity {
        String id;
        String number;
        int sum;

        entity(String id, String number, int sum) {
            this.id = id;
            this.number = number;
            this.sum = sum;
        }
    }

    //        for (String s : arr) {
//            String[] ay = s.split(",");
//            String ay0 = ay[0];
//            int ay1 = Integer.parseInt(ay[1]);
//            map.put(ay0, map.getOrDefault(ay0, 0) + ay1);
//            kMap.put(ay0, kMap.getOrDefault(ay0, 0) + 1);
//        }
//
//        for (String n : brr) {
//            String[] by = n.split(",");
//            String ay0 = by[0];
//            int ay1 = Integer.parseInt(by[1]);
//            map.put(ay0, map.getOrDefault(ay0, 0) + ay1);
//            kMap.put(ay0, kMap.getOrDefault(ay0, 0) + 1);
//        }
}
