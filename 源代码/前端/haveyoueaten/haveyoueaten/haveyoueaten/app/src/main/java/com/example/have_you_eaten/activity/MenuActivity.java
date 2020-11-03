package com.example.have_you_eaten.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.have_you_eaten.R;
import com.example.have_you_eaten.adapter.ShopAdapter;
import com.example.have_you_eaten.api.ApiUrl;
import com.example.have_you_eaten.bean.Shop;

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
 * 搜索界面，对应menu.xml
 * TODO 待定
 */
public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    ListView shopList;
    String shopname;
    private final String  TAG="MenuActivity";
    private MHandler mHandler=new MHandler();
    private List<Shop> menuList_item = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        Button btOrder = (Button)findViewById(R.id.Order);
        Button btPerson = (Button)findViewById(R.id.Settings);
      shopList=findViewById(R.id.shops);
        btOrder.setOnClickListener(this);
        btPerson.setOnClickListener(this);
    }
    //TODO 方法名、参数
    private void getShop(String userAccount) {//变量是否需要
        // 1、初始化okhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        // 2、构建请求体requestBody
        MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
        // TODO userAccount
        RequestBody requestBody = RequestBody.create(JSON_TYPE, JSON.toJSONString(userAccount));
        // 3、发送请求
        Request request = new Request.Builder()
                // TODO AUTHER_USER_ORDER
                .url(ApiUrl.AUTHER_USER_ORDER)//更改哦
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
                //TODO 2020 11 1 暂未完成
                JSONObject jsonObject = JSON.parseObject(res).getJSONObject("data");
                Shop shops = new Shop();
                shops.setName(jsonObject.getString("shopname"));
                shops.setPhone(jsonObject.getString("shopphone"));
                shops.setAddress(jsonObject.getString("shopaddress"));
                menuList_item.add(shops);
                //Adapter
                ShopAdapter shopAdapter = new ShopAdapter(MenuActivity.this,R.layout.menu_item,menuList_item);
                final ListView listView = findViewById(R.id.shops);
                listView.setAdapter(shopAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Shop shop=menuList_item.get(position);
                        Intent intent=new Intent(MenuActivity.this,FoodMenuActivity.class);
                        intent.putExtra("shopname", shop.getName());
                        startActivity(intent);
                    }
                });
                Log.i(TAG, "注册信息: " + res);
                // 构造并发送消息給UI线程
                Message msg = new Message();
                msg.what = MenuActivity.MHandler.GETMENU_SUCCESS;
                msg.obj = res;
                mHandler.sendMessage(msg);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.search_button:
                break;
            case R.id.Order:
                Intent intentOrder=new Intent(MenuActivity.this,OrderActivity.class);
                startActivity(intentOrder);
                break;
            case R.id.Settings:
                Intent userOrder=new Intent(MenuActivity.this, PersonalActivity.class);
                startActivity(userOrder);
                break;
                }
        }


    class MHandler extends Handler {
        private static final int GETMENU_SUCCESS = 1;
        @Override
        public void dispatchMessage(@NonNull Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case GETMENU_SUCCESS:
                    Log.v(TAG, "GETMENU_SUCCESS");
                    //TODO 刷新线程，展示订单页面

                    break;

            }
        }
    }

}


