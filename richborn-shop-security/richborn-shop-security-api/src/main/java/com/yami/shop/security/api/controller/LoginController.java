package com.yami.shop.security.api.controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yami.shop.bean.R;
import com.yami.shop.bean.model.User;
import com.yami.shop.bean.wechat.CodeSessionDto;
import com.yami.shop.bean.wechat.WxPhoneInfoDto;
import com.yami.shop.common.util.PrincipalUtil;
import com.yami.shop.common.util.RedisUtil;
import com.yami.shop.dao.UserMapper;
import com.yami.shop.security.common.bo.UserInfoInTokenBO;
import com.yami.shop.security.common.dto.AuthenticationDTO;
import com.yami.shop.security.common.dto.WxAuthenticationDTO;
import com.yami.shop.security.common.enums.SysTypeEnum;
import com.yami.shop.security.common.manager.PasswordCheckManager;
import com.yami.shop.security.common.manager.PasswordManager;
import com.yami.shop.security.common.manager.TokenStore;
import com.yami.shop.security.common.vo.TokenInfoVO;
import com.yami.shop.service.IWxService;
import com.yami.shop.service.UserService;
import com.yami.shop.utils.JsonUtils;
import com.yami.shop.utils.RichBornStringUtils;
import com.yami.shop.utils.WechatUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;

/**
 * @author 菠萝凤梨
 * @date 2022/3/28 15:20
 */
@RestController
@Api(tags = "登录")
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private TokenStore tokenStore;

    @Resource
    private UserMapper userMapper;

    @Autowired
    private PasswordCheckManager passwordCheckManager;

    @Autowired
    private PasswordManager passwordManager;
    @Resource
    private UserService userService;
    @Resource
    IWxService wxService;


    @PostMapping("/login")
    @ApiOperation(value = "账号密码(用于前端登录)", notes = "通过账号/手机号/用户名密码登录，还要携带用户的类型，也就是用户所在的系统")
    public ResponseEntity<TokenInfoVO> login(
            @Valid @RequestBody AuthenticationDTO authenticationDTO) {
        String mobileOrUserName = authenticationDTO.getUserName();
        User user = getUser(mobileOrUserName);

        String decryptPassword = passwordManager.decryptPassword(authenticationDTO.getPassWord());

        // 半小时内密码输入错误十次，已限制登录30分钟
        passwordCheckManager.checkPassword(SysTypeEnum.ORDINARY, authenticationDTO.getUserName(), decryptPassword, user.getLoginPassword());

        UserInfoInTokenBO userInfoInToken = new UserInfoInTokenBO();
        userInfoInToken.setUserId(user.getUserId());
        userInfoInToken.setSysType(SysTypeEnum.ORDINARY.value());
        userInfoInToken.setEnabled(user.getStatus() == 1);
        // 存储token返回vo
        TokenInfoVO tokenInfoVO = tokenStore.storeAndGetVo(userInfoInToken);
        return ResponseEntity.ok(tokenInfoVO);
    }

    @PostMapping("/wxlogin")
    @ApiOperation(value = "微信登录", notes = "微信登录")
    public R<TokenInfoVO> wxLogin(
            @Valid @RequestBody WxAuthenticationDTO authenticationDTO
    ) {
        //获取微信accessToken
        String accessToken = RedisUtil.get("access:token");
        if (StringUtils.isEmpty(accessToken)) {
            JSONObject jsonObject = WechatUtil.getAccessToken(authenticationDTO.getPhonenumbecode());
            log.info("微信token返回：" + JsonUtils.toString(jsonObject));
            accessToken = jsonObject.getString("access_token");
            Integer expireTime = jsonObject.getInteger("expires_in");
            RedisUtil.set("access:token", accessToken, expireTime - 10);
        }
        R<WxPhoneInfoDto> wxPhoneInfo = wxService.getWxPhoneInfo(accessToken, authenticationDTO.getPhonenumbecode());
        User user = getUser(wxPhoneInfo.getResult().getPhoneInfo().getPurePhoneNumber());
        R<CodeSessionDto> r = wxService.code2Session(authenticationDTO.getPrincipal());
        UserInfoInTokenBO userInfoInToken = new UserInfoInTokenBO();
        if (user == null) {
            User userNew = new User();
            userNew.setModifyTime(new Date());
            userNew.setUserRegtime(new Date());
            userNew.setStatus(1);
            userNew.setUserMobile(wxPhoneInfo.getResult().getPhoneInfo().getPurePhoneNumber());
            userNew.setNickName(RichBornStringUtils.hideSubString(wxPhoneInfo.getResult().getPhoneInfo().getPurePhoneNumber(), 3, 4, '*'));
            String userId = IdUtil.simpleUUID();
            userNew.setUserId(userId);
            userService.save(userNew);
            user = userNew;
        }
        userInfoInToken.setUserId(user.getUserId());
        userInfoInToken.setSysType(SysTypeEnum.ORDINARY.value());
        userInfoInToken.setEnabled(user.getStatus() == 1);
        userInfoInToken.setOpenId(r.getResult().getOpenid());
        userInfoInToken.setSessionKey(r.getResult().getSession_key());
        userInfoInToken.setUnionId(r.getResult().getUnionid());
        userInfoInToken.setNickName(user.getNickName());

        // 存储token返回vo
        TokenInfoVO tokenInfoVO = tokenStore.storeAndGetVo(userInfoInToken);
        return R.ok(tokenInfoVO);
    }


    private User findUser(String openId) {
        User user = userMapper.selectOneByOpenId(openId);
        return user;
    }

    private User getUser(String mobileOrUserName) {
        User user = null;
        // 手机验证码登陆，或传过来的账号很像手机号
        if (PrincipalUtil.isMobile(mobileOrUserName)) {
            user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserMobile, mobileOrUserName));
        }
        // 如果不是手机验证码登陆， 找不到手机号就找用户名
        if (user == null) {
            user = userMapper.selectOneByUserName(mobileOrUserName);
        }
        if (user == null) {
            return null;
        }
        return user;
    }
}
