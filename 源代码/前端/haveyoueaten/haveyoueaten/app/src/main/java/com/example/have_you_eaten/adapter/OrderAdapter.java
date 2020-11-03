package com.example.have_you_eaten.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.have_you_eaten.R;
import com.example.have_you_eaten.bean.Order;
import java.util.List;

/**
 * 订单适配器adapter,每一个item包含商品名和商家名，即一个订单包含商品名和商家名
 */
public class OrderAdapter extends ArrayAdapter<Order> {
    //订单列表
    private List<Order> orderList;
    public OrderAdapter(Context context, int textViewResourceId, List<Order> orderList){
        super(context,  textViewResourceId, orderList);
        this.orderList=orderList;
    }

    @Override
    public int getCount(){
        return orderList.size();
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    /**
     * 更新view
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        OrderViewHolder viewHolder;
        View v;
        if(convertView==null){
        v= LayoutInflater.from(getContext()).inflate(R.layout.order_items,parent,false);
        viewHolder=new OrderViewHolder();
       viewHolder.producer=v.findViewById(R.id.producer);
       viewHolder.commodity=v.findViewById(R.id.commodity);
        v.setTag(viewHolder);
    }
        else {
        v=convertView;
        viewHolder= (OrderViewHolder) v.getTag();
    }
         Order order = orderList.get(position);
        viewHolder.commodity.setText(order.getCommodity());
        viewHolder.producer.setText(order.getProducer());
        return v;
}
}
