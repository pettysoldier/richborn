
package com.yami.shop.bean.wechat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.processing.Generated;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "phoneNumber",
    "purePhoneNumber",
    "countryCode",
    "watermark"
})
@Generated("jsonschema2pojo")
public class PhoneInfo implements Serializable
{

    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @JsonProperty("purePhoneNumber")
    private String purePhoneNumber;
    @JsonProperty("countryCode")
    private Long countryCode;
    @JsonProperty("watermark")
    private Watermark watermark;
    private final static long serialVersionUID = 2147856505602360390L;

    @JsonProperty("phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @JsonProperty("phoneNumber")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonProperty("purePhoneNumber")
    public String getPurePhoneNumber() {
        return purePhoneNumber;
    }

    @JsonProperty("purePhoneNumber")
    public void setPurePhoneNumber(String purePhoneNumber) {
        this.purePhoneNumber = purePhoneNumber;
    }

    @JsonProperty("countryCode")
    public Long getCountryCode() {
        return countryCode;
    }

    @JsonProperty("countryCode")
    public void setCountryCode(Long countryCode) {
        this.countryCode = countryCode;
    }

    @JsonProperty("watermark")
    public Watermark getWatermark() {
        return watermark;
    }

    @JsonProperty("watermark")
    public void setWatermark(Watermark watermark) {
        this.watermark = watermark;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(PhoneInfo.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("phoneNumber");
        sb.append('=');
        sb.append(((this.phoneNumber == null)?"<null>":this.phoneNumber));
        sb.append(',');
        sb.append("purePhoneNumber");
        sb.append('=');
        sb.append(((this.purePhoneNumber == null)?"<null>":this.purePhoneNumber));
        sb.append(',');
        sb.append("countryCode");
        sb.append('=');
        sb.append(((this.countryCode == null)?"<null>":this.countryCode));
        sb.append(',');
        sb.append("watermark");
        sb.append('=');
        sb.append(((this.watermark == null)?"<null>":this.watermark));
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
        result = ((result* 31)+((this.phoneNumber == null)? 0 :this.phoneNumber.hashCode()));
        result = ((result* 31)+((this.watermark == null)? 0 :this.watermark.hashCode()));
        result = ((result* 31)+((this.purePhoneNumber == null)? 0 :this.purePhoneNumber.hashCode()));
        result = ((result* 31)+((this.countryCode == null)? 0 :this.countryCode.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PhoneInfo) == false) {
            return false;
        }
        PhoneInfo rhs = ((PhoneInfo) other);
        return (((((this.phoneNumber == rhs.phoneNumber)||((this.phoneNumber!= null)&&this.phoneNumber.equals(rhs.phoneNumber)))&&((this.watermark == rhs.watermark)||((this.watermark!= null)&&this.watermark.equals(rhs.watermark))))&&((this.purePhoneNumber == rhs.purePhoneNumber)||((this.purePhoneNumber!= null)&&this.purePhoneNumber.equals(rhs.purePhoneNumber))))&&((this.countryCode == rhs.countryCode)||((this.countryCode!= null)&&this.countryCode.equals(rhs.countryCode))));
    }

}
