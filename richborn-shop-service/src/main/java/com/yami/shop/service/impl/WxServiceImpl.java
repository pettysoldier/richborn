package com.yami.shop.service.impl;


import com.yami.shop.bean.R;
import com.yami.shop.bean.wechat.CodeSessionDto;
import com.yami.shop.bean.wechat.WxPhoneInfoDto;
import com.yami.shop.service.IWxService;
import com.yami.shop.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * ---------\,,,/---------
 * ---------(o o)---------
 * -----oOOo-(3)-oOOo-----
 *
 * @Description:
 * @Author: zhushengguo
 * @Version:
 * @Date: 10:58 2023/3/27
 **/
@Service
public class WxServiceImpl implements IWxService {

    private static final Logger log = LoggerFactory.getLogger(WxServiceImpl.class);
    @Resource
    RestTemplate restTemplate;

    /**
     * 微信登录凭证校验接口
     *
     * @param code 小程序端调用登录获取的临时code，用户换取微信登录凭证
     * @return
     */
    public R<CodeSessionDto> code2Session(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("appid", "wx381f16375560d587")
                .queryParam("secret", "36ad7d3e931872f08c730f88569501dd")
                .queryParam("js_code", code)
                .queryParam("grant_type", "authorization_code");
        ResponseEntity<CodeSessionDto> result = restTemplate.getForEntity(builder.build().toUri().toString(), CodeSessionDto.class);
        log.info("微信登录凭证校验接口: "+ JsonUtils.toString(result));
        if (result.getStatusCodeValue() != 200) {
            return R.fail("微信服务器错误 返回码：" + result.getStatusCodeValue() + " 接口错误码：" + result.getBody().getErrcode());
        } else {
            CodeSessionDto codeSessionDto = result.getBody();
            return R.ok(codeSessionDto);
        }
    }

    public R<WxPhoneInfoDto> getWxPhoneInfo(String accessToken, String phCode) {
        String url = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=" + accessToken;
        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> paramMap = new HashMap<>(0);
        paramMap.put("code", phCode);
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            multiValueMap.put(entry.getKey(), Arrays.asList(entry.getValue()));
        }
//        HttpEntity<String> request = new HttpEntity(multiValueMap, headers);

        HttpEntity<String> request = new HttpEntity<>(JsonUtils.toString(paramMap), headers);
        try {
            ResponseEntity<WxPhoneInfoDto> result = restTemplate.postForEntity(url, request, WxPhoneInfoDto.class);
            if (result.getStatusCodeValue() != 200 || result.getBody().getErrcode() != 0) {
                log.error("获取手机号出错：" + JsonUtils.toString(result));
                return R.fail(-1, "获取手机号出错");
            }

            return R.ok(JsonUtils.toObject(JsonUtils.toString(result.getBody()), WxPhoneInfoDto.class));
        } catch (Exception e) {
            log.error("获取手机号异常:", e);
            return R.fail(-1, "获取手机号出错");
        }
    }
}
