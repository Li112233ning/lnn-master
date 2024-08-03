package com.lnn.util;

import cn.hutool.core.date.DateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class DateUtils {
    // 减去指定分钟数
    public static Date subMin(int min) {
        Calendar cal = Calendar.getInstance(); // 获取日历实例
        cal.setTime(new Date()); // 设置当前时间
        cal.add(Calendar.MINUTE, -min); // 减去分钟数
        Date nowTime = cal.getTime(); // 获取新的时间
        return nowTime; // 返回新的时间
    }

    // 加上指定分钟数
    public static Date addMin(int min) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, min); // 加上分钟数
        Date nowTime = cal.getTime();
        return nowTime;
    }

    // 减去指定天数
    public static Date subDay(int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, -day); // 减去天数
        Date nowTime = cal.getTime();
        return nowTime;
    }

    // 加上指定天数
    public static Date addDay(int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, day); // 加上天数
        Date nowTime = cal.getTime();
        return nowTime;
    }

    // 获取当前日期的零点时间
    public static Date getCurrentZero() {
        Calendar c = Calendar.getInstance(); // 获取日历实例
        c.set(Calendar.HOUR_OF_DAY, 0); // 设置小时为 0
        c.set(Calendar.MINUTE, 0); // 设置分钟为 0
        c.set(Calendar.SECOND, 0); // 设置秒为 0
        c.set(Calendar.MILLISECOND, 0); // 设置毫秒为 0
        return c.getTime(); // 返回当前日期的零点时间
    }

    /**
     * 往前推指定天数到零点
     * @param day 天数
     * @return 指定天数前的零点时间
     */
    public static Date getCurrentZero(int day) {
        Calendar c = Calendar.getInstance(); // 获取日历实例
        c.set(Calendar.HOUR_OF_DAY, 0); // 设置小时为 0
        c.set(Calendar.MINUTE, 0); // 设置分钟为 0
        c.set(Calendar.SECOND, 0); // 设置秒为 0
        c.set(Calendar.MILLISECOND, 0); // 设置毫秒为 0
        c.add(Calendar.DAY_OF_MONTH, -day); // 减去指定天数
        return c.getTime(); // 返回新的零点时间
    }

    // 获取指定日期的凌晨到下个凌晨的时间范围
    public static Date[] getBeginEnd(int day) {
        Date begin = getCurrentZero(day); // 获取指定天数前的零点时间

        // 创建一个数组，包含当前零点和前一天的零点
        Date[] dates = {getCurrentZero(day), getCurrentZero(day - 1)};

        return dates; // 返回时间范围
    }

    public static void main(String[] args) {
        // 打印指定天数的开始和结束时间
        System.out.println(getBeginEnd(1)[0]); // 打印指定天数的零点时间
        System.out.println(getBeginEnd(1)[1]); // 打印前一天的零点时间
        System.out.println(new Random().nextInt(10)); // 打印一个 0 到 9 的随机数
    }

    // 格式化日期为指定格式的字符串
    public static String format(Date date) {
        return DateUtil.format(date, "yyyy-MM-dd HH:mm:ss"); // 使用 Hutool 格式化日期
    }
}
