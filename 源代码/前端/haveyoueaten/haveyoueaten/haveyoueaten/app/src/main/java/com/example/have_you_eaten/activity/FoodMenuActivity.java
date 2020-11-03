package com.example.have_you_eaten.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.have_you_eaten.R;
import com.example.have_you_eaten.adapter.FoodMenuAdapter;
import com.example.have_you_eaten.bean.FoodMenu;
import java.util.ArrayList;
import java.util.List;

/**
 * 选餐界面
 */
public class FoodMenuActivity extends AppCompatActivity implements View.OnClickListener {
    List<FoodMenu> menus = new ArrayList<>();
    Button food_menu_confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_menu);
        init();
        FoodMenuAdapter adapter = new FoodMenuAdapter(FoodMenuActivity.this,R.layout.order_menu_item,menus);
        ListView listView =(ListView) findViewById(R.id.foodmenu);
        listView.setAdapter(adapter);

    }

public void init(){
    food_menu_confirm=findViewById(R.id.food_menu_confirm);
    food_menu_confirm.setOnClickListener(this);
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
}
