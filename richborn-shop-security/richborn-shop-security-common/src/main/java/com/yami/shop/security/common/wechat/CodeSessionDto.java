package com.yami.shop.security.common.wechat;

import lombok.Data;

/**
 * ---------\,,,/---------
 * ---------(o o)---------
 * -----oOOo-(3)-oOOo-----
 *
 * @Description:
 * @Author: zhushengguo
 * @Version:
 * @Date: 11:10 2023/3/27
 **/
@Data
public class CodeSessionDto {

    private String openid;
    private String session_key;
    private String unionid;
    private Integer errcode;
    private String errmsg;
}
