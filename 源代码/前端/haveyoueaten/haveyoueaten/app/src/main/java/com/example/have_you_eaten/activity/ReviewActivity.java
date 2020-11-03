package com.example.have_you_eaten.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.have_you_eaten.R;

/**
 * 评价界面，对应product_reviews.xml
 * TODO 待定界面，功能基本未完成
 */
class ReviewActivity extends AppCompatActivity implements View.OnClickListener {

    //初始化
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_reviews);
    }
    
    @Override
    public void onClick(View v) {
        
    }
}
