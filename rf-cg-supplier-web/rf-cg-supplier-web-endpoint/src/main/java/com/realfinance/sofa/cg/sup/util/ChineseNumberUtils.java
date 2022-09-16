package com.realfinance.sofa.cg.sup.util;

import cn.hutool.core.convert.NumberChineseFormatter;
import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class ChineseNumberUtils {

    private static final String[] SIMPLE_DIGITS = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    private static final String[] SIMPLE_DIGITS2 = {"〇", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    private static final String[] TRADITIONAL_DIGITS = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    private static final String[] SIMPLE_UNITS = {"", "十", "百", "千"};
    private static final String[] TRADITIONAL_UNITS = {"", "拾", "佰", "仟"};

    public static LocalDate parseLocalDate(String str) {
        Objects.requireNonNull(str);
        str = str.trim();
        if (str.isEmpty() || !str.contains("年") || !str.contains("月") || !str.contains("日")) {
            return null;
        }
        String year = StringUtils.substringBefore(str,"年").trim();
        String month = StringUtils.substringBetween(str,"年","月").trim();
        String day = StringUtils.substringBetween(str,"月","日").trim();
        for (int i = 0; i < SIMPLE_DIGITS2.length; i++) {
            String s = String.valueOf(i);
            year = year.replace(SIMPLE_DIGITS2[i],s);
            month = month.replace(SIMPLE_DIGITS2[i],s);
            day = day.replace(SIMPLE_DIGITS2[i],s);
        }
        try {
            return LocalDate.of(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day));
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(NumberChineseFormatter.format(100233200.21, true));
        String a = "二〇一五年04月11日";
        LocalDateTime yyyy年mm月dd日 = DateUtil.parseLocalDateTime(a, "yyyy年MM月dd日");
        System.out.println(yyyy年mm月dd日);
    }
}
