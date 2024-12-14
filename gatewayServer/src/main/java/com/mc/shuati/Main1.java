package com.mc.shuati;

import java.util.*;

public class Main1 {

    /**
     * 总共有7轮牌面。
     * 第一轮选择该轮牌面，总分数为1。
     * 第二轮不选择该轮牌面，总分数还原为0。
     * 第三轮不选择该轮牌面，总分数还原为0。
     * 第四轮选择该轮牌面，总分数为4。
     * 第五轮选择该轮牌面，总分数为7。
     * 第六轮选择该轮牌面，总分数为13。
     * 第七轮如果不选择该轮牌面，则总分数还原到3轮1前分数，即第四轮的总分数4，如果选择该轮牌面，总分数为11，所以选择该轮牌面。
     * 因此，最终的最高总分为11。-1,-5,-4,-4,-3,-6,-2
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] arr = Arrays.stream(scanner.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        int max = 0;
//        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> sumList = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            int index = i + 1;
            if (arr[i] > 0) {
                max += arr[i];
                sumList.add(max);
//                map.put(index, max);
            } else {
                if (i <= 2) {
                    max = sum1(arr[i], max);
                } else {
                    max =sum2(arr[i], max, sumList, index);
                }
                sumList.add(max);
//                map.put(index, max);
            }
        }
        System.out.println(max);
    }

    private static int sum2(int tar, int sum, List<Integer> sumList, int index) {
        int s = sumList.get(index - 3);
//        int s = map.getOrDefault(index - 3, 0);
        return Math.max(sum + tar, s);
    }

    private static int sum1(int tar, int sum) {
        if (sum + tar > 0) {
            return sum + tar;
        } else {
            return 0;
        }
    }
}
