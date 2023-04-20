package com.yami.shop.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import okhttp3.HttpUrl;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.core.io.ClassPathResource;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author zhanglei
 * @date 2019-09-11 16:49
 */
public class WechatUtil {

    //获取access_token
    public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?";
    //推送url
    public final static String PUSH_URL = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=";

    public final static String APP_ID = "wxf656758734199544";//wx26d8b2d516c989d1

    public final static String SECRET ="f61299837b32af06da9d33008d076349";//"1bbfceccf9c8d679d140364f482c029e";

    public final static String MCH_ID = "1597508881";

    public final static String SYMMETRY_KEY ="HYtaibosaqiang820709taibosaqiang";

    public final static String APP_CREATE_ORDER ="https://api.mch.weixin.qq.com/v3/pay/transactions/app";

    public final static String weixinMerchantSerialNumber ="19A662787A1A8C37D59B40759483145F19980027";

    /**
     * 获得openid
     *
     * @param code
     * @return
     */
    public static JSONObject getSessionKeyOrOpenId(String code) {
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, String> requestUrlParam = new HashMap<>();
        // https://mp.weixin.qq.com/wxopen/devprofile?action=get_profile&token=164113089&lang=zh_CN
        //小程序appId
        requestUrlParam.put("appid", APP_ID);
        //小程序secret
        requestUrlParam.put("secret", SECRET);
        //小程序端返回的code
        requestUrlParam.put("js_code", code);
        //默认参数
        requestUrlParam.put("grant_type", "authorization_code");
        //发送post请求读取调用微信接口获取openid用户唯一标识
        JSONObject jsonObject = JSON.parseObject(HttpClientUtil.doPost(requestUrl, requestUrlParam));
        return jsonObject;
    }


    /**
     * 获得accessToken
     *
     * @return
     */
    public static JSONObject getAccessToken(String code) {

        String url = ACCESS_TOKEN_URL + "appid=" + APP_ID + "&secret=" + SECRET + "&code=" + code + "&grant_type=authorization_code";
        PrintWriter out = null;
        BufferedReader in = null;
        String line;
        StringBuffer sb = new StringBuffer();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();

            // 设置通用的请求属性 设置请求格式
            //设置返回类型
            conn.setRequestProperty("contentType", "text/plain");
            //设置请求类型
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            //设置超时时间
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setDoOutput(true);
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应    设置接收格式
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            // 将获得的String对象转为JSON格式
            JSONObject jsonObject = JSONObject.parseObject(sb.toString());
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取用户个人信息
     *
     * @return
     */
    public static JSONObject getUserInfo(String accessToken, String openId) {

        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId;
        PrintWriter out = null;
        BufferedReader in = null;
        String line;
        StringBuffer sb = new StringBuffer();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();

            // 设置通用的请求属性 设置请求格式
            //设置返回类型
            conn.setRequestProperty("contentType", "text/plain");
            //设置请求类型
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            //设置超时时间
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setDoOutput(true);
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应    设置接收格式
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            // 将获得的String对象转为JSON格式
            JSONObject jsonObject = JSONObject.parseObject(sb.toString());
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取用户个人信息
     *
     * @return
     */
    public static JSONObject getRefreshToken(String refreshToken) {

        String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + APP_ID + "&grant_type=refresh_token&refresh_token=" + refreshToken;
        PrintWriter out = null;
        BufferedReader in = null;
        String line;
        StringBuffer sb = new StringBuffer();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();

            // 设置通用的请求属性 设置请求格式
            //设置返回类型
            conn.setRequestProperty("contentType", "text/plain");
            //设置请求类型
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            //设置超时时间
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setDoOutput(true);
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应    设置接收格式
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            // 将获得的String对象转为JSON格式
            JSONObject jsonObject = JSONObject.parseObject(sb.toString());
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 统一下单接口
     *
     * @return
     */
    public static String getPayOrder(String xmlParam) {

        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        PrintWriter out = null;
        BufferedReader in = null;
        String line;
        StringBuffer sb = new StringBuffer();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();

            // 设置通用的请求属性 设置请求格式
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-type", "application/xml");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            //设置超时时间
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.connect();


            // 当outputStr不为null时向输出流写数据
            if (null != xmlParam) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(xmlParam.getBytes("UTF-8"));

            }

            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应    设置接收格式
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));

            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 推送工具类
     *
     * @param params      推送消息内容
     * @param accessToken
     * @return boolean
     * @author zhanglei
     * @date 2018/12/25
     */
    public static boolean setPush(String params, String accessToken) {
        boolean flag = false;
        String url = PUSH_URL + accessToken;
        OutputStream outputStream = null;
        InputStreamReader inputStreamReader = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        HttpsURLConnection connection = null;
        try {
            // 创建URL对象
            URL realUrl = new URL(url);
            // 打开连接 获取连接对象
            connection = (HttpsURLConnection) realUrl.openConnection();
            // 设置请求编码
            connection.addRequestProperty("encoding", "UTF-8");
            // 设置允许输入
            connection.setDoInput(true);
            // 设置允许输出
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            // 当outputStr不为null时向输出流写数据
            if (null != params) {
                outputStream = connection.getOutputStream();
                // 注意编码格式
                outputStream.write(params.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            inputStream = connection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            JSONObject jsonObject = JSONObject.parseObject(buffer.toString());
            int errorCode = jsonObject.getInteger("errcode");
            String errorMessage = jsonObject.getString("errmsg");
            if (errorCode == 0) {
                flag = true;
            } else {
                System.out.println("模板消息发送失败:" + errorCode + "," + errorMessage);
                flag = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 依次关闭打开的输入流
            try {
                connection.disconnect();
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                // 依次关闭打开的输出流
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }


    /**
     * 微信支付POST请求
     *
     * @param reqUrl       请求地址 示例：https://api.mch.weixin.qq.com/v3/pay/transactions/app
     * @param paramJsonStr 请求体 json字符串 此参数与微信官方文档一致
     * @return 订单支付的参数
     * @throws Exception
     */
    public static String V3PayPost(String reqUrl, String paramJsonStr) throws Exception {

        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(reqUrl);
        //装填参数
        StringEntity s = new StringEntity(paramJsonStr, "utf-8");
        s.setContentType("application/json");

        //设置参数到请求对象中
        httpPost.setEntity(s);
        //指定报文头【Content-type】、【User-Agent】
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
        httpPost.setHeader("Accept", "application/json");
        //httpPost.setHeader("Authorization",getToken("POST",HttpUrl.parse(reqUrl),paramJsonStr));

        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = WxPayHttpClientFactory.httpClient.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        //获取数据，并释放资源
        String body = closeHttpResponse(response);
        if (statusCode == 200) { //处理成功
            switch (reqUrl) {
                case "https://api.mch.weixin.qq.com/v3/pay/transactions/app"://返回APP支付所需的参数
                    return JSONObject.parseObject(body).getString("prepay_id");
                case "https://api.mch.weixin.qq.com/v3/refund/domestic/refunds"://返回APP退款结果
                    return body;
            }
        }
        return null;
    }

    /**
     * 获取数据，并释放资源
     *
     * @param response
     * @return
     * @throws IOException
     */
    public static String closeHttpResponse(CloseableHttpResponse response) throws IOException {
        String body = "";
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) { //处理成功
            //获取结果实体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                //按指定编码转换结果实体为String类型
                body = EntityUtils.toString(entity, "utf-8");
            }
            //EntityUtils.consume将会释放所有由httpEntity所持有的资源
            EntityUtils.consume(entity);
        }
        //释放链接
        response.close();
        return body;
    }


    /**
     * 微信调起支付参数
     * 返回参数如有不理解 请访问微信官方文档
     * https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_2_4.shtml
     *
     * @param prepayId         微信下单返回的prepay_id
     * @param appId            应用ID
     * @param mch_id           商户号
     * @param private_key_path 私钥路径
     * @return 当前调起支付所需的参数
     * @throws Exception
     */
    public static String WxAppPayTuneUp(String prepayId, String appId, String mch_id, String private_key_path) throws Exception {
        if (StringUtils.isNotBlank(prepayId)) {
            long timestamp = System.currentTimeMillis() / 1000;
            String nonceStr = UUID.randomUUID().toString().replace("-","");
            //加载签名
            String packageSign = sign(buildMessage2(appId, timestamp, nonceStr, prepayId).getBytes(), private_key_path);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("appId", appId);
            jsonObject.put("prepayId", prepayId);
            jsonObject.put("timeStamp", timestamp);
            jsonObject.put("nonceStr", nonceStr);
            jsonObject.put("package", "Sign=WXPay");
            jsonObject.put("signType", "RSA");
            jsonObject.put("sign", packageSign);
            jsonObject.put("partnerId", mch_id);
            return jsonObject.toJSONString();
        }
        return "";
    }

    /**
     * 按照前端签名文档规范进行排序，\n是换行
     *
     * @param appId     appId
     * @param timestamp 时间
     * @param nonceStr  随机字符串
     * @param prepay_id prepay_id
     * @return
     */
    public static String buildMessage2(String appId, long timestamp, String nonceStr, String prepay_id) {
        return appId + "\n"
                + timestamp + "\n"
                + nonceStr + "\n"
                + prepay_id + "\n";
    }

    public static String sign(byte[] message, String private_key_path) throws NoSuchAlgorithmException, SignatureException, IOException, InvalidKeyException {
        //签名方式
        Signature sign = Signature.getInstance("SHA256withRSA");
        //私钥
        sign.initSign(PemUtil
                .loadPrivateKey(new ClassPathResource(private_key_path).getInputStream()));
        sign.update(message);

        return Base64.getEncoder().encodeToString(sign.sign());
    }
}
