package com.imooc.controller;

import com.imooc.config.WechatAccountConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

    @GetMapping("/verify")
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

    @GetMapping("/userInfo")
    public void getUserInfo(String token,String openId) {
        log.info("进入verify 方法。。。");
        log.info("token={},openId={}",token,openId);
        String ACCESS_TOKEN = token;
        String OPENID = openId;
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+ACCESS_TOKEN+"&openid=" + OPENID+"&lang=zh_CN";

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        log.info("userInfo={}", response);
    }
}
