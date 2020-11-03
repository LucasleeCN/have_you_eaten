package com.example.have_you_eaten.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.have_you_eaten.R;
import com.example.have_you_eaten.adapter.SentCommodityAdapter;
import com.example.have_you_eaten.api.ApiUrl;
import com.example.have_you_eaten.bean.AppStatus;
import com.example.have_you_eaten.bean.Order;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 对应送餐界面availablemission.xml
 * TODO 未实现删除方法 待定
 */
public class  SentCommodityActivity extends AppCompatActivity implements View.OnClickListener {
    private final String  TAG="SentCommodityActivity";
    private MHandler mHandler=new MHandler();

    private List<Order> sentList_item = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.availablemission);
        getMission();
    }

    /**
     *     TODO 调用删除数据库方法
     *     参数未修改
     */
    private void getMission() {
        // 1、初始化okhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        // 2、构建请求体requestBody
        MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
        // 3、发送请求
        Request request = new Request.Builder().addHeader("Authorization", AppStatus.token).
                url(ApiUrl.AUTHER_USER_ORDER)//需要更改
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
                //TODO 封装到order类中  依据返回的类型，进行下面adapter的适配 可能包含循环，因为data可能是一个list
                Log.i(TAG, "注册信息: " + res);
                // 构造并发送消息給UI线程
                Message msg = new Message();
                msg.what = MHandler.GETSENTINFO_SUCCESS;
                msg.obj = res;
                mHandler.sendMessage(msg);
            }
        });
    }



    /**
     * 不知道干什么用的
     * @param v
     */
    @Override
    public void onClick(View v) {

    }

    class MHandler extends Handler {
        private static final int GETSENTINFO_SUCCESS = 1;
        private static final int DELETEINFO_SUCCESS = 2;
        @Override
        public void dispatchMessage(@NonNull Message msg) { super.dispatchMessage(msg);
            switch (msg.what) {
                case GETSENTINFO_SUCCESS:
                    Log.v(TAG, "GETSENTINFO_SUCCESS");
                    String res= msg.obj.toString();
                    JSONObject jsonObject = JSON.parseObject(res).getJSONObject("data");
                    Log.d(TAG, "dispatchMessage: "+res);
                    JSONArray orderList = jsonObject.getJSONArray("orderList");
                    Log.d(TAG, "dispatchMessage: "+orderList.toJSONString());
                    for (int i = 0;i<orderList.size();i++){
                        Order order = new Order();
                        order.setProducer(orderList.getJSONObject(i).getString("producer"));
                        Log.d(TAG, "producer: "+order.getProducer());
                        order.setTime(orderList.getJSONObject(i).getString("time"));
                        order.setAddress(orderList.getJSONObject(i).getString("address"));
                        sentList_item.add(order);
                    }
                    SentCommodityAdapter sentCommodityAdapter = new SentCommodityAdapter(SentCommodityActivity.this,R.layout.mission_item,sentList_item);
                    ListView listView = findViewById(R.id.mission_listview);
                    listView.setAdapter(sentCommodityAdapter);
                    break;

            }
        }
    }
}
