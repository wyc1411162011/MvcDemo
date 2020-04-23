package com.dp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dp.listener.GetDataListener;
import com.dp.model.GetDataModel;
import com.dp.model.Impl.GetDataModelImpl;
import com.dp.util.UtilTools;

import java.io.File;
import java.io.IOException;

public class MainActivity extends FragmentActivity implements GetDataListener {
    private GetDataModelImpl getDataModel;
    private Button bt0;
    private Button bt1;
    private Button bt2;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt0 = (Button)findViewById(R.id.bt0);
        bt1 = (Button)findViewById(R.id.bt1);
        bt2 = (Button)findViewById(R.id.bt2);
        tv = (TextView)findViewById(R.id.tv);

        bt0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.baidu.com/";
                getDataModel.getData(url,MainActivity.this);

            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //换一种形态的mvc模式这个时候只不过是没有回调lister，但是方法返回值里面有也属于mvc模式
                int number = UtilTools.parseInt("2");
                Toast.makeText(getApplicationContext(),"获取的数据"+ number,Toast.LENGTH_LONG).show();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //没有mvc模式 数据处理的能力都在activity层也就是Controller层
                //这样造成activity 有控制层的能力也有model层的数据处理的能力，导致activity代码量很大
                String url = "https://www.baidu.com/";
                getData(url,MainActivity.this);
            }
        });
        if(getDataModel == null){
            getDataModel = new GetDataModel();
        }
    }

    @Override
    public void onSuccess(String data) {
        if(!TextUtils.isEmpty(data)){
            tv.setText(data);
        }else{
            tv.setText("请求的结果为空");
        }

    }

    @Override
    public void onFail() {
        tv.setText("---请求的结果失败--");
    }
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
