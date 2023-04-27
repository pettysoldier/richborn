package com.yami.shop.service;

import com.yami.shop.bean.wechat.WxPhoneInfoDto;
import com.yami.shop.security.common.R;
import com.yami.shop.security.common.wechat.CodeSessionDto;

/**
 * ---------\,,,/---------
 * ---------(o o)---------
 * -----oOOo-(3)-oOOo-----
 *
 * @Description:
 * @Author: zhushengguo
 * @Version:
 * @Date: 10:57 2023/3/27
 **/
public interface IWxService {
    R<CodeSessionDto> code2Session(String code);
    R<WxPhoneInfoDto> getWxPhoneInfo(String accessToken, String phCode);
}
