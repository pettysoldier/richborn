package com.yami.shop.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ---------\,,,/---------
 * ---------(o o)---------
 * -----oOOo-(3)-oOOo-----
 *
 * @Description:
 * @Author: zhushengguo
 * @Version:
 * @Date: 15:08 2023/2/20
 **/
public class RichBornStringUtils {

    public static String NumberToChinese(int num) {
        String numStr = "0123456789";
        String chineseStr = "零一二三四五六七八九";

        int index = numStr.indexOf(String.valueOf(num));
        if (index != -1) {
            return String.valueOf(chineseStr.toCharArray()[index]);
        }
        return null;
    }

    /**
     *
     * @param str 字符串
     * @param showStart 开头保留字符长度
     * @param showEnd 结尾保留字符长度
     * @param replaceChar 隐藏部分替代符号
     * @return 隐藏后的字符串
     */
    public static  String hideSubString(String str, int showStart, int showEnd, char replaceChar)
    {
        if (StringUtils.isEmpty(str))
        {
            return "";
        }
        if (str.length() > (showStart + showEnd))
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < str.length() - showStart - showEnd ; i++) {
                sb.append(replaceChar);
            }
            return str.substring(0, showStart) + sb.toString() + str.substring(str.length() - showEnd);
        }
        return str;
    }
    public static String  replaceBlankWithComma(String content){
        content= content.trim();
        List<String> result = new ArrayList<>();
        Arrays.stream(content.split(" ")).forEach(i->{
            if(!StringUtils.isEmpty(i.trim())){
                result.add(i);
            }
        });
       return String.join(",",result);
    }

    public static void main(String[] args) {
        String s = NumberToChinese(5);
        System.out.println(s);
    }
}
