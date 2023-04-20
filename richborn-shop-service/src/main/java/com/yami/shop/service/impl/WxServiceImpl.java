package com.yami.shop.service.impl;


import com.yami.shop.security.common.R;
import com.yami.shop.security.common.wechat.CodeSessionDto;
import com.yami.shop.service.IWxService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
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
    @Resource
    RestTemplate restTemplate;

    /**
     * 微信登录凭证校验接口
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
        ResponseEntity<Map> result = restTemplate.getForEntity(builder.build().toUri().toString(), Map.class);
        CodeSessionDto   codeSessionDto = new CodeSessionDto();
        if (result.getStatusCodeValue() != 200 || Integer.parseInt(result.getBody().get("errcode").toString()) != 0) {
            return R.fail("微信服务器错误 返回码：" + result.getStatusCodeValue() + " 接口错误码：" + result.getBody().get("errcode"));
        }else {
            codeSessionDto.setSession_key(result.getBody().get("session_key").toString());
            codeSessionDto.setOpenid(result.getBody().get("openid").toString());
            codeSessionDto.setUnionid(result.getBody().get("unionid").toString());
            codeSessionDto.setErrcode((Integer)result.getBody().get("errcode"));
            codeSessionDto.setErrmsg(result.getBody().get("errmsg").toString());
        }
        return  R.ok(codeSessionDto);
    }
}
