package com.yami.shop.security.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用于微信登陆传递授权码
 *
 * @author ZSG
 * @date 2023/5/4 17:33
 */
@Data
public class WxAuthenticationDTO {

    /**
     * 用户名
     */
    @NotBlank(message = "微信授权码不能为空")
    @ApiModelProperty(value = "微信授权码", required = true)
    protected String principal;

    /**
     * 密码
     */
    @NotBlank(message = "手机号授权码不能为空")
    @ApiModelProperty(value = "手机号授权码", required = true)
    protected String phonenumbecode;

}
