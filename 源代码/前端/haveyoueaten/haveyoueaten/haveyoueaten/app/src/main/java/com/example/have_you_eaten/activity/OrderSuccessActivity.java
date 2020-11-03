package com.example.have_you_eaten.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.have_you_eaten.R;
/**
 * 订单成功页面
 */
public class OrderSuccessActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //初始化
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_success);
        Button btescMenu = findViewById(R.id.esc_menu);
        Button btorderDetail = findViewById(R.id.order_details);
        btescMenu.setOnClickListener(this);
        btorderDetail.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //返回首页按钮跳转到首页
            case R.id.esc_menu:
                Intent intent = new Intent(OrderSuccessActivity.this,MenuActivity.class);
                startActivity(intent);
                break;
            //跳转到订单页面
            case R.id.order_details:
                Intent intent1 = new Intent(OrderSuccessActivity.this,OrderActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
