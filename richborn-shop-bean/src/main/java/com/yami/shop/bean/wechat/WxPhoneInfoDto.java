
package com.yami.shop.bean.wechat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.processing.Generated;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "errcode",
    "errmsg",
    "phone_info"
})
@Generated("jsonschema2pojo")
public class WxPhoneInfoDto implements Serializable
{

    @JsonProperty("errcode")
    private Long errcode;
    @JsonProperty("errmsg")
    private String errmsg;
    @JsonProperty("phone_info")
    private PhoneInfo phoneInfo;
    private final static long serialVersionUID = -9003353507551146551L;

    @JsonProperty("errcode")
    public Long getErrcode() {
        return errcode;
    }

    @JsonProperty("errcode")
    public void setErrcode(Long errcode) {
        this.errcode = errcode;
    }

    @JsonProperty("errmsg")
    public String getErrmsg() {
        return errmsg;
    }

    @JsonProperty("errmsg")
    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    @JsonProperty("phone_info")
    public PhoneInfo getPhoneInfo() {
        return phoneInfo;
    }

    @JsonProperty("phone_info")
    public void setPhoneInfo(PhoneInfo phoneInfo) {
        this.phoneInfo = phoneInfo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(WxPhoneInfoDto.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("errcode");
        sb.append('=');
        sb.append(((this.errcode == null)?"<null>":this.errcode));
        sb.append(',');
        sb.append("errmsg");
        sb.append('=');
        sb.append(((this.errmsg == null)?"<null>":this.errmsg));
        sb.append(',');
        sb.append("phoneInfo");
        sb.append('=');
        sb.append(((this.phoneInfo == null)?"<null>":this.phoneInfo));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.errcode == null)? 0 :this.errcode.hashCode()));
        result = ((result* 31)+((this.errmsg == null)? 0 :this.errmsg.hashCode()));
        result = ((result* 31)+((this.phoneInfo == null)? 0 :this.phoneInfo.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof WxPhoneInfoDto) == false) {
            return false;
        }
        WxPhoneInfoDto rhs = ((WxPhoneInfoDto) other);
        return ((((this.errcode == rhs.errcode)||((this.errcode!= null)&&this.errcode.equals(rhs.errcode)))&&((this.errmsg == rhs.errmsg)||((this.errmsg!= null)&&this.errmsg.equals(rhs.errmsg))))&&((this.phoneInfo == rhs.phoneInfo)||((this.phoneInfo!= null)&&this.phoneInfo.equals(rhs.phoneInfo))));
    }

}
