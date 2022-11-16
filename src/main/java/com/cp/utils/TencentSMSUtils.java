package com.cp.utils;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.github.qcloudsms.httpclient.HTTPMethod;
import com.github.qcloudsms.httpclient.HTTPRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 短信发送工具类
 */
public class TencentSMSUtils {
    public static final Integer SDK_APP_ID = 1400763346;//SDK-ID
    public static final String Secret_Key = "c6c611a518ffcb2ae46d9cad98f2af4d";//App-Id
    public static final Integer VALIDATE_CODE = 1604603;//发送短信验证码（模板）
//    public static final Integer ORDER_NOTICE = 1431492;//体检预约成功通知（模板）
//    public static final Integer LOGIN_VALIDATE_CODE = 1432383;//登录短信验证码（模板）
    public static final String CHINA_NATION_CODE = "86";//国家编码
    public static final String SMS_SIGN = "羊村恶霸浩浩";// 签名内容：公众号名称


    /**
     * @param templateCode 模板号
     * @param phoneNumbers 手机号
     * @param list         参数
     * @param nationCode   国家代码 +86
     */
    public static void sendShortMessage(Integer templateCode, String phoneNumbers, List<String> list, String nationCode) throws Exception {
        SmsSingleSender smsSingleSender = new SmsSingleSender(SDK_APP_ID, Secret_Key);
        smsSingleSender.sendWithParam(nationCode, phoneNumbers, templateCode, (ArrayList<String>) list, SMS_SIGN, "", "");
    }

    public static void sendShortMessage(Integer templateCode, String phoneNumbers, String message, String nationCode) throws Exception {
        SmsSingleSender smsSingleSender = new SmsSingleSender(SDK_APP_ID, Secret_Key);
        smsSingleSender.sendWithParam(nationCode, phoneNumbers, templateCode, new String[]{message}, SMS_SIGN, "", "");
    }
}
