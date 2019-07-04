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

简单总结一下就是实现这个方法要保证以下几点： 
* compare(x,y) 和 compare(y,x) 的正负相反；
* 如果 compare(x,y)>0，并且 compare(y,z)>0，那么 compare(x,z)>0；
* 如果 compare(x,y)==0，那么 compare(x,z) 和 compare(y,z) 正负相同

我们上面代码实现的 compare 方法中，如果传入的两个对象相等，compare(x,y) 和 compare(y,x) 都会返回 -1，没有保证上面说的第一点。其实一般的排序算法并不需要严格保证 compare 方法，只需要两个对象简单比较一下。比如 JDK1.6 内置排序算法 `Collections.sort()` 使用的是归并排序（JDK1.7 保留了这个算法），并在元素个数小于 `INSERTIONSORT_THRESHOLD`（默认值 7） 时优化为使用简单的冒泡排序：
```
if (length < INSERTIONSORT_THRESHOLD) {
    for (int i=low; i<high; i++)
        for (int j=i; j>low && c.compare(dest[j-1], dest[j])>0; j--)
            swap(dest, j, j-1);
    return;
}
```
我们实现的 compare 在这些排序中完全适用，但 JDK1.7 中默认排序算法改为了 TimeSort，就让我们来深入了解一下这种排序算法。

## TimSort

TimSort 的起源和历史我就不多说了，最早应用在 [python 的内置排序中](http://svn.python.org/projects/python/trunk/Objects/listsort.txt)。TimSort 的核心就是 归并排序+二分查找插入排序，并进行大量优化，主要思路如下：
1. 划分 run（对原序列分块，每个块称之为 run）
2. 排序 run
3. 合并 run
网络上有关介绍这种算法的文章很多，我就不多赘述了，我们来看一下 JDK1.7 中的具体实现

### 代码总览
首先进入排序逻辑会先判断一个 jvm 参数变量，选择使用旧的归并排序（元素个数小于 7 时用冒泡排序），还是使用 TimeSort 进行排序。默认为使用 TimSort。
```
if (LegacyMergeSort.userRequested)
    legacyMergeSort(a, c);
else
    TimSort.sort(a, c);
```

进入 TimSort 代码后会进行一些校验和判断，比如判断元素个数少于 `MIN_MERGE`（默认值 32） 则会通过一个“迷你-TimSort” 进行排序。这是将整个序列看做一个 run，省略了划分 run 和合并 run 两个步骤，直接进行排序 run。
```
// If array is small, do a "mini-TimSort" with no merges
if (nRemaining < MIN_MERGE) {
    int initRunLen = countRunAndMakeAscending(a, lo, hi, c);
    binarySort(a, lo, hi, lo + initRunLen, c);
    return;
}
```

我们来看一下核心的算法流程代码，后面会详细讲解每个步骤：
```
//栈结构，用于保存以及合并 run
TimSort<T> ts = new TimSort<>(a, c);
//确定每个 run 的最小长度
int minRun = minRunLength(nRemaining);
do {
    //划分、排序 run
    // Identify next run
    int runLen = countRunAndMakeAscending(a, lo, hi, c);
    
    // If run is short, extend to min(minRun, nRemaining)
    if (runLen < minRun) {
        int force = nRemaining <= minRun ? nRemaining : minRun;
        binarySort(a, lo, lo + force, lo + runLen, c);
        runLen = force;
    }

    //保存、合并 run
    // Push run onto pending-run stack, and maybe merge
    ts.pushRun(lo, runLen);
    ts.mergeCollapse();

    // Advance to find next run
    lo += runLen;
    nRemaining -= runLen;
} while (nRemaining != 0);

// Merge all remaining runs to complete sort
assert lo == hi;
ts.mergeForceCollapse();
assert ts.stackSize == 1;
```

### 1.划分 run
划分 run 和排序 run 密不可分，TimSort 算法优化的点之一就是尽可能利用原序列的单调子序列。`countRunAndMakeAscending()` 方法寻找原始元素数组 `a` 中从 `lo` 位置开始的最长单调递增或递减序列（递减序列会被反转）。这样，这部分元素相当于排好序了，我们可以直接把它当做一个排序好的 run。但问题随之而来，如果这样的序列很短，会产生很多 run，后续归并的代价就很大，所以我们要控制 run 的长度。下面这段代码规定 run 的最小长度：
```
private static int minRunLength(int n) {
    assert n >= 0;
    int r = 0;      // Becomes 1 if any 1 bits are shifted off
    while (n >= MIN_MERGE) {
        r |= (n & 1);
        n >>= 1;
    }
    return n + r;
}
```
`n` 为整个序列的长度，TimSort 算法优化点之一是通过控制 run 的长度，使 run 的数量保持在 2 的 n 次方，这样在归并的时候，就像二叉树一样进行归并，不会到最后出现非常大的 run 与非常小的 run 归并。代码中 `MIN_MERGE` 为 32，最后计算出的最小 run 长度介于 16 和 32 之间。

### 2.排序 run
```
// Identify next run
int runLen = countRunAndMakeAscending(a, lo, hi, c);
// If run is short, extend to min(minRun, nRemaining)
if (runLen < minRun) {
    int force = nRemaining <= minRun ? nRemaining : minRun;
    binarySort(a, lo, lo + force, lo + runLen, c);
    runLen = force;
}
```
随后在循环中根据计算出的最短 run 长度和剩余序列单调子序列来划分 run，先取出剩余序列开头的单调子序列，如果长度不够规定的最短长度，则用 `binarySort()` 方法将其后的元素一个个通过二分查找插入到这个找出的单调递增数组中，直到长度达到规定的最短长度（或到剩余序列结尾），从而将整个序列划分多个 run，并确保每个 run 都是排好序的。
```
private static <T> void binarySort(T[] a, int lo, int hi, int start,
                                   Comparator<? super T> c) {
    assert lo <= start && start <= hi;
    if (start == lo)
        start++;
    for ( ; start < hi; start++) {
        T pivot = a[start];
        // Set left (and right) to the index where a[start] (pivot) belongs
        int left = lo;
        int right = start;
        assert left <= right;
        /*
         * Invariants:
         *   pivot >= all in [lo, left).
         *   pivot <  all in [right, start).
         */
        while (left < right) {
            int mid = (left + right) >>> 1;
            if (c.compare(pivot, a[mid]) < 0)
                right = mid;
            else
                left = mid + 1;
        }
        assert left == right;
        /*
         * The invariants still hold: pivot >= all in [lo, left) and
         * pivot < all in [left, start), so pivot belongs at left.  Note
         * that if there are elements equal to pivot, left points to the
         * first slot after them -- that's why this sort is stable.
         * Slide elements over to make room for pivot.
         */
        int n = start - left;  // The number of elements to move
        // Switch is just an optimization for arraycopy in default case
        switch (n) {
            case 2:  a[left + 2] = a[left + 1];
            case 1:  a[left + 1] = a[left];
                     break;
            default: System.arraycopy(a, left, a, left + 1, n);
        }
        a[left] = pivot;
    }
}
```
上面代码首先二分查找出插入点 `assert left == right`，插入点及其后元素后移，通过 ` a[left] = pivot`，将目标元素插入。可以看到，这里也有很多优化，比如计算需要后移的元素个数，如果是 1，则直接交换目标元素和插入点元素即可（目标元素本来在数组最后一格）。

### 3.合并 run
将 run 压入栈，执行合并，之后便是在循环中寻找下一个 run，入栈的时候会记录当前 run 的起点在整个序列的位置（所有 run 都在原数组里，不占用额外空间）以及 run 长度：
```
// Push run onto pending-run stack, and maybe merge
ts.pushRun(lo, runLen);
ts.mergeCollapse();

// Advance to find next run
lo += runLen;
nRemaining -= runLen;
```

我们来看一下具体合并流程，首先是合并的条件，我们要保证所有的 run 类似二叉树方式进行合并，防止出现非常大的 run 与非常小的 run 进行合并，每个 run 入栈时都会调动这个方法，假设栈顶位置为 i，那么我们要保证栈里的 run 符合以下条件 `stack[i-2].length > stack[i-1].length + stack[i].length`，并且 `stack[i-1].length > stack[i].length`。如果不符合，则需要合并。
```
private void mergeCollapse() {
    while (stackSize > 1) {
        int n = stackSize - 2;
        if (n > 0 && runLen[n-1] <= runLen[n] + runLen[n+1]) {
            if (runLen[n - 1] < runLen[n + 1])
                n--;
            mergeAt(n);
        } else if (runLen[n] <= runLen[n + 1]) {
            mergeAt(n);
        } else {
            break; // Invariant is established
        }
    }
}
```

因为我们的 run 都在原数组中，通过记录起点坐标和长度来划分，没有占用额外空间，所以我们合并的时候合并相邻两个 run，排序完成后，修改记录的起点坐标和长度来实现合并。在合并时也有优化，run1 和 run2 相邻，run1 在前，run2 在后。那么 run1 中比 run2 最小（第一个）元素小的那些元素其实相当于已经在正确的位置了，不需要考虑，同理 run2 中比 run1 最大的元素大的那些元素也是这样。举个例子：`[1,2,3,4][3,4,4,4,5,6] -> [1,2,[3,4][3,4,4],5,6] -> [1,2,3,3,4,4,4,5,6]`，数组 9 个连续位置，两个相邻 run，其中 `[1,2,-,-,-,-,-,5,6]` 相当于排好序了，只需要合并剩余的 `[3,4][3,4,4]`，代码如下：
```
/*
 * Find where the first element of run2 goes in run1. Prior elements
 * in run1 can be ignored (because they're already in place).
 */
int k = gallopRight(a[base2], a, base1, len1, 0, c);
assert k >= 0;
base1 += k;
len1 -= k;
if (len1 == 0)
    return;

/*
 * Find where the last element of run1 goes in run2. Subsequent elements
 * in run2 can be ignored (because they're already in place).
 */
len2 = gallopLeft(a[base1 + len1 - 1], a, base2, len2, len2 - 1, c);
assert len2 >= 0;
if (len2 == 0)
    return;

// Merge remaining runs, using tmp array with min(len1, len2) elements
if (len1 <= len2)
    mergeLo(base1, len1, base2, len2);
else
    mergeHi(base1, len1, base2, len2);
```

可以看到，即使合并剩余部分，依然通过判断两者长度来进行算法优化。


在循环结束后，会尝试最后的合并，确保栈里只剩一个 run，即排序好的整个序列。
```
// Merge all remaining runs to complete sort
assert lo == hi;
ts.mergeForceCollapse();
assert ts.stackSize == 1;
```


结论
我们只能确定低版本编译的代码可以运行在高版本的 Java，但却无法保证运行的行为和结果与低版本一致。