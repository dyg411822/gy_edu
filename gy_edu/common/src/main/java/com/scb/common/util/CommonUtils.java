package com.scb.common.util;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by
 */
@Deprecated
public class CommonUtils {

    private static final String PHONE_NUM_REG = "^1[0-9]{10}$";

    /**
     * 生成token
     *
     * @param systemCode
     * @return
     */
    public static String generateToken(String systemCode) {
        String random = RandomStringUtils.randomNumeric(2);
        return getSysRunMillis() + systemCode + random;//系统运行第几年 + 该年第几天 + 当天秒数     + 该秒毫秒数      +  系统code + 随机数

    }

    /**
     * 12~13位 + 6位 + 3位
     */
    public static String getSysRunMillis() {
        DateTime dateTime = DateTime.now();
        int runYear = dateTime.getYear() - 2015 + 1;
        String secondOfDay = zeroFill(dateTime.secondOfDay().getAsString(), 5);
        String dayOfYear = zeroFill(dateTime.getDayOfYear() + "", 3);
        String millisOfSecond = zeroFill(dateTime.millisOfSecond().getAsString(), 3);
        return runYear + dayOfYear + secondOfDay + millisOfSecond;
    }

    public static String zeroFill(String str, int length) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length - str.length(); i++) {
            buffer.append("0");
        }
        return buffer.toString() + str;
    }

    /**
     * 手机字符串验证
     *
     * @param phoneNum
     * @return
     */
    public static boolean isValidPhoneNum(String phoneNum) {
        return StringUtils.isNotEmpty(phoneNum) && Pattern.matches(PHONE_NUM_REG, phoneNum);
    }

    /**
     * 获取文本里面的图片路径List
     * 如xxxx<img src="http://192.168.2.79:8800/member/images/M100951585520050kwjq/M_GxonWbXyaF190517.png" style="max-width: 100%;">
     * 返回 http://192.168.2.79:8800/member/images/M100951585520050kwjq/M_GxonWbXyaF190517.png
     */
    public static List<String> getDetailImgsUrls(String detail) {
        List<String> imgList = new ArrayList<>();
        if (StringUtils.isEmpty(detail)) {
            return imgList;
        }
        StringBuffer descUrls = new StringBuffer();
        Pattern patternImg = Pattern.compile("<(img|IMG)(.*?)(/>|></img>|>)");//正则 获取整个图片
        Pattern patternSrc = Pattern.compile("(src|SRC)=(\"|\')(.*?)(\"|\')");//正则 获取图片里面的src
        Matcher matcherImg = patternImg.matcher(detail);
        while (matcherImg.find()) {
            String imgTag = matcherImg.group();
            Matcher matcherSrc = patternSrc.matcher(imgTag);
            while (matcherSrc.find()) {
                String src = matcherSrc.group();
                src = src.substring(5, src.length() - 1);
                imgList.add(src);
            }
        }
        if (StringUtils.isNotEmpty(descUrls.toString())) {
            descUrls.deleteCharAt(descUrls.length() - 1);
        }
        return imgList;
    }

    /**
     * 将旧文本与新文本比较，得出fastdfs上需要被删除的图片路径
     */
    public static List<String> getFdfsRemoveList(String oldDetail, String detail) {
        List<String> removeList = new ArrayList<>();
        List<String> oldImgs = getDetailImgsUrls(oldDetail);
        List<String> newImgs = getDetailImgsUrls(detail);
        if (null != newImgs && newImgs.size() > 0) {
            String newImgsString = String.join(",", newImgs);
            for (String oldImg : oldImgs) {
                if (!newImgsString.contains(oldImg)) {
                    removeList.add(oldImg);
                }
            }
        } else {
            removeList = oldImgs;
        }
        return removeList;
    }
}
