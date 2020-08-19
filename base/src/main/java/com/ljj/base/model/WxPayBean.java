package com.ljj.base.model;

import com.google.gson.annotations.SerializedName;

public class WxPayBean {
    /**
     * appid : wxb94a510b66345977
     * timestamp : 1541749776
     * noncestr : valhbzghh2zm190uch1fw02w154akhvy
     * package : Sign=WXPay
     * partnerid : 1494168522
     * prepayid : wx091549362517231295b411582928248900
     * sign : 8518359E0CA557AC740D520717E964B1
     */

    private String appid;
    private String timestamp;
    private String noncestr;
    @SerializedName("package")
    private String packageX;
    private String partnerid;
    private String prepayid;
    private String sign;
    private String extData;

    public String getExtData() {
        return extData == null ? "" : extData;
    }

    public void setExtData(String extData) {
        this.extData = extData;
    }

    public String getAppid() {
        return appid == null ? "" : appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getTimestamp() {
        return timestamp == null ? "" : timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNoncestr() {
        return noncestr == null ? "" : noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPackageX() {
        return packageX == null ? "" : packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getPartnerid() {
        return partnerid == null ? "" : partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid == null ? "" : prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getSign() {
        return sign == null ? "" : sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "PayDataBean{" +
                "appid='" + appid + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", packageX='" + packageX + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", prepayid='" + prepayid + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
