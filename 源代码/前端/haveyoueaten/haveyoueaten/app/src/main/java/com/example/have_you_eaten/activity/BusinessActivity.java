package com.example.have_you_eaten.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.have_you_eaten.R;
import com.example.have_you_eaten.api.ApiUrl;
import com.example.have_you_eaten.bean.AppStatus;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 商家中心界面
 */
public class BusinessActivity extends AppCompatActivity implements View.OnClickListener {
    Button exit ,return_manage;
    TextView name,phone,account,iden_number;
    private MHandler mHandler=new MHandler();
    private final String  TAG="BusinessActivity";
    public void init(){
        exit=findViewById(R.id.exit);
        exit.setOnClickListener(this);
        return_manage=findViewById(R.id.return_manage);
        return_manage.setOnClickListener(this);
        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone);
        account=findViewById(R.id.account);
        iden_number=findViewById(R.id.iden_number);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.businesscenter);
        init();
        getUserInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exit:
                /**
                 * TODO 跳转至登录界面
                 */
                Intent intent = new Intent(BusinessActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.return_manage:
                /**
                TODO 返回菜品管理界面
                 */
                Intent intent2 = new Intent(BusinessActivity.this,ProducerManagerActivity.class);
                startActivity(intent2);
                break;
        }
    }



    /**
    TODO 展示商家信息
     */
    private void getUserInfo() {
        // 1、初始化okhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        // 3、发送请求
        Request request = new Request.Builder().addHeader("Authorization", AppStatus.token)
                // TODO
                .url(ApiUrl.AUTHER_USER_INFO)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            /**
             * 请求错误的处理
             * @param call
             * @param e
             */
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                Log.e(TAG, "获取数据失败 ");
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                // TODO 处理后端返回值：res是一个json数据
                /**
                 * {}
                 */
                String res = response.body().string();
                //打印服务端返回结果,其中data字段为json所包含的数组
                Log.d(TAG, "onResponse: "+res);
                Message msg = new Message();
                msg.what = MHandler.GETINFO_SUCCESS;
                msg.obj = res;
                mHandler.sendMessage(msg);
            }
        });
    }

    /**
     * 处理各种外部请求消息
     */
    class MHandler extends Handler {
        private static final int GETINFO_SUCCESS = 1;
        private static final int CHANGEPWD_SUCCESS=2;

        @Override
        public void dispatchMessage(@NonNull Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case GETINFO_SUCCESS:
                    Log.v(TAG, "GETINFO_SUCCESS");
                    //TODO dialog,提示获得成功
                    String res= msg.obj.toString();
                    JSONObject jsonObject = JSON.parseObject(res).getJSONObject("data");
                    //将返回的对象放放到TextView对应的位置
                    name.setText(jsonObject.getString("name"));
                    phone.setText(jsonObject.getString("phone"));
                    account.setText(jsonObject.getString("username"));
                    iden_number.setText(jsonObject.getString("idenNum"));
                    Log.i(TAG, "注册信息: " + res);
                    break;
                case CHANGEPWD_SUCCESS:
                    Log.d(TAG, "CHANGEPWD_SUCCESS");
                    //TODO 修改密码成功，提示
                    break;
            }
        }
    }


}
