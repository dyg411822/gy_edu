package com.scb.common.util;

import cn.hutool.core.util.IdUtil;

/**
 * <主键生成器>
 * @author Administrator
 *
 */
public class PrimaryKeyUtil {

    /**
     * 获得一个 UUID（不带横线）
     * @return String UUID
     */
    public static String getUuid() {
        return IdUtil.simpleUUID();
    }

    /**
     * 按照手机获得会员ID
     * @return String UUID
     */
    @Deprecated
    public static String getMemberId(String mobile) {
        String str_result = "";
        try {
            long l = Long.parseLong(mobile);
            str_result = "M" + Long.toString(l * 5 - 1000000) + RandomUtil.getStrRandom(4, RandomUtil.LETTER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return str_result;
    }

    /**
     * 获得会员ID
     * @return String
     */
    @Deprecated
    public static String getMemberId() {
        String str_result = "";
        try {
            String str_date = JodaTimeUtils.getStrCurrentDate(JodaTimeUtils.DF_SHORT);
            long l = Long.parseLong(str_date);
            str_result = "M" + Long.toString(l * 5 - 1000000000) + RandomUtil.getStrRandom(4, RandomUtil.SMALLLELETTERNUMBER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return str_result;
    }

    /**
     * 获得课程ID
     * @return String UUID
     */
    @Deprecated
    public static String getCourseId() {
        String str_result = "";
        try {
            String str_date = JodaTimeUtils.getStrCurrentDate(JodaTimeUtils.DF_SHORT_YMD);
            long l = Long.parseLong(str_date);
            str_result = "C" + Long.toString(l * 3 - 10000) + RandomUtil.getStrRandom(5, RandomUtil.BIGELETTERNUMBER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return str_result;
    }

    /**
     * 获得贷款ID或者合同ID
     * @return String UUID
     */
    @Deprecated
    public static String getbusinessID(String flag) {
        String str_result = "";
        try {
            String str_date = JodaTimeUtils.getStrCurrentDate(JodaTimeUtils.DF_YMD);
            str_result = flag + str_date + RandomUtil.getStrRandom(6, RandomUtil.LETTERNUMBER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return str_result;
    }

    /**
     * @param args
     */
//    public static void main(String[] args) {
//        System.out.println("getUUID    = " + getUUID());
//        System.out.println("getMemberID= " + getMemberID("18999999999"));
//        System.out.println("getCourseID=" + getCourseID());
//        System.out.println("getbusinessID=" + getbusinessID("C"));
//    }
}
