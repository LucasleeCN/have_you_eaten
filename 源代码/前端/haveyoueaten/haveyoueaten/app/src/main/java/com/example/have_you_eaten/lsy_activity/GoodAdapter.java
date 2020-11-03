package com.example.have_you_eaten.lsy_activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.have_you_eaten.R;
import com.example.have_you_eaten.lsy_fakeActivity.GoodDetailActivity;
import com.example.have_you_eaten.lsy_modle.Good;
import com.example.have_you_eaten.lsy_modle.Order;

import java.util.List;

public class GoodAdapter extends RecyclerView.Adapter<GoodAdapter.GoodViewHolder> {
    private Context context;
    private List<Good> goods;
    private Order order;

    public GoodAdapter(Context context, List<Good> goods, Order order) {
        this.context = context;
        this.goods = goods;
        this.order = order;
    }

    @NonNull
    @Override
    public GoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.lsy_item_good, null);
        return new GoodViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GoodViewHolder holder, int position) {
        Good good = goods.get(position);
        holder.name.setText(good.getName());
        holder.info.setText(good.getInfo());
        holder.price.setText(String.valueOf(good.getPrice()));
    }

    @Override
    public int getItemCount() {
        return goods.size();
    }


    class GoodViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name;
        private TextView info;
        private TextView price;

        private Button add;
        private TextView num;
        private Button sub;

        public GoodViewHolder(@NonNull View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.image);
            name = (TextView) itemView.findViewById(R.id.name);
            info = (TextView) itemView.findViewById(R.id.info);
            price = (TextView) itemView.findViewById(R.id.price);

            add = (Button) itemView.findViewById(R.id.add);
            num = (TextView) itemView.findViewById(R.id.num);
            sub = (Button) itemView.findViewById(R.id.sub);

            //TODO item被点击跳转到详情页
            //已取消，使用假跳转
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Good good = goods.get(getLayoutPosition());
//                    Toast.makeText(context, "跳转到 " + good.getName() + " 详情页", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, GoodDetailActivity.class));
                }
            });

            //+1按钮onClick
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Good good = goods.get(getLayoutPosition());
                    int i = Integer.parseInt(num.getText().toString());
                    i += 1;
                    num.setText(String.valueOf(i));
                    order.buyList.put(good, i);
                    if (myCallBackInAd != null) {
                        myCallBackInAd.DoCallBack(114514);
                    }
                }
            });

            //-1按钮onClick
            sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Good good = goods.get(getLayoutPosition());
                    int i = Integer.parseInt(num.getText().toString());
                    if (i > 0) {
                        i -= 1;
                        num.setText(String.valueOf(i));
                        order.buyList.put(good, i);
                        if (myCallBackInAd != null) {
                            myCallBackInAd.DoCallBack(114514);
                        }
                        if (i == 0) {
                            //从购物车中移除该商品
                            order.buyList.remove(good);
                        }
                    } else {
                        Toast.makeText(context, good.getName() + "不能少于0", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    //回调接口
    public interface MyCallBack {
        public void DoCallBack(int i);
    }

    private MyCallBack myCallBackInAd;

    public void setMyCallBackInAd(MyCallBack myCallBack) {
        this.myCallBackInAd = myCallBack;
    }

}
