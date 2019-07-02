# JDK 完全向下兼容吗？JDK1.7 新的内置排序算法 TimSort 引发的异常

从 1997 年 JDK1.1 面世，20 年间 Java 已经发布了 10 多个版本，而我们都知道 Java 的兼容性很强，低版本编译的项目可以运行在高版本的 Java 上，平时工程项目升级 Java 版本时无需任何顾虑。
但是真的是这样吗。我们来看下面一段代码：
```
public static void main(String[] args) {
    int[] sample = new int[]
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -2, 1, 0, -2, 0, 0, 0, 0};
    List<Integer> list = new ArrayList<Integer>();
    for (int i : sample)
        list.add(i);
    Collections.sort(list, new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1 > o2 ? 1 : -1;
        }
    });
    System.out.println(list);
}
```
这段代码是调用 Java 内置排序方法 `Collections.sort()` 来排序一个数组，用 JDK1.6 版本运行没问题，但如果用 JDK1.7 就会抛出异常。

原因有两点
1. JDK1.7 将内置排序算法改为了 TimSort。
2. 我们 Comparator 接口的实现并不规范。

## Comparator

我们先来看一下源代码中 Comparator 接口下 compare() 方法的注释：
>
Compares its two arguments for order. Returns a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.
In the foregoing description, the notation sgn(expression) designates the mathematical signum function, which is defined to return one of -1, 0, or 1 according to whether the value of expression is negative, zero or positive.
The implementor must ensure that sgn(compare(x, y)) == -sgn(compare(y, x)) for all x and y. (This implies that compare(x, y) must throw an exception if and only if compare(y, x) throws an exception.)
The implementor must also ensure that the relation is transitive: ((compare(x, y)>0) && (compare(y, z)>0)) implies compare(x, z)>0.
Finally, the implementor must ensure that compare(x, y)==0 implies that sgn(compare(x, z))==sgn(compare(y, z)) for all z.
It is generally the case, but not strictly required that (compare(x, y)==0) == (x.equals(y)). Generally speaking, any comparator that violates this condition should clearly indicate this fact. The recommended language is "Note: this comparator imposes orderings that are inconsistent with equals."

简单总结一下就是要保证以下几点： 
* compare(x,y) 和 compare(y,x) 的正负相反；
* 如果 compare(x,y)>0，并且 compare(y,z)>0，那么 compare(x,z)>0；
* 如果 compare(x,y)==0，那么 compare(x,z) 和 compare(y,z) 正负相同

我们上面代码实现的 compare 方法中，如果传入的两个对象相等，compare(x,y) 和 compare(y,x) 都会返回 -1，没有保证上面说的第一点。其实一般的排序算法并不需要严格保证 compare 方法，只需要两个对象简单比较一下。比如 JDK1.6 内置排序算法在元素个数小于 7 时会使用简单的冒泡排序（默认算法是归并排序）：
```
if (length < INSERTIONSORT_THRESHOLD) {
    for (int i=low; i<high; i++)
        for (int j=i; j>low && c.compare(dest[j-1], dest[j])>0; j--)
            swap(dest, j, j-1);
    return;
}
```
我们实现的 compare 在这种插入排序中完全适用，但 JDK1.7 中默认排序算法改为了 TimeSort，就让我们来深入了解一下这种排序算法。

## TimSort

TimSort 的起源和历史我就不多说了，最早应用在 [python 的内置排序中](http://svn.python.org/projects/python/trunk/Objects/listsort.txt)。TimSort 的核心就是 归并排序+二分查找插入排序，并进行大量优化，主要思路如下：
1. 对原序列分块（称之为 run）
2. 每个 run 排好序
3. 合并 run
我们来看一下 JDK1.7 中具体实现

### 前置代码
首先进入排序逻辑会先判断一个 jvm 参数变量，选择使用旧的归并排序（元素个数小于 7 时用冒泡排序），还是使用 TimeSort 进行排序。默认为使用 TimSort。
```
if (LegacyMergeSort.userRequested)
    legacyMergeSort(a, c);
else
    TimSort.sort(a, c);
```

进入 TimSort 代码后会进行一些校验和判断，比如判断元素个数少于 32 则会通过一个迷你 TimSort 进行排序。
```
// If array is small, do a "mini-TimSort" with no merges
if (nRemaining < MIN_MERGE) {
    int initRunLen = countRunAndMakeAscending(a, lo, hi, c);
    binarySort(a, lo, hi, lo + initRunLen, c);
    return;
}
```
`countRunAndMakeAscending()` 方法寻找原始元素数组 `a` 中从 `lo` 位置开始的最长单调递增或递减序列（递减序列会被反转）。这样，最前面这部分元素相当于排好序了。随后用 `binarySort()` 方法将后面的元素一个个通过二分查找插入到前面排序好的数组中，从而实现整个数组排好序。

### 1.划分 run


结论
我们只能确定低版本编译的代码可以运行在高版本的 Java，但却无法保证运行的行为和结果与低版本一致。