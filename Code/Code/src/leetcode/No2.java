package leetcode;
/**
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 *
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 *
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 *
 * 示例：
 *
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 *
 */
public class No2 {

    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
        }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode fakeHead = new ListNode(0);
        ListNode nextNode = fakeHead;
        int tmp = 0;
        while (l1 != null || l2 != null) {
            int sum = 0;
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }
            sum += tmp;
            nextNode.next = new ListNode(sum % 10);
            nextNode = nextNode.next;
            tmp = sum / 10;
        }
        if (tmp != 0) {
            nextNode.next = new ListNode(tmp);
        }
        return fakeHead.next;
    }

    public static void main(String[] args) {
        No2 no2 = new No2();
        ListNode listNode11 = no2.new ListNode(2);
        ListNode listNode12 = no2.new ListNode(4);
        ListNode listNode13 = no2.new ListNode(3);
        ListNode listNode21 = no2.new ListNode(5);
        ListNode listNode22 = no2.new ListNode(6);
        ListNode listNode23 = no2.new ListNode(4);
        listNode11.next = listNode12;
        listNode12.next = listNode13;
        listNode21.next = listNode22;
        listNode22.next = listNode23;
        ListNode node = no2.addTwoNumbers(listNode11, listNode21);
        while (node != null) {
            System.out.println(node.val);
            node = node.next;
        }
    }
}
