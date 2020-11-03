package com.example.have_you_eaten.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.have_you_eaten.R;

/**
 * 骑手主页
 * TODO 骑手订单跳转
 */
public class DelivererActivity extends AppCompatActivity {

    private Button order,about,version;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliverer);
        order = (Button) findViewById(R.id.order);
        about = (Button) findViewById(R.id.about);
        version = (Button) findViewById(R.id.version);
        alertDialog = new AlertDialog.Builder(this).create();

        //TODO  跳转至骑手订单页面
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DelivererActivity.this,SentCommodityActivity.class);
                startActivity(intent);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.setTitle("关于我们");
                alertDialog.setMessage("萤火团队出品");
                alertDialog.show();
            }
        });

        version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.setTitle("版本");
                alertDialog.setMessage("V 1.0");
                alertDialog.show();
            }
        });

    }

}