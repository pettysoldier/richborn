package com.yami.shop.utils;

import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.cert.CertificatesManager;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

/**
 * @program: qingsong-api
 * @description:
 * @author: zhanglei
 * @create: 2023-03-30 10:08
 **/
@Service
@Slf4j
public class WxPayHttpClientFactory {

    public static CloseableHttpClient httpClient;
    public static Verifier verifier;


//    @PostConstruct
    public void initWXPayClient() {
        try {
            // 加载商户私钥（privateKey：私钥字符串）
            PrivateKey merchantPrivateKey = PemUtil
                    .loadPrivateKey(new ClassPathResource("apiclient_key.pem").getInputStream());

            X509Certificate certificate = PemUtil.loadCertificate(new ClassPathResource("apiclient_cert.pem").getInputStream());
            String serialNo = certificate.getSerialNumber().toString(16).toUpperCase();
            //merchantId:商户号,serialNo:商户证书序列号
            // 获取证书管理器实例
            CertificatesManager certificatesManager = CertificatesManager.getInstance();
            // 向证书管理器增加需要自动更新平台证书的商户信息
            certificatesManager.putMerchant(WechatUtil.MCH_ID, new WechatPay2Credentials(WechatUtil.MCH_ID,
                    new PrivateKeySigner(serialNo, merchantPrivateKey)), WechatUtil.SYMMETRY_KEY.getBytes(StandardCharsets.UTF_8));
            // 从证书管理器中获取verifier
            //版本>=0.4.0可使用 CertificatesManager.getVerifier(mchId) 得到的验签器替代默认的验签器。
            // 它会定时下载和更新商户对应的微信支付平台证书 （默认下载间隔为UPDATE_INTERVAL_MINUTE）。
            verifier = certificatesManager.getVerifier(WechatUtil.MCH_ID);

            //创建一个httpClient
            httpClient = WechatPayHttpClientBuilder.create()
                    .withMerchant(WechatUtil.MCH_ID, serialNo, merchantPrivateKey)
                    .withValidator(new WechatPay2Validator(verifier)).build();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("加载秘钥文件失败");
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            log.error("获取平台证书失败");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeWXClient() {
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
