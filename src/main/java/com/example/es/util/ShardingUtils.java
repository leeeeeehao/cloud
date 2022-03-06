package com.example.es.util;

import org.apache.commons.lang.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.TreeSet;

public class ShardingUtils {

    public static TreeSet<String> getSuffixListForRange(String lowerSuffix, String upperSuffix) {
        TreeSet<String> suffixList = new TreeSet<>();

        if (lowerSuffix.equals(upperSuffix)) {
            //上下界在同一张表
            suffixList.add(lowerSuffix);
        } else {
            //上下界不在同一张表  计算间隔的所有表
            String tempSuffix = lowerSuffix;
            while (!tempSuffix.equals(upperSuffix)) {
                suffixList.add(tempSuffix);
                String[] ym = tempSuffix.split("_");
                Date tempDate = DateUtils.parse(ym[0] + (ym[1].length() == 1 ? "0" + ym[1] : ym[1]), "yyyyMM");
                Calendar cal = Calendar.getInstance();
                cal.setTime(tempDate);
                cal.add(Calendar.MONTH, 1);
                tempSuffix = ShardingUtils.getSuffixByYearMonth(cal.getTime());
            }
            suffixList.add(tempSuffix);
        }
        return suffixList;
    }

    public static HashSet<String> getSuffixListForRange1(String lowerSuffix, String upperSuffix) {
        HashSet<String> suffixList = new HashSet<>();
        if (lowerSuffix.equals(upperSuffix)) { //上下界在同一张表
            String[] split = upperSuffix.split("_");
            String year = split[0] ;
            String month = split[1] ;
            //按年月,分表  上半年-下半年
            StringBuffer tableSuffix = new StringBuffer();
            tableSuffix.append(year);
            tableSuffix.append("_");
            if(Integer.parseInt(month) > 6){
                tableSuffix.append(2);
            }else {
                tableSuffix.append(1);
            }
            suffixList.add(tableSuffix.toString());
        } else {  //上下界不在同一张表  计算间隔的所有表
            String tempSuffix = lowerSuffix;
            while (!tempSuffix.equals(upperSuffix)) {
                suffixList.add(tempSuffix);
                String[] ym = tempSuffix.split("_");
                Date tempDate = DateUtils.parse(ym[0] + (ym[1].length() == 1 ? "0" + ym[1] : ym[1]), "yyyyMM");
                Calendar cal = Calendar.getInstance();
                cal.setTime(tempDate);
                cal.add(Calendar.MONTH, 1);
                tempSuffix = ShardingUtils.getSuffixByYearMonth(cal.getTime());
            }
            String[] split = tempSuffix.split("_");
            String year = split[0] ;
            String month = split[1] ;
            //按年月,分表  上半年-下半年
            StringBuffer tableSuffix = new StringBuffer();
            tableSuffix.append(year);
            if(Integer.parseInt(month) > 6){
                tableSuffix.append(2);
                tableSuffix.append("_");
            }else {
                tableSuffix.append(1);
            }
            suffixList.add(tableSuffix.toString());
        }
        return suffixList;
    }

    public static String getSuffixByYearMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR) + "_" + (calendar.get(Calendar.MONTH) + 1);
    }

    public static String getPrevSuffix(String suffix) {
        if (StringUtils.isBlank(suffix)) {
            return getSuffixByYearMonth(new Date());
        }
        String[] arr = suffix.split("_");
        if ("1".equals(arr[1])) {
            return (Integer.valueOf(arr[0]) - 1) + "_12";
        } else {
            return arr[0] + "_" + (Integer.valueOf(arr[1]) - 1);
        }
    }

}
