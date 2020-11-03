package com.example.have_you_eaten.activity;

import android.content.DialogInterface;
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
import com.example.have_you_eaten.bean.Commodity;

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
 * 商品管理主界面，对应producermanager.xml。
 * TODO 添加商品 、删除商品  按钮功能实现
 */
public class ProducerManagerActivity extends AppCompatActivity implements View.OnClickListener {
    Button addfood,deletefood;
    TextView textviewfoodname,textviewfoodnum,textviewfoodprice;
    EditText etfoodname,etfoodnum,etfoodprice;
    private static final String TAG = "ProducerManagerActivity";
    List<Commodity> commodityList = new ArrayList<>();
    private MHandler mHandler=new MHandler();
        protected void onCreate(Bundle savedInstanceState) {//初始化
            super.onCreate(savedInstanceState);
            setContentView(R.layout.producermanager);
            addfood=findViewById(R.id.add_button);
            deletefood=findViewById(R.id.delete_button);
            addfood.setOnClickListener(this);
            deletefood.setOnClickListener(this);
            getCommodityInfo();
        }

    /**
     * 读取数据库接口
     * TODO
     */
    private void getCommodityInfo() {
        // 1、初始化okhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        // 2、构建请求体requestBody
        MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
        // 3、发送请求
        Request request = new Request.Builder().addHeader("Authorization", AppStatus.token).
                // TODO AUTHER_USER_ORDER
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
                msg.what = MHandler.GET_COM_INFO;
                msg.obj = res;
                mHandler.sendMessage(msg);
            }
        });
    }




    /**
     * 写入数据库接口
     *
     */
    private void sendCommodityInfo(Commodity commodity) {
        // 1、初始化okhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        // 2、构建请求体requestBody
        MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
        // 3、发送请求
        RequestBody requestBody = RequestBody.create(JSON_TYPE, JSON.toJSONString(commodity));
        Request request = new Request.Builder().addHeader("Authorization",AppStatus.token).
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
                msg.what = MHandler.SEND_COM_INFO;
                msg.obj = res;
                mHandler.sendMessage(msg);
            }
        });
    }

    class MHandler extends Handler {
        private static final int GET_COM_INFO = 1;
        private static final int SEND_COM_INFO = 2;
        @Override
        public void dispatchMessage(@NonNull Message msg) { super.dispatchMessage(msg);
            switch (msg.what) {
                case GET_COM_INFO:
                    Log.v(TAG, "GETSENTINFO_SUCCESS");
                    String res_get= msg.obj.toString();
                    JSONObject jsonObject = JSON.parseObject(res_get).getJSONObject("data");
                    //TODO
                    Log.d(TAG, "dispatchMessage: "+res_get);

                    //TODO  等待返回参数进行修改
//                    JSONArray orderList = jsonObject.getJSONArray("orderList");
//
//                    Log.d(TAG, "dispatchMessage: "+orderList.toJSONString());
//                    for (int i = 0;i<orderList.size();i++){
//                        Commodity commodity = new Commodity();
//                        commodity.setName();
//                        order.setProducer(orderList.getJSONObject(i).getString("producer"));
//                        Log.d(TAG, "producer: "+order.getProducer());
//                        order.setTime(orderList.getJSONObject(i).getString("time"));
//                        order.setAddress(orderList.getJSONObject(i).getString("address"));
//                        commodityList.add(order);
//
//                    }
//                    CommodityInManagerAdapter commodityInManagerAdapter = new CommodityInManagerAdapter(ProducerManagerActivity.this,R.layout.manage_commodity_item,commodityList);
//                    ListView listView = findViewById(R.id.list_view);
//                    listView.setAdapter(commodityInManagerAdapter);
                    break;
                case SEND_COM_INFO:
                    String res_send = msg.obj.toString();
                    JSONObject jsonObject_send =JSON.parseObject(res_send);
                    //TODO 获取返回值，进行判断
                    String result= jsonObject_send.getString("");
                    //TODO 获取返回值之后的逻辑

            }
        }
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.add_button:
                AlertDialog.Builder builder = new AlertDialog.Builder(ProducerManagerActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                final View v= inflater.inflate(R.layout.add_commodity_dialog, null);
                builder.setView(v).setTitle("添加菜品")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                textviewfoodname=findViewById(R.id.textViewfoodname);
                                textviewfoodnum=findViewById(R.id.textViewfoodnum);
                                textviewfoodprice=findViewById(R.id.textViewfoodprice);
                                etfoodname=findViewById(R.id.commodityName);
                                etfoodnum=findViewById(R.id.commodityNumber);
                                etfoodprice=findViewById(R.id.commodityPrice);
                                if (etfoodname.getText().toString().isEmpty() || etfoodnum.getText().toString().isEmpty()
                                        ||etfoodprice.getText().toString().isEmpty()
                                ) {
                                    Toast.makeText(ProducerManagerActivity.this, "请填写完整", Toast.LENGTH_SHORT).show();
                                } else {
                                    //TODO 添加菜品到数据库
                                    Commodity commodity = new Commodity();
                                    commodity.setName(textviewfoodname.getText().toString());
                                    commodity.setNumber(Integer.parseInt(textviewfoodnum.getText().toString()));
                                    commodity.setPrice(Double.parseDouble(textviewfoodprice.getText().toString()));
                                    sendCommodityInfo(commodity);
                                }
                            }
                        });
                builder.show();
                break;
            case R.id.delete_button://xml存在不合理性，有待商讨

                break;
        }
    }
}
