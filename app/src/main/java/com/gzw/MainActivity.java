package com.gzw;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by gujian
 * Time is 2017/11/21
 * Email is gujian@maihaoche.com
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mDirShareBtn;
    private Button mShareBtn;
    private Button mAuthBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
        setContentView(R.layout.activity_main);
        mDirShareBtn = findViewById(R.id.dir_share);
        mShareBtn = findViewById(R.id.share);
        mAuthBtn = findViewById(R.id.auth);
        mDirShareBtn.setOnClickListener(this);
        mShareBtn.setOnClickListener(this);
        mAuthBtn.setOnClickListener(this);
        applyPermission();

    }

    //定向分享
    private void share(){
        SnsPlatform platform = SHARE_MEDIA.QQ.toSnsPlatform();
        SHARE_MEDIA share_media = platform.mPlatform;
        ArrayList<String> styles = new ArrayList<String>();
        styles.add(StyleUtil.IMAGELOCAL);
        styles.add(StyleUtil.IMAGEURL);
        styles.add(StyleUtil.WEB11);
        styles.add(StyleUtil.MUSIC11);
        styles.add(StyleUtil.VIDEO11);
        UMImage umImage = new UMImage(this,R.mipmap.ic_launcher);
        setContentView(R.layout.activity_main);
        new ShareAction(MainActivity.this)
                .withText("hello")
                .withMedia(umImage)
                .withMedia(new UMWeb("http://www.baidu.com"))
                .setPlatform(share_media)
                .setCallback(new ShareListener())
                .share();
    }
    //默认面板分享
    private void share2(){
        new ShareAction(MainActivity.this)
                .withText("hello")
                .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                .setCallback(new ShareListener())
                .open();
    }

    private void auth(){
        UMShareAPI umShareAPI = UMShareAPI.get(this);
        umShareAPI.getPlatformInfo(this,SHARE_MEDIA.QQ,umAuthListener);
    }



    private void applyPermission(){
        if(Build.VERSION.SDK_INT>=23){
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this,mPermissionList,123);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);//完成回调
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.dir_share){
            share();
        }else if(v.getId() == R.id.share){
            share2();
        }else{
            auth();
        }
    }

    static class ShareListener implements UMShareListener{
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Log.e("share:start:",share_media.name());
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Log.e("share:onResult:",share_media.name());
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Log.e("share:onError:",throwable.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Log.e("share:onCancel:",share_media.name());
        }
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
        }
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };
}
