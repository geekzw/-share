package com.gzw;

import android.app.Application;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * Created by gujian
 * Time is 2017/11/21
 * Email is gujian@maihaoche.com
 */

public class MyApp extends Application {

    {
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        //微信 appid appsecret
        PlatformConfig.setQQZone("1106441319", "W5xflRhyR8IxLAsq");
        // QQ和Qzone appid appkey
        PlatformConfig.setAlipay("2015111700822536");
        //支付宝 appid
        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
        //易信 appkey
        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
        //Twitter appid appkey
        PlatformConfig.setPinterest("1439206");
        //Pinterest appid
        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
        //来往 appid appkey
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Config.DEBUG = true;
        UMShareAPI.get(this);
    }
}
