package com.example.have_you_eaten.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.have_you_eaten.R;

/**
 * 主界面，用于引导用户
 */
public class MainActivity extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences SpUtils = this.getSharedPreferences("mypref", 0);
        boolean isFirstOpen = SpUtils.getBoolean("FIRST", true);
        if (isFirstOpen) {
            //第一次启动则 跳转引导界面
            SpUtils.edit().putBoolean("FIRST", false).commit();
            setContentView(R.layout.activity_main);
            button = (Button) findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            //如果是第二次启动 跳转另外界面
            Intent intent2 = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent2);
        }
    }
}
