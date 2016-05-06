package com.set.kingbaselib.thirdplatform;

import android.content.SyncRequest;

/**
 * Author    LeoCheung
 * Email     leocheung4ever@gmail.com
 * Description  第三方平台 登录分享 辅助类
 * Date          Author          Version          Description
 * ------------------------------------------------------------------
 * 2016/5/4     LeoCheung       1.0              1.0
 * Why & What is modified:
 */
public class PlatformConfig {

    private static PlatformConfig mInstance;

    public static PlatformConfig getInstance() {
        if (mInstance == null) {
            synchronized (PlatformConfig.class) {
                if (mInstance == null) {
                    mInstance = new PlatformConfig();
                }
            }
        }
        return mInstance;
    }

    /**
     * init the weibo config,
     *
     * @param key      app key
     * @param secret   app secret
     * @param callback the callback url
     * @param scope    the scope
     * @return
     */
    public PlatformConfig initWeibo(String key, String secret, String callback, String scope) {
        this.WBAppId = key;
        this.WBAppSecret = secret;
        this.WBCallBack = callback;
        this.WBScope = scope;
        return this;
    }

    /**
     * init the wechat
     *
     * @param key    app key
     * @param secret app secret
     * @param scope  the scope
     * @param state  the state
     * @return
     */
    public PlatformConfig initWeChat(String key, String secret, String scope, String state) {
        this.WXAppId = key;
        this.WXAppSecret = secret;
        this.WXScope = scope;
        this.WXState = state;
        return this;
    }

    /**
     * init QQ config
     *
     * @param key    app key
     * @param secret app secret
     * @return
     */
    public PlatformConfig initQQ(String key, String secret) {
        this.QQAppId = key;
        this.QQAppSecret = secret;
        return this;
    }

    private String QQAppId = "";
    private String QQAppSecret = "";

    private String WXAppId = "";
    private String WXAppSecret = "";
    private String WXScope = ""; //授权域
    private String WXState = ""; //

    private String WBAppId = "";
    private String WBAppSecret = "";
    private String WBCallBack = "";
    private String WBScope = "";

    public String getQQAppId() {
        return QQAppId;
    }

    public String getQQAppSecret() {
        return QQAppSecret;
    }

    public String getWXAppId() {
        return WXAppId;
    }

    public String getWXAppSecret() {
        return WXAppSecret;
    }

    public String getWXScope() {
        return WXScope;
    }

    public String getWXState() {
        return WXState;
    }

    public String getWBAppId() {
        return WBAppId;
    }

    public String getWBAppSecret() {
        return WBAppSecret;
    }

    public String getWBCallBack() {
        return WBCallBack;
    }

    public String getWBScope() {
        return WBScope;
    }

    public void setQQAppId(String QQAppId) {
        this.QQAppId = QQAppId;
    }

    public void setQQAppSecret(String QQAppSecret) {
        this.QQAppSecret = QQAppSecret;
    }

    public void setWXAppId(String WXAppId) {
        this.WXAppId = WXAppId;
    }

    public void setWXAppSecret(String WXAppSecret) {
        this.WXAppSecret = WXAppSecret;
    }

    public void setWXScope(String WXScope) {
        this.WXScope = WXScope;
    }

    public void setWXState(String WXState) {
        this.WXState = WXState;
    }

    public void setWBAppId(String WBAppId) {
        this.WBAppId = WBAppId;
    }

    public void setWBAppSecret(String WBAppSecret) {
        this.WBAppSecret = WBAppSecret;
    }

    public void setWBCallBack(String WBCallBack) {
        this.WBCallBack = WBCallBack;
    }

    public void setWBScope(String WBScope) {
        this.WBScope = WBScope;
    }
}
