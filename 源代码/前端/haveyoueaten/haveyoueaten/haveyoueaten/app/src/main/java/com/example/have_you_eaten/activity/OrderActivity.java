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
import com.example.have_you_eaten.adapter.OrderAdapter;
import com.example.have_you_eaten.api.ApiUrl;
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
 * 消费者订单界面,对应consumerorder.xml
 * TODO 查看个人信息、修改密码   模块未完成
 */
public class OrderActivity extends AppCompatActivity implements View.OnClickListener  {
    ListView orderList ;
    Button back;
    private final String  TAG="OrderActivity";
    private MHandler mHandler = new MHandler();
    private List<Order> orderList_items = new ArrayList<>();
    /**
     * 初始化参数信息
     */
     public void init(){
        orderList = findViewById(R.id.list_items);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consumerorder);
        init();
    }
    //TODO 方法名、参数
    private void getOrders(String userAccount) {
        // 1、初始化okhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        // 2、构建请求体requestBody
        MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
        // TODO userAccount
        RequestBody requestBody = RequestBody.create(JSON_TYPE, JSON.toJSONString(userAccount));
        // 3、发送请求
        Request request = new Request.Builder()
                // TODO AUTHER_USER_ORDER
                .url(ApiUrl.AUTHER_USER_ORDER)
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
                //TODO 封装到order类中  依据返回的类型，进行下面adapter的适配 可能包含循环，因为data可能是一个list
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
     *  TODO 处理各种外部请求消息
     */
    class MHandler extends Handler {
        private static final int GETINFO_SUCCESS = 1;
        @Override
        public void dispatchMessage(@NonNull Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case GETINFO_SUCCESS:
                    Log.v(TAG, "GETINFO_SUCCESS");
                    //TODO 刷新线程，展示订单页面
                    String res= msg.obj.toString();
                    JSONObject jsonObject = JSON.parseObject(res).getJSONObject("data");
                    Log.d(TAG, "dispatchMessage: "+res);
                    JSONArray orderList = jsonObject.getJSONArray("orderList");
                    Log.d(TAG, "dispatchMessage: "+orderList.toJSONString());
                    for (int i = 0;i<orderList.size();i++){
                        Order order = new Order();
                        //TODO 商家地址名称
                        order.setProducer(orderList.getJSONObject(i).getString("producer"));
                        order.setCommodity(orderList.getJSONObject(i).getString("commodity"));
                        orderList_items.add(order);
                    }
                    OrderAdapter orderAdapter = new OrderAdapter(OrderActivity.this,R.layout.order_items,orderList_items);
                    ListView listView = findViewById(R.id.list_items);
                    listView.setAdapter(orderAdapter);
                    break;

            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                Intent intent = new Intent(OrderActivity.this,MenuActivity.class);
                startActivity(intent);
                break;
        }
    }
}
