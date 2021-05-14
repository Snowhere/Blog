package util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * 常用计算方法
 *
 * @author suntenghao
 * @date 2021-01-19
 */
public class MathUtil {

    private final static BigDecimal HUNDRED = new BigDecimal(100);
    private final static BigDecimal ZERO = new BigDecimal(0);

    /**
     * 假设有如下元素(省略了元素的 get 和 set 以及构造方法)
     * <pre>{@code
     * class Item {
     *     private int value;
     *     private BigDecimal percent;
     * }
     * }</pre>
     * 使用方法如下
     * <pre>{@code
     * List<Item> itemList = Arrays.asList(
     *     new Item(2, null),
     *     new Item(2, null),
     *     new Item(2, null),
     *     new Item(2, null));
     * MathUtil.percent(itemList, Item::getValue, Item::setPercent, 1);
     * }</pre>
     * 此时 itemList 的数据:
     * [Item(value=2, percent=25.0), Item(value=2, percent=25.0), Item(value=2, percent=25.0), Item(value=2, percent=25.0)]
     * @see <a href="https://confluence.zhenguanyu.com/pages/viewpage.action?pageId=123705306">算法详解</a>
     *
     * @param list             元素列表
     * @param getValueMethod   取元素中需要计算的值方法
     * @param setPercentMethod 为元素设置百分比方法
     * @param point            保留到小数点后几位
     * @return void
     */
    public static <T, N extends Number> void percent(Collection<T> list, Function<T, N> getValueMethod, BiConsumer<T, BigDecimal> setPercentMethod, int point) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (point < 0) {
            point = 0;
        }
        //最小刻度
        BigDecimal per = new BigDecimal(1);
        for (int i = 0; i < point; i++) {
            per = per.divide(new BigDecimal(10), point, BigDecimal.ROUND_UNNECESSARY);
        }
        //总值
        final BigDecimal total = list.stream().map(getValueMethod).filter(Objects::nonNull).map(t -> new BigDecimal(t.toString())).reduce(BigDecimal::add).orElse(ZERO);

        //有序 map, key 为需要计算的值，根据余数排序，value 为元素列表
        final BigDecimal temp = new BigDecimal(10).pow(point + 2);
        TreeMap<BigDecimal, List<T>> map = new TreeMap<>(Comparator.comparing(o -> o.multiply(temp).divideAndRemainder(total)[1]));
        for (T t : list) {
            N number = getValueMethod.apply(t);
            if (number == null || total.equals(ZERO)) {
                map.computeIfAbsent(ZERO, k -> new ArrayList<>()).add(t);
            } else {
                map.computeIfAbsent(new BigDecimal(number.toString()), k -> new ArrayList<>()).add(t);
            }
        }
        //占比之和
        BigDecimal sum = new BigDecimal(0);
        for (Entry<BigDecimal, List<T>> entry : map.entrySet()) {
            BigDecimal key = entry.getKey();
            BigDecimal result = key.multiply(HUNDRED).divide(total, point, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(entry.getValue().size()));
            sum = sum.add(result);
        }

        // 需要（100-占比之和)/最小刻度 个元素补刻度，并最终设值
        int lack = HUNDRED.subtract(sum).divide(per, 0, BigDecimal.ROUND_HALF_UP).intValue();
        for (BigDecimal key : map.descendingKeySet()) {
            for (T t : map.get(key)) {
                BigDecimal percent = key.multiply(HUNDRED).divide(total, point, BigDecimal.ROUND_DOWN);
                if (lack > 0) {
                    setPercentMethod.accept(t, percent.add(per));
                    lack--;
                } else {
                    setPercentMethod.accept(t, percent);
                }
            }
        }
    }

    /**
     * 假设有如下元素(省略了元素的 get 和 set 以及构造方法)
     * <pre>{@code
     * class Item {
     *     private int id;
     *     private int value;
     * }
     * }</pre>
     * 使用方法如下
     * <pre>{@code
     * List<Item> itemList = Arrays.asList(
     *     new Item(1, 2),
     *     new Item(2, 2),
     *     new Item(3, 2),
     *     new Item(4, 2));
     * Map<Integer, BigDecimal> percentMap = MathUtil.percent(itemList, Item::getValue, Item::getId, 1);
     * }</pre>
     * 返回 percentMap 数据:
     * {1=25.0, 2=25.0, 3=25.0, 4=25.0}
     * @see <a href="https://confluence.zhenguanyu.com/pages/viewpage.action?pageId=123705306">算法详解</a>
     *
     * @param list           元素列表
     * @param getValueMethod 取元素中需要计算的值方法
     * @param getKeyMethod   获取元素 key 的方法
     * @param point          保留到小数点后几位
     * @return Map&lt;K, BigDecimal&gt; 根据 key 和占比构建的 map
     * @author suntenghao
     * @date 2021-01-19
     */
    public static <T, K, N extends Number> Map<K, BigDecimal> percent(Collection<T> list, Function<T, N> getValueMethod, Function<T, K> getKeyMethod, int point) {
        Map<K, BigDecimal> map = new HashMap<>();
        percent(list, getValueMethod, (t, r) -> map.put(getKeyMethod.apply(t), r), point);
        return map;
    }

    /**
     * 使用方法如下
     * <pre>{@code
     * List<BigDecimal> percentList = MathUtil.percent(Arrays.asList(1.2, 2, 3, 4), 1);
     * }</pre>
     * 返回 percentList 数据:
     * [11.8, 39.2, 29.4, 19.6]
     * @see <a href="https://confluence.zhenguanyu.com/pages/viewpage.action?pageId=123705306">算法详解</a>
     *
     * @param list  值列表
     * @param point 保留到小数点后几位
     * @return List&lt;BigDecimal&gt; 百分比列表（不保证顺序性）
     * @author suntenghao
     * @date 2021-02-03
     */
    public static <N extends Number> List<BigDecimal> percent(Collection<N> list, int point) {
        List<BigDecimal> result = new ArrayList<>();
        percent(list, o -> o, (t, r) -> result.add(r), point);
        return result;
    }

    @Data
    @AllArgsConstructor
    static class Item {
        private int id;
        private int value;
        private BigDecimal percent;
    }

    public static void main(String[] args) {
        List<Item> itemList = Arrays.asList(
                new Item(2, 2, null),
                new Item(3, 2, null),
                new Item(4, 2, null));
        //方式一：直接回填
        MathUtil.percent(itemList, Item::getValue, Item::setPercent, 1);
        System.out.println(itemList);
        //方式二：生成 map
        Map<Integer, BigDecimal> percentMap = MathUtil.percent(itemList, Item::getValue, Item::getId, 1);
        System.out.println(percentMap);
        //方式三：list
        List<BigDecimal> percentList = MathUtil.percent(Arrays.asList(1.2, 2, 3, 4, 0, null), 1);
        System.out.println(percentList);
    }
}