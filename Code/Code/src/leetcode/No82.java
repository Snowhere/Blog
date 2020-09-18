package leetcode;
/**
 * 给定一个排序链表，删除所有含有重复数字的节点，只保留原始链表中 没有重复出现 的数字。
 *
 * 示例 1:
 *
 * 输入: 1->2->3->3->4->4->5
 * 输出: 1->2->5
 * 示例 2:
 *
 * 输入: 1->1->1->2->3
 * 输出: 2->3
 *
 */
public class No82 {

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode fake = new ListNode(0);
        fake.next = head;
        ListNode begin = fake;
        ListNode end = head;
        while (end != null) {

            //重复区间
            while (end.next != null && end.val == end.next.val) {
                end = end.next;
            }

            if (begin.next != end) {
                //有重复元素，删除区间
                begin.next = end.next;
            } else {
                //没有重复元素，后移起始点
                begin = end;
            }
            end = end.next;
        }
        return fake.next;
    }

    public static void main(String[] args) {
        No82 no82 = new No82();
        ListNode listNode1 = no82.new ListNode(1);
        ListNode listNode2 = no82.new ListNode(2);
        ListNode listNode3 = no82.new ListNode(3);
        ListNode listNode4 = no82.new ListNode(3);
        ListNode listNode5 = no82.new ListNode(4);
        ListNode listNode6 = no82.new ListNode(4);
        ListNode listNode7 = no82.new ListNode(5);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        listNode5.next = listNode6;
        listNode6.next = listNode7;
        ListNode node = no82.deleteDuplicates(listNode1);
        while (node != null) {
            System.out.println(node.val);
            node = node.next;
        }
    }
}
