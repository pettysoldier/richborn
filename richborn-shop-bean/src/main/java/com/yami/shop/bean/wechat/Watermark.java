
package com.yami.shop.bean.wechat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.processing.Generated;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "timestamp",
    "appid"
})
@Generated("jsonschema2pojo")
public class Watermark implements Serializable
{

    @JsonProperty("timestamp")
    private Long timestamp;
    @JsonProperty("appid")
    private String appid;
    private final static long serialVersionUID = 6591877952225537012L;

    @JsonProperty("timestamp")
    public Long getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @JsonProperty("appid")
    public String getAppid() {
        return appid;
    }

    @JsonProperty("appid")
    public void setAppid(String appid) {
        this.appid = appid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Watermark.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("timestamp");
        sb.append('=');
        sb.append(((this.timestamp == null)?"<null>":this.timestamp));
        sb.append(',');
        sb.append("appid");
        sb.append('=');
        sb.append(((this.appid == null)?"<null>":this.appid));
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
        result = ((result* 31)+((this.timestamp == null)? 0 :this.timestamp.hashCode()));
        result = ((result* 31)+((this.appid == null)? 0 :this.appid.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Watermark) == false) {
            return false;
        }
        Watermark rhs = ((Watermark) other);
        return (((this.timestamp == rhs.timestamp)||((this.timestamp!= null)&&this.timestamp.equals(rhs.timestamp)))&&((this.appid == rhs.appid)||((this.appid!= null)&&this.appid.equals(rhs.appid))));
    }

}
