package com.dp;

import android.annotation.TargetApi;
import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

/**
 * Created by ufsoft on2020-04-15
 * describle:
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        deleteFileUriExposure();
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void deleteFileUriExposure() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }
}
