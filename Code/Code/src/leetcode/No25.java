package leetcode;
/**
 * 给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
 *
 * k 是一个正整数，它的值小于或等于链表的长度。
 *
 * 如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
 *
 *  
 *
 * 示例：
 *
 * 给你这个链表：1->2->3->4->5
 *
 * 当 k = 2 时，应当返回: 2->1->4->3->5
 *
 * 当 k = 3 时，应当返回: 3->2->1->4->5
 *
 *  
 *
 * 说明：
 *
 * 你的算法只能使用常数的额外空间。
 * 你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。
 *
 */
public class No25 {

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    /**
     * 递归
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null) {
            return null;
        }
        ListNode pre = head;
        ListNode cur = head.next;
        ListNode tail = head;
        //隔k个元素，没有tail则不翻转，有tail则递归翻转
        for (int i = 0; i < k - 1; i++) {
            tail = tail.next;
            if (tail == null) {
                return head;
            }
        }
        head.next = reverseKGroup(tail.next, k);
        //本组翻转
        ListNode tmp;
        for (int i = 0; i < k - 1; i++) {
            tmp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmp;
        }
        return tail;
    }

    public static void main(String[] args) {
        No25 no25 = new No25();
        ListNode listNode1 = no25.new ListNode(1);
        ListNode listNode2 = no25.new ListNode(2);
        ListNode listNode3 = no25.new ListNode(3);
        ListNode listNode4 = no25.new ListNode(4);
        ListNode listNode5 = no25.new ListNode(5);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        ListNode node = no25.reverseKGroup(listNode1, 3);
        while (node != null) {
            System.out.println(node.val);
            node = node.next;
        }
    }
}
