package com.dp.model;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.dp.listener.GetDataListener;
import com.dp.model.Impl.GetDataModelImpl;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ufsoft on2020-04-14
 * describle:
 */
public class GetDataModel implements GetDataModelImpl {
    @Override
    public void getData(String url, final GetDataListener listener){
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url).addHeader("Content-Type","application/json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFail();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String resultStr = response.body().string();
                Handler mainHandler = new Handler(Looper.getMainLooper());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //已在主线程中，更新UI
                        if(response.isSuccessful()){//回调的方法执行在子线程。
                            listener.onSuccess(resultStr);
                        }else{
                            listener.onFail();
                        }
                    }
                });


            }



        });
    }
}
