package com.example.have_you_eaten.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.have_you_eaten.R;
import com.example.have_you_eaten.api.ApiUrl;
import com.example.have_you_eaten.bean.AppStatus;
import com.example.have_you_eaten.bean.PersonInfo;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * 用户登录之后的主界面,对应activity_main.xml
 * TODO 界面基本完善，点击事件基本未完成
 */
public class PersonalActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private MHandler mHandler = new MHandler();
    //从登陆将用户账号信息传过来
   PersonInfo personInfo = new PersonInfo();
    Button password,back;
    EditText pw1,pw2;
    TextView address,name,phone;

    /**
     * 初始化布局信息
     */
    public void init(){
        password=findViewById(R.id.password);
        address=(TextView)findViewById(R.id.address);
        pw1=(EditText)findViewById(R.id.pw1);
        pw2=(EditText)findViewById(R.id.pw2);
        back = findViewById(R.id.back);
        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone);
        password.setOnClickListener(this);
        address.setOnClickListener(this);
        back.setOnClickListener(this);
    }


    /**
     * 与后端交互
     *TODO 通过账号获取用户信息
     * @param
     */
    private void getUserInfo() {
        // 1、初始化okhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        // 2、构建请求体requestBody
        MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
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
                JSONObject jsonObject = JSON.parseObject(res).getJSONObject("data");
                //将返回的对象放放到TextView对应的位置
//                name.setText(jsonObject.getString("name"));
//                phone.setText(jsonObject.getString("phone"));
//                address.setText(jsonObject.getString("address"));
                Log.i(TAG, "注册信息: " + res);
                // 构造并发送消息給UI线程
                Message msg = new Message();
                msg.what = MHandler.GETINFO_SUCCESS;
                msg.obj = res;
                mHandler.sendMessage(msg);
            }
        });
    }


    /**
     *
     * TODO 修改数据库中密码
     * @param userAccount
     */
    public void changePassword(String userAccount,String userPassWord){
        // 1、初始化okhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        // 2、构建请求体requestBody
        MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
        // TODO userPassWord
        RequestBody requestBody = RequestBody.create(JSON_TYPE, JSON.toJSONString(userAccount+" "+userPassWord));
        // 3、发送请求
        Request request = new Request.Builder()
                // TODO
                .url(ApiUrl.AUTHER_USER_PASSWORD)
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
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                // TODO 处理后端返回值：res是一个json数据
                /**
                 * {}
                 */
                String res = response.body().string();
                //打印服务端返回结果,其中data字段为json所包含的数组

                Log.i(TAG, "注册信息: " + res);
                // 构造并发送消息給UI线程
                Message msg = new Message();
                msg.what = MHandler.CHANGEPWD_SUCCESS;
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
                    address.setText(jsonObject.getString("address"));
                    name.setText(jsonObject.getString("name"));
                    phone.setText(jsonObject.getString("phone"));
//                    Log.d(TAG, "dispatchMessage: ");
                    break;
                case CHANGEPWD_SUCCESS:
                    Log.d(TAG, "CHANGEPWD_SUCCESS");
                    //TODO 修改密码成功，提示
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal);
          init();
         //调用方法，从后端获得数据
        getUserInfo();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.password:
                //修改密码
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonalActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                final View view = inflater.inflate(R.layout.passwordchange, null);
                builder.setView(view).setTitle("修改密码")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                pw1 = (EditText) view.findViewById(R.id.pw1);
                                pw2 = (EditText) view.findViewById(R.id.pw2);
                                if (pw1.getText().toString().isEmpty() || pw2.getText().toString().isEmpty()
                                ) {
                                    Toast.makeText(PersonalActivity.this, "请填写完整", Toast.LENGTH_SHORT).show();
                                } else if (!pw1.getText().toString().equals(pw2.getText().toString())) {
                                    Toast.makeText(PersonalActivity.this, "两次填写不一致", Toast.LENGTH_SHORT).show();
                                } else {
                                    //TODO 修改密码
                                    changePassword(personInfo.getAccount(),personInfo.getPassWord());
                                    Toast.makeText(PersonalActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                builder.show();
                break;
            case R.id.back:
                Intent intent = new Intent(PersonalActivity.this,MenuActivity.class);
                startActivity(intent);
                break;


        }
    }

}
