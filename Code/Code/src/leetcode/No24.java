package leetcode;
/**
 * 给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。
 *
 * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
 *
 *  
 *
 * 示例:
 *
 * 给定 1->2->3->4, 你应该返回 2->1->4->3.
 *
 */
public class No24 {

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
    public ListNode swapPairs(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode first = head;
        ListNode second = head.next;
        if (second != null) {
            first.next = swapPairs(second.next);
            second.next = first;
            return second;
        } else {
            return first;
        }
    }

    public static void main(String[] args) {
        No24 no24 = new No24();
        ListNode listNode1 = no24.new ListNode(1);
        ListNode listNode2 = no24.new ListNode(2);
        ListNode listNode3 = no24.new ListNode(3);
        ListNode listNode4 = no24.new ListNode(4);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        ListNode listNode = no24.swapPairs(listNode1);
        while (listNode != null) {
            System.out.println(listNode.val);
            listNode = listNode.next;
        }
    }
}
