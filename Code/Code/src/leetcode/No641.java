package leetcode;
/**
 * 设计实现双端队列。
 * 你的实现需要支持以下操作：
 *
 * MyCircularDeque(k)：构造函数,双端队列的大小为k。
 * insertFront()：将一个元素添加到双端队列头部。 如果操作成功返回 true。
 * insertLast()：将一个元素添加到双端队列尾部。如果操作成功返回 true。
 * deleteFront()：从双端队列头部删除一个元素。 如果操作成功返回 true。
 * deleteLast()：从双端队列尾部删除一个元素。如果操作成功返回 true。
 * getFront()：从双端队列头部获得一个元素。如果双端队列为空，返回 -1。
 * getRear()：获得双端队列的最后一个元素。 如果双端队列为空，返回 -1。
 * isEmpty()：检查双端队列是否为空。
 * isFull()：检查双端队列是否满了。
 * 示例：
 *
 * MyCircularDeque circularDeque = new MycircularDeque(3); // 设置容量大小为3
 * circularDeque.insertLast(1);			        // 返回 true
 * circularDeque.insertLast(2);			        // 返回 true
 * circularDeque.insertFront(3);			        // 返回 true
 * circularDeque.insertFront(4);			        // 已经满了，返回 false
 * circularDeque.getRear();  				// 返回 2
 * circularDeque.isFull();				        // 返回 true
 * circularDeque.deleteLast();			        // 返回 true
 * circularDeque.insertFront(4);			        // 返回 true
 * circularDeque.getFront();				// 返回 4
 *  
 *  
 *
 * 提示：
 *
 * 所有值的范围为 [1, 1000]
 * 操作次数的范围为 [1, 1000]
 * 请不要使用内置的双端队列库。
 * 通过次数8,911提交次数16,969
 *
 */
public class No641 {

    /**
     * 数组作为环，实现双端队列
     * head指向第一个元素，tail指向第一个空白位置
     * 需要额外一个节点区分 empty 和 full,(或者单独用个 size 变量记录)
     */
    int[] container;
    int head, tail;

    /**
     * Initialize your data structure here. Set the size of the deque to be k.
     */
    public No641(int k) {
        container = new int[k + 1];
    }

    /**
     * Adds an item at the front of Deque. Return true if the operation is successful.
     */
    public boolean insertFront(int value) {
        if (isFull()) {
            return false;
        } else {
            head = (head + container.length - 1) % container.length;
            container[head] = value;
            return true;
        }
    }

    /**
     * Adds an item at the rear of Deque. Return true if the operation is successful.
     */
    public boolean insertLast(int value) {
        if (isFull()) {
            return false;
        } else {
            container[tail] = value;
            tail = (tail + 1) % container.length;
            return true;
        }
    }

    /**
     * Deletes an item from the front of Deque. Return true if the operation is successful.
     */
    public boolean deleteFront() {
        if (isEmpty()) {
            return false;
        } else {
            head = (head + 1) % container.length;
            return true;
        }
    }

    /**
     * Deletes an item from the rear of Deque. Return true if the operation is successful.
     */
    public boolean deleteLast() {
        if (isEmpty()) {
            return false;
        } else {
            tail = (tail + container.length - 1) % container.length;
            return true;
        }
    }

    /**
     * Get the front item from the deque.
     */
    public int getFront() {
        if (isEmpty()) {
            return -1;
        } else {
            return container[head];
        }
    }

    /**
     * Get the last item from the deque.
     */
    public int getRear() {
        if (isEmpty()) {
            return -1;
        } else {
            return container[(tail + container.length - 1) % container.length];
        }
    }

    /**
     * Checks whether the circular deque is empty or not.
     */
    public boolean isEmpty() {
        return head == tail;
    }

    /**
     * Checks whether the circular deque is full or not.
     */
    public boolean isFull() {
        return (tail + 1) % container.length == head;
    }

    public static void main(String[] args) {
        No641 obj = new No641(3);
        System.out.println(obj.insertLast(1));      // 返回 true
        System.out.println(obj.insertLast(2));      // 返回 true
        System.out.println(obj.insertFront(3));     // 返回 true
        System.out.println(obj.insertFront(4));     // 已经满了，返回 false
        System.out.println(obj.getRear());                // 返回 2
        System.out.println(obj.isFull());                 // 返回 true
        System.out.println(obj.deleteLast());             // 返回 true
        System.out.println(obj.insertFront(4));     // 返回 true
        System.out.println(obj.getFront());               // 返回 4
    }
}
