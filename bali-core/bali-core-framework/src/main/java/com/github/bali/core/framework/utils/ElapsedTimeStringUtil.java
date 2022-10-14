package com.github.bali.core.framework.utils;

/**
 * 用于计算经过的时间并显示的工具类
 * <p>
 * 类似于社交工具显示的"3分钟"，"7天"
 *
 * @author Pettyfer
 */
public class ElapsedTimeStringUtil {

    private static final String[] INTERVAL_LABELS = new String[]{"秒", "分钟", "小时", "天", "月", "年"};

    public static String elapsed(long timestampSecond) {

        long elapsedSecond = Math.abs((System.currentTimeMillis() / 1000) - timestampSecond);

        long[] intervals = new long[]{
            elapsedSecond,
            (elapsedSecond - (elapsedSecond % 60)) / 60,
            (elapsedSecond - (elapsedSecond % 3600)) / 3600,
            (elapsedSecond - (elapsedSecond % (3600 * 24))) / (3600 * 24),
            (elapsedSecond - (elapsedSecond % (3600 * 24 * 30))) / (3600 * 24 * 30),
            (elapsedSecond - (elapsedSecond % (3600 * 24 * 30 * 12))) / (3600 * 24 * 30 * 12)
        };

        for (int i = 5; i >= 0; i--) {
            long interval = intervals[i];
            if (interval > 0) {
                return interval + INTERVAL_LABELS[i];
            }
        }

        return "";
    }
}
