package leetcode;

import java.util.Stack;

/**
 * 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。
 *
 * 示例：
 *
 * 给定一个链表: 1->2->3->4->5, 和 n = 2.
 *
 * 当删除了倒数第二个节点后，链表变为 1->2->3->5.
 * 说明：
 *
 * 给定的 n 保证是有效的。
 *
 * 进阶：
 *
 * 你能尝试使用一趟扫描实现吗？
 *
 */
public class No19 {

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    /**
     * 栈
     * pop n-1
     * pop n
     * pop n+1
     * 修改next指针
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        Stack<ListNode> stack = new Stack<>();
        ListNode node = head;
        while (node != null) {
            stack.push(node);
            node = node.next;
        }
        for (int i = 1; i < n - 1; i++) {
            stack.pop();
        }
        //删了尾元素(n==1)
        ListNode pre = n == 1 ? null : stack.pop();
        //只有一个元素，删了
        if (stack.isEmpty()) {
            return null;
        }
        stack.pop();
        //删了头元素
        if (stack.isEmpty()) {
            return pre;
        }
        stack.pop().next = pre;
        return head;
    }


    /**
     * 标准答案
     * 通过设置哑节点，规避特殊情况的判断
     * 双指针，固定间隔为n
     */
    public ListNode removeNthFromEnd2(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode first = dummy;
        ListNode second = dummy;
        for (int i = 1; i <= n + 1; i++) {
            first = first.next;
        }
        while (first != null) {
            first = first.next;
            second = second.next;
        }
        second.next = second.next.next;
        return dummy.next;
    }


    public static void main(String[] args) {
        No19 no19 = new No19();
        ListNode listNode1 = no19.new ListNode(1);
        ListNode listNode2 = no19.new ListNode(2);
        ListNode listNode3 = no19.new ListNode(3);
        ListNode listNode4 = no19.new ListNode(4);
        ListNode listNode5 = no19.new ListNode(5);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        ListNode listNode = no19.removeNthFromEnd(listNode1, 2);
        while (listNode != null) {
            System.out.println(listNode.val);
            listNode = listNode.next;
        }
        ListNode node = no19.removeNthFromEnd(no19.new ListNode(1), 1);
        while (node != null) {
            System.out.println(node.val);
            node = node.next;
        }
    }
}
