package com.example.have_you_eaten.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.have_you_eaten.R;
import com.example.have_you_eaten.bean.Commodity;
import java.util.ArrayList;
import java.util.List;

/**
 * 商家管理界面的adapter
 */
public class CommodityInManagerAdapter extends ArrayAdapter<Commodity> {
    //List存储Commodity类，然后封装
    List<Commodity> commodityList = new ArrayList<>();
    public CommodityInManagerAdapter(@NonNull Context context,  int textViewResourceId, @NonNull List<Commodity> commodityList) {
        super(context, textViewResourceId, commodityList);
        this.commodityList=commodityList;
    }
    @Override
    public int getCount(){
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
       CommodityInManagerViewHolder viewHolder;
        View v;
        if(convertView==null){
            v= LayoutInflater.from(getContext()).inflate(R.layout.manage_commodity_item,parent,false);
            viewHolder=new CommodityInManagerViewHolder();
            viewHolder.name=v.findViewById(R.id.name);
            viewHolder.price=v.findViewById(R.id.price);
            viewHolder.number=v.findViewById(R.id.number);
            v.setTag(viewHolder);
        }
        else {
            v=convertView;
            viewHolder= (CommodityInManagerViewHolder) v.getTag();
        }
        Commodity commodity = commodityList.get(position);
        viewHolder.name.setText(commodity.getName());
        viewHolder.price.setText(""+commodity.getPrice());
        viewHolder.number.setText(""+commodity.getNumber());
        return v;
    }
}
