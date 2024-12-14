package com.mc.shuati;

import java.util.Scanner;

public class Main3 {

    /**
     * 自己实现一个链表类。读入两个有序链表（链表的值是排序过的），打印两个链表重合的数字（两个链表中都有的数字）
     * 例如输入两行是“1 2 3 4 5”和“2 4 8”，输出“2 4”
     * 注意：不得使用外部的list、set、map等判断重复。
     * @param args
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] first = in.nextLine().split(" ");
        String[] tw = in.nextLine().split(" ");
        ListNode listNode1 = new ListNode(Integer.parseInt(first[0]));
        ListNode curr = listNode1;
        for (int i = 1; i < first.length; i++) {
            int v = Integer.parseInt(first[i]);
            curr.next = new ListNode(v);
            curr = curr.next;
        }

        ListNode listNode2 = new ListNode(Integer.parseInt(tw[0]));
        ListNode curr1 = listNode2;
        for (int i = 1; i < tw.length; i++) {
            int v = Integer.parseInt(tw[i]);
            curr1.next = new ListNode(v);
            curr1 = curr1.next;
        }

        StringBuilder stringBuilder = new StringBuilder();
        ListNode listNode = listNode2;
        while (listNode1 != null) {
            int v1 = listNode1.val;
            while (listNode2 != null) {
                int v2 = listNode2.val;
                if (v1 == v2) {
                    stringBuilder.append(v1).append(" ");
                }
                if (listNode2 != null) {
                    listNode2 = listNode2.next;
                }
            }
            if (listNode1 != null) {
                listNode1 = listNode1.next;
                listNode2 = listNode;
            }
        }
        System.out.println(stringBuilder.toString());
    }

    static class ListNode{
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }

        ListNode(ListNode next) {
            this.next = next;
        }
    }

}
