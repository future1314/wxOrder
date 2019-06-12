package com.imooc.controller;

import com.imooc.config.WechatAccountConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by 廖师兄
 * 2017-07-03 00:50
 */
@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinController {

    @Autowired
    WechatAccountConfig accountConfig;

    @GetMapping("/auth")
    public void auth(@RequestParam(value = "code") String code) {
        log.info("进入auth方法。。。");
        log.info("code={}", code);
        String appId  = accountConfig.getMpAppId();
        String secret = accountConfig.getMpAppSecret();
        //String code = accountConfig.getMpAppSecret();
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId+"&secret="+secret+"&code=" + code + "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        log.info("response={}", response);
    }

    @GetMapping("/verify")//XXX
    public void verifyAuth(String token,String openId) {
        log.info("进入verify 方法。。。");
        log.info("token={},openId={}",token,openId);
        String ACCESS_TOKEN = token;
        String OPENID = openId;
        String url = "https://api.weixin.qq.com/sns/auth?access_token="+ACCESS_TOKEN+"&openid=" + OPENID;

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        log.info("verifyAuth={}", response);
    }

    @GetMapping("/userInfo2")//XXX
    public void getUserInfo2(String token,String openId) {
        log.info("进入verify 方法。。。");
        log.info("token={},openId={}",token,openId);
        String ACCESS_TOKEN = token;
        String OPENID = openId;
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+ACCESS_TOKEN+"&openid=" + OPENID+"&lang=zh_CN";

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        log.info("userInfo={}", response);
    }

    @GetMapping("/userInfo")//XXX
    @ResponseBody
    public String getUserInfo(String code,String state) {
        log.info("进入getUserInfo 方法。。。");
        log.info("code={},state={}",code,state);
        //String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+ACCESS_TOKEN+"&openid=" + OPENID+"&lang=zh_CN";
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+accountConfig.getMpAppId()+"&secret="+accountConfig.getMpAppSecret()+"&js_code="+code+"&grant_type=authorization_code";

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        log.info("xcx.userInfo={}", response);
        return response;
    }

    /**
     * 微信支付后台通知
     *
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("wxpay")
    public String wxpay(HttpServletRequest request) throws Exception {
        InputStream inStream = null;
        ByteArrayOutputStream outSteam = null;
    //    try {
            inStream = request.getInputStream();
            outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            String result = new String(outSteam.toByteArray(), "utf-8");
            log.info("微信支付后台通知内容:{}", result);
      //      WXPay wxpay = new WXPay(wxPayConfig);

            // 转换成map
      /*      Map<String, String> notifyMap = WXPayUtil.xmlToMap(result);
            if (wxpay.isResponseSignatureValid(notifyMap)) {
                log.info("验签通过");

                String serialNo = notifyMap.get("out_trade_no");
                Command command = commandService.findCommandBySerialNo(serialNo);
                if (command == null) {
                    log.info("异步通知流水号不存在:{}", serialNo);
                    return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[ERR]]></return_msg></xml>";
                }

                if (WXPayConstants.SUCCESS.equals(notifyMap.get("result_code"))) {
                    log.info("交易成功，状态更新为待发货");

                    // 更新交易状态
                    command.setTranSt(TranSt.P.getCode());
                    commandService.updateTranSt(command);

                    // 通知商户系统发货
                    if(paymentHelper.notify(command)) {
                        // 响应落库, 更新交易状态
                        wxPayTransactionService.processNotice(notifyMap, command);
                    }
                }*/
    /*        } else {
                log.info("验签失败");
                // 签名错误，如果数据里没有sign字段，也认为是签名错误
                return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[ERR]]></return_msg></xml>";
            }
        } catch (Exception e) {
            log.error("微信支付后台通知接收异常", e);
        } finally {
            if (outSteam != null) {
                outSteam.close();
            }
            if (inStream != null) {
                inStream.close();
            }
        }
    */
        return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }
}
