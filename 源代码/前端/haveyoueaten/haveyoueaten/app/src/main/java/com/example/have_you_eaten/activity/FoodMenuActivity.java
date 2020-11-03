package com.example.have_you_eaten.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.have_you_eaten.R;
import com.example.have_you_eaten.adapter.FoodMenuAdapter;
import com.example.have_you_eaten.api.ApiUrl;
import com.example.have_you_eaten.bean.AppStatus;
import com.example.have_you_eaten.bean.FoodMenu;
import com.example.have_you_eaten.bean.Order;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 选餐界面
 */
public class FoodMenuActivity extends AppCompatActivity implements View.OnClickListener {
    List<FoodMenu> menus = new ArrayList<>();
    Button food_menu_confirm;
    String shop_name;
    private MHandler mHandler=new MHandler();
    private final String  TAG="FoodMenuActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_menu);
        init();
        FoodMenuAdapter adapter = new FoodMenuAdapter(FoodMenuActivity.this,R.layout.order_menu_item,menus);
        ListView listView = findViewById(R.id.foodmenu);
        listView.setAdapter(adapter);
        if (getIntent().getStringExtra("shopname") != null){
            shop_name=getIntent().getStringExtra("shopname");
        }
    }

public void init(){
    food_menu_confirm=findViewById(R.id.food_menu_confirm);
    food_menu_confirm.setOnClickListener(this);
}
    private void getshop_menu(String shop_name) {
        // 1、初始化okhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        // 2、构建请求体requestBody
        MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
        // TODO userPassWord
        RequestBody requestBody = RequestBody.create(JSON_TYPE, JSON.toJSONString(shop_name));
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
                String res = response.body().string();
                //打印服务端返回结果,其中data字段为json所包含的数组
                //TODO 封装到order类中  依据返回的类型，进行下面adapter的适配 可能包含循环，因为data可能是一个list
                Log.i(TAG, "注册信息: " + res);
                // 构造并发送消息給UI线程
                Message msg = new Message();
                msg.what = MHandler.GETFOODMENU;
                msg.obj = res;
                mHandler.sendMessage(msg);
            }
        });
    }
    private void sendFoodorderInfo(Order order) {
        // 1、初始化okhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        // 2、构建请求体requestBody
        MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
        // 3、发送请求
        RequestBody requestBody = RequestBody.create(JSON_TYPE, JSON.toJSONString(order));
        Request request = new Request.Builder().addHeader("Authorization", AppStatus.token).
                // TODO AUTHER_USER_ORDER
                        url(ApiUrl.AUTHER_USER_MENU)//需要更改
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
                // 处理后端返回值：res是一个json数据
                /**
                 * {}
                 */
                String res = response.body().string();
                //打印服务端返回结果,其中data字段为json所包含的数组
                //封装到order类中  依据返回的类型，进行下面adapter的适配 可能包含循环，因为data可能是一个list
                Log.i(TAG, "注册信息: " + res);
                // 构造并发送消息給UI线程
                Message msg = new Message();
                msg.what = MHandler.SENTORDERINFO;
                msg.obj = res;
                mHandler.sendMessage(msg);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.food_menu_confirm:
                /**
                 TODO  传送订单数据到数据库
                 */

                Intent intent = new Intent(FoodMenuActivity.this,OrderSuccessActivity.class);
                startActivity(intent);
                break;
        }
    }
    class MHandler extends Handler {
        private static final int GETFOODMENU = 1;
        private static final int SENTORDERINFO=2;
        @Override
        public void dispatchMessage(@NonNull Message msg) { super.dispatchMessage(msg);
            switch (msg.what) {
                case GETFOODMENU:
                    Log.v(TAG, "GETSHOP_MENU_SUCCESS");
                    String res= msg.obj.toString();
                    JSONObject jsonObject = JSON.parseObject(res).getJSONObject("data");
                    Log.d(TAG, "dispatchMessage: "+res);
                    JSONArray foodmenuList = jsonObject.getJSONArray("shopmenuList");
                    Log.d(TAG, "dispatchMessage: "+foodmenuList.toJSONString());
                    for (int i = 0;i<foodmenuList.size();i++){
                        FoodMenu foodMenu=new FoodMenu();
                        foodMenu.setName(foodmenuList.getJSONObject(i).getString("name"));
                        foodMenu.setPrice(foodmenuList.getJSONObject(i).getDouble("price"));
                        foodmenuList.add(foodMenu);
                    }
                    FoodMenuAdapter foodMenuAdapter= new FoodMenuAdapter(FoodMenuActivity.this,R.layout.order_menu_item,menus);
                    ListView listView = findViewById(R.id.mission_listview);
                    listView.setAdapter(foodMenuAdapter);
                    break;

            }
        }
    }
}
