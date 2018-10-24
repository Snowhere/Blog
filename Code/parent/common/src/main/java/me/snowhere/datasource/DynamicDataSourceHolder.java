package me.snowhere.datasource;

import java.util.Stack;

/***
 * @Description:ThreadLocal<Stack<DataSourceEnum>>
 * @author suntenghao
 * @date 2018-10-24 14:48
 */
public class DynamicDataSourceHolder {

    private static final ThreadLocal<Stack<DataSourceEnum>> holder = new ThreadLocal<>();

    public static void putDataSource(DataSourceEnum dataSource) {
        Stack<DataSourceEnum> dataSourceEnums = holder.get();
        if (null == dataSourceEnums) {
            dataSourceEnums = new Stack<>();
            holder.set(dataSourceEnums);
        }

        dataSourceEnums.push(dataSource);
    }

    public static DataSourceEnum getDataSource() {
        Stack<DataSourceEnum> dataSourceEnums = holder.get();
        if (null == dataSourceEnums || dataSourceEnums.isEmpty()) {
            return null;
        }
        return dataSourceEnums.peek();
    }

    public static void clearDataSource() {
        Stack<DataSourceEnum> dataSourceEnums = holder.get();
        if (null == dataSourceEnums || dataSourceEnums.isEmpty()) {
            return;
        }
        dataSourceEnums.pop();
    }
}