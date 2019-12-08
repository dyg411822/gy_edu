package com.scb.common.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;

import java.rmi.ServerException;
import java.util.Map;

/**
 * 短信发送工具类
 */
@Slf4j
public class SmsUtil {

    // accessKeyId
    private static final String ACCESS_KEY_ID = "LTAIBDgP8KVx7qHm";
    // accessKeySecret
    private static final String ACCESS_KEY_SECRET = "sY5seNTYEqhMtkJq7wNcesoV9yhsWB";
    // 服务地址
    private static final String AL_PATH = "dysmsapi.aliyuncs.com";
    // 版本信息，（已经固定，不能进行更改）
    private static final String SMS_VERSION = "2017-05-25";



    /**
     * 短信发送接口信息  支持批量发送 ps--目前签名信息仅设置一个
     *
     * @param signName      签名
     * @param phone         需要发送的电话号码，支持多个电话号码 格式为"13600000000,15000000000"
     * @param templateCode  明确需要使用哪个模板，可以从阿里云控制台查看
     * @param templateParam 模板内需要填充的字段及字段值 格式为("{\"name\":\"Tom\", \"code\":\"123\"}")
     * @Return true 代表发送成功  false 代表发送失败
     */
    public static boolean sendMsg(String signName, String phone, String templateCode, String templateParam) {
        boolean bool = false;
        DefaultProfile profile = DefaultProfile.getProfile("default", ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(AL_PATH);
        request.setVersion(SMS_VERSION);
        // 必填:待发送手机号
        request.putQueryParameter("PhoneNumbers", phone);
        // 阿里云控制台签名
        request.putQueryParameter("SignName", signName);
        // 阿里云控制台模板编号
        request.putQueryParameter("TemplateCode", templateCode);
        //系统规定参数
        request.setAction("SendSms");
        // 模板内需要填充参数信息
        request.putQueryParameter("TemplateParam", templateParam);
        try {
            CommonResponse response = client.getCommonResponse(request);
            Map<String, Object> map = JacksonUtil.json2Map(response.getData());
            if ("OK".equals(map.get("Code"))) {
                bool = true;
            }
        } catch (Exception e) {
            log.error("阿里云短信服务异常:{}", e.getLocalizedMessage(), e);
        }
        return bool;
    }


}
