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
 * 送餐界面的adapter，每一个item包含time&address两个信息
 */
public class SentCommodityAdapter extends ArrayAdapter<Order> {

    private static final String TAG = "SentCommodityAdapter";
    List<Order> commodityList ;

    public SentCommodityAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<Order> commodityList) {
        super(context,  textViewResourceId, commodityList);
        this.commodityList=commodityList;
    }

    @Override
    public int getCount(){
//        Log.d(TAG, "getCount: "+commodityList.size());
        return commodityList.size();
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
//        Log.d(TAG, "getView() returned: " +"run here" );
        SentCommodityViewHolder viewHolder;
        View v;
        if(convertView==null){
            v= LayoutInflater.from(getContext()).inflate(R.layout.mission_item,parent,false);
            viewHolder=new SentCommodityViewHolder();
            viewHolder.time=v.findViewById(R.id.time);
            viewHolder.address=v.findViewById(R.id.address);
            viewHolder.shopName=v.findViewById(R.id.shopName);
            v.setTag(viewHolder);
        }
        else {
            v=convertView;
            viewHolder= (SentCommodityViewHolder) v.getTag();
        }
        Order order = commodityList.get(position);
       // Log.d(TAG, "getView: "+commodityList.get(position).getProducer().toString());
        viewHolder.time.setText(order.getTime());
        viewHolder.address.setText(order.getAddress());
        viewHolder.shopName.setText(order.getProducer());
        //Log.d(TAG, "getView: "+viewHolder.shopName.getText().toString());
        return v;
    }
}
