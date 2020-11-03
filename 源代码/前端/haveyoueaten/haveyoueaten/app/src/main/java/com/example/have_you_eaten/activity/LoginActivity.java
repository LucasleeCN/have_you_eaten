package com.example.have_you_eaten.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.have_you_eaten.R;
import com.example.have_you_eaten.api.ApiUrl;
import com.example.have_you_eaten.bean.AppStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private AlertDialog alertDialog,alertDialog1;
    private Button btn,sign_in;
    final String[] items = {"顾客", "商家", "骑手"};
    public Integer type = 0;
    private EditText et1,et2;
    private Map<String,Object> map = new HashMap();
    private MHandler mHandler = new MHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn = (Button)findViewById(R.id.sign_up);
        sign_in = (Button)findViewById(R.id.sign_in);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et1 = (EditText)findViewById(R.id.editUserID);
                et2 = (EditText)findViewById(R.id.editPassword);
                String username = et1.getText().toString();
                String password = et2.getText().toString();
                map.put("username",username);
                map.put("password",password);
                map.put("type",type);
                asyncValidate(map);
                /**
                 * TODO 界面跳转
                 */
//                Intent intent = new Intent(LoginActivity.this, DelivererActivity.class);
//                startActivity(intent);
            }
        });

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void showList(View view){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        final Button button = (Button)findViewById(R.id.button2);
        alertBuilder.setTitle("请选择用户类型");
        alertBuilder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                type = i + 1;
                button.setText(items[i]);
            }
        });

        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });

        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });

        alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    class MHandler extends Handler {
        private static final int LOGIN_SUCCESS = 1;
        private static final int LOGIN_FAIL = 2;

        @Override
        public void dispatchMessage(@NonNull Message msg) {
            alertDialog1 = new AlertDialog.Builder(LoginActivity.this)
                    .setIcon(R.mipmap.ic_launcher)//图标
                    .create();
            switch (msg.what) {
                case LOGIN_SUCCESS:
                    String res = msg.obj.toString();
                    JSONObject jsonObject = JSONObject.parseObject(res);
                    AppStatus.token=jsonObject.getJSONObject("data").getString("token");
                    String type = jsonObject.getJSONObject("data").getJSONObject("user").getString("type");
                    switch (type){
                        case "普通用户":
                            Intent intent_user = new Intent(LoginActivity.this,MenuActivity.class);
                            startActivity(intent_user);
                            break;
                        case "骑手":
                            Intent intent_rider = new Intent(LoginActivity.this,DelivererActivity.class);
                            startActivity(intent_rider);
                            break;
                        case "商家":
                            Intent intent_business = new Intent(LoginActivity.this,BusinessActivity.class);
                            startActivity(intent_business);
                            break;
                    }

                    break;
                case LOGIN_FAIL:
                    alertDialog1.setTitle("登录失败");
                    alertDialog1.show();
                    break;
            }
        }
    }

    private void asyncValidate(Map<String, Object> map) {
        // 1、初始化okhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();

        // 2、构建请求体requestBody
        MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON_TYPE, JSON.toJSONString(map));

        // 3、发送请求
        Request request = new Request.Builder()
                .url(ApiUrl.LOGIN_URL)
                .post(requestBody)
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
                Message msg = new Message();
                msg.what = MHandler.LOGIN_FAIL;
                msg.obj = null;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                // TODO 处理后端返回值：res是一个json数据
                String res = response.body().string();
                //打印服务端返回结果
                Log.i(TAG, "登录信息: " + res);
                JSONObject jsonObject = JSONObject.parseObject(res);
                // 构造并发送消息給UI线程
                Message msg = new Message();
                if (jsonObject.getInteger("code")==0){
                    msg.what = MHandler.LOGIN_SUCCESS;
                }else {
                    msg.what=MHandler.LOGIN_FAIL;
                }
                msg.obj = res;
                mHandler.sendMessage(msg);

            }
        });
    }

}