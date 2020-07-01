package leetcode;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 合并 k 个排序链表，返回合并后的排序链表。请分析和描述算法的复杂度。
 *
 * 示例:
 *
 * 输入:
 * [
 *   1->4->5,
 *   1->3->4,
 *   2->6
 * ]
 * 输出: 1->1->2->3->4->4->5->6
 *
 */
public class No23 {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> queue = new PriorityQueue<>((o1,o2)->o1.val-o2.val);
        for (ListNode node : lists) {
            if (node != null) {
                queue.add(node);
            }

        }

        ListNode head = queue.peek();
        ListNode cur = queue.poll();
        if (cur != null && cur.next != null) {
            queue.add(cur.next);
        }
        while (!queue.isEmpty()) {
            cur.next = queue.poll();
            if (cur.next.next != null) {
                queue.add(cur.next.next);
            }
            cur = cur.next;
        }
        return head;
    }

    public static void main(String[] args) {
        No23 no23 = new No23();
        ListNode listNode11 = no23.new ListNode(1);
        ListNode listNode12 = no23.new ListNode(4);
        ListNode listNode13 = no23.new ListNode(5);
        listNode11.next = listNode12;
        listNode12.next = listNode13;

        ListNode listNode21 = no23.new ListNode(1);
        ListNode listNode22 = no23.new ListNode(3);
        ListNode listNode23 = no23.new ListNode(4);
        listNode21.next = listNode22;
        listNode22.next = listNode23;

        ListNode listNode31 = no23.new ListNode(2);
        ListNode listNode32 = no23.new ListNode(6);
        listNode31.next = listNode32;

        ListNode[] lists = new ListNode[3];
        lists[0] = listNode11;
        lists[1] = listNode21;
        lists[2] = listNode31;

        ListNode listNode = no23.mergeKLists(lists);
        while (listNode != null) {
            System.out.println(listNode.val);
            listNode = listNode.next;
        }
    }
}
