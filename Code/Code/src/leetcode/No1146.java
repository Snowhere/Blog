package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 实现支持下列接口的「快照数组」- SnapshotArray：
 *
 * SnapshotArray(int length) - 初始化一个与指定长度相等的 类数组 的数据结构。初始时，每个元素都等于 0。
 * void set(index, val) - 会将指定索引 index 处的元素设置为 val。
 * int snap() - 获取该数组的快照，并返回快照的编号 snap_id（快照号是调用 snap() 的总次数减去 1）。
 * int get(index, snap_id) - 根据指定的 snap_id 选择快照，并返回该快照指定索引 index 的值。
 *  
 *
 * 示例：
 *
 * 输入：["SnapshotArray","set","snap","set","get"]
 *      [[3],[0,5],[],[0,6],[0,0]]
 * 输出：[null,null,0,null,5]
 * 解释：
 * SnapshotArray snapshotArr = new SnapshotArray(3); // 初始化一个长度为 3 的快照数组
 * snapshotArr.set(0,5);  // 令 array[0] = 5
 * snapshotArr.snap();  // 获取快照，返回 snap_id = 0
 * snapshotArr.set(0,6);
 * snapshotArr.get(0,0);  // 获取 snap_id = 0 的快照中 array[0] 的值，返回 5
 *  
 *
 * 提示：
 *
 * 1 <= length <= 50000
 * 题目最多进行50000 次set，snap，和 get的调用 。
 * 0 <= index < length
 * 0 <= snap_id < 我们调用 snap() 的总次数
 * 0 <= val <= 10^9
 *
 */
public class No1146 {

    /**
     * 数组(每个元素)+链表(版本号)的形式
     * 链表查询效率太低，优化为树
     * List<TreeMap<version,value>>
     * floorKey 方法可以返回小于等于 version 的最大 key
     */
    class SnapshotArray {
        List<TreeMap<Integer, Integer>> list = new ArrayList<>();
        int version = 0;

        //初始，默认值是0，因为没有snap，所以不赋值
        public SnapshotArray(int length) {
            for (int i = 0; i < length; i++) {
                list.add(new TreeMap<>());
            }
        }

        //set 时，把
        public void set(int index, int val) {
            TreeMap<Integer, Integer> map = list.get(index);
            map.put(version, val);
        }

        public int snap() {
            return version++;
        }

        public int get(int index, int snap_id) {
            TreeMap<Integer, Integer> map = list.get(index);
            Map.Entry<Integer, Integer> entry = map.floorEntry(snap_id);
            return entry == null ? 0 : entry.getValue();
        }
    }


    public static void main(String[] args) {
        No1146 no1146 = new No1146();
        SnapshotArray snapshotArray = no1146.new SnapshotArray(3);
        snapshotArray.set(0, 5);
        System.out.println(snapshotArray.snap());
        snapshotArray.set(0, 6);
        System.out.println(snapshotArray.get(0, 0));
        //
        SnapshotArray s = no1146.new SnapshotArray(3);
        s.snap();
        s.snap();
        s.set(0, 4);
        s.snap();
        System.out.println(s.get(0, 1));
        s.set(0, 12);
        System.out.println(s.get(0, 1));
        s.snap();
        System.out.println(s.get(0, 3));
    }
}
