package com.example.have_you_eaten.lsy_activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.have_you_eaten.R;
import com.example.have_you_eaten.activity.OrderSuccessActivity;
import com.example.have_you_eaten.api.ApiUrl;
import com.example.have_you_eaten.bean.AppStatus;
import com.example.have_you_eaten.lsy_modle.Good;
import com.example.have_you_eaten.lsy_modle.Order;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
public class BuyerInShopActivity extends AppCompatActivity {

    private List<Good> goods=new ArrayList<>();
    private Order order;
    Long shop_id;
    private MHandler mHandler = new MHandler();
    private final String TAG = "FoodMenuActivity";
    private RecyclerView recyclerView;
    private GoodAdapter adapter;
    private TextView total;
    private Button settle;
    private View underbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lsy_activity_buyer_in_shop);
        final Intent intent = getIntent();
        shop_id = intent.getLongExtra("shopid", 1);
        getFoodmenu(shop_id);

        order = new Order();
        order.buyList = new HashMap<>();

        //底边栏onClick
        underbar = this.findViewById(R.id.underbar);
        underbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BuyerInShopActivity.this, "展示购物车:\n" + order.printBuyList(), Toast.LENGTH_SHORT).show();
            }
        });

        //总金额同步展示
        total = (TextView) this.findViewById(R.id.total);
        total.setText("￥" + order.getTotal());

        //结算按钮onClick
        settle = (Button) this.findViewById(R.id.settle);
        settle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OkHttpClient okHttpClient = new OkHttpClient();
                // 2、构建请求体requestBody
                MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
                // 3、发送请求
                RequestBody requestBody = RequestBody.create(JSON_TYPE, JSON.toJSONString(order.getMsg()));

                // 3、发送请求
                Request request = new Request.Builder()
                        .url(ApiUrl.AUTHER_SEND_FOODMENU_ORDER)
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
                        Log.i(TAG, "商家菜品信息: " + res);
                        // 构造并发送消息給UI线程
                        Message msg = new Message();
                        msg.what = MHandler.SENTFOODMENU_SUCCESS;
                        msg.obj = res;
                        mHandler.sendMessage(msg);
                    }
                });
            }
        });
    }

    /**
     *获得 商品菜单
     * @param shopid
     */
    private void getFoodmenu(Long shopid) {
        // 1、初始化okhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        // 2、构建请求体requestBody
        MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
        // 3、发送请求
        Request request = new Request.Builder().addHeader("Authorization", AppStatus.token).
                url(ApiUrl.AUTHER_GET_FOODMENU_ORDER+"?shopId="+shopid)//需要更改
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
                String res = response.body().string();
                //打印服务端返回结果,其中data字段为json所包含的数组

                Log.i(TAG, "商家菜品信息: " + res);
                // 构造并发送消息給UI线程
                Message msg = new Message();
                msg.what = MHandler.GETFOODMENU_SUCCESS;
                msg.obj = res;
                mHandler.sendMessage(msg);
            }
        });
    }


    class MHandler extends Handler {
        private static final int GETFOODMENU_SUCCESS = 1;
        private static final int SENTFOODMENU_SUCCESS = 2;

        @Override
        public void dispatchMessage(@NonNull Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case GETFOODMENU_SUCCESS:
                    Log.v(TAG, "GETSENTINFO_SUCCESS");
                    String res = msg.obj.toString();
                    JSONObject jsonObject = JSON.parseObject(res).getJSONObject("data");
                    Log.d(TAG, "dispatchMessage: " + res);
                    JSONArray shopmenu = jsonObject.getJSONArray("menus");
                    Log.d(TAG, "dispatchMessage: " + shopmenu.toJSONString());
                    for (int i = 0; i < shopmenu.size(); i++) {
                        Good good = new Good();
                        good.setId(shopmenu.getJSONObject(i).getLong("id"));
                        good.setName(shopmenu.getJSONObject(i).getString("name"));
                        good.setPrice(shopmenu.getJSONObject(i).getDouble("price"));
                        goods.add(good);
                    }
                    recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
                    recyclerView.setLayoutManager(new LinearLayoutManager(BuyerInShopActivity.this, RecyclerView.VERTICAL, false));
                    adapter = new GoodAdapter(BuyerInShopActivity.this, goods, order);
                    recyclerView.setAdapter(adapter);
                    adapter.setMyCallBackInAd(new GoodAdapter.MyCallBack() {
                        @Override
                        public void DoCallBack(int i) {
//                Toast.makeText(getApplicationContext(), "触发callback，回传数据=" + i, Toast.LENGTH_SHORT).show();
                            total.setText("￥" + order.getTotal());
                        }
                    });
                    break;
                case SENTFOODMENU_SUCCESS:
                    startActivity(new Intent(BuyerInShopActivity.this, OrderSuccessActivity.class));
            }

        }
    }


    /**
     * 用途未知
     * @param order
     */
    private void sendFoodorderInfo(Order order) {
        // 1、初始化okhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        // 2、构建请求体requestBody
        MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
        // 3、发送请求
        RequestBody requestBody = RequestBody.create(JSON_TYPE, JSON.toJSONString(order));
        Request request = new Request.Builder().addHeader("Authorization", AppStatus.token).
                        url(ApiUrl.AUTHER_SEND_FOODMENU_ORDER)//需要更改
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
                Log.i(TAG, "信息: " + res);
                // 构造并发送消息給UI线程
                Message msg = new Message();
                msg.what = MHandler.SENTFOODMENU_SUCCESS;
                msg.obj = res;
                mHandler.sendMessage(msg);
            }
        });
    }

}
