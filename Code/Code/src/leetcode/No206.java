package leetcode;

/**
 * 反转一个单链表。
 * <p>
 * 示例:
 * <p>
 * 输入: 1->2->3->4->5->NULL
 * 输出: 5->4->3->2->1->NULL
 */
public class No206 {
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
        }
    }

    /**
     * 简单遍历，记录当前节点以及前后节点，头脑清晰捋清指针变化
     * @date 2020-06-28
     */
    public ListNode reverseList(ListNode head) {
        ListNode prev=null;
        ListNode next;
        while (head != null) {
            next = head.next;
            head.next = prev;

            prev = head;
            head = next;
        }
        return prev;
    }

    public static void main(String[] args) {
        No206 no206 = new No206();
        ListNode listNode1 =  no206.new ListNode(1);
        ListNode listNode2 =  no206.new ListNode(2);
        ListNode listNode3 =  no206.new ListNode(3);
        ListNode listNode4 =  no206.new ListNode(4);
        ListNode listNode5 =  no206.new ListNode(5);
        listNode1.next=listNode2;
        listNode2.next=listNode3;
        listNode3.next=listNode4;
        listNode4.next=listNode5;
        ListNode newHead = no206.reverseList(listNode1);
        while (newHead != null) {
            System.out.println(newHead.val);
            newHead=newHead.next;
        }
    }
}
