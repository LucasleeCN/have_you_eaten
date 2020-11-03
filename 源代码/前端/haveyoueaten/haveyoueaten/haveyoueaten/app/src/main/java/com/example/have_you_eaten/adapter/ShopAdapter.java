package com.example.have_you_eaten.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.have_you_eaten.R;
import com.example.have_you_eaten.bean.Shop;
import java.util.List;

/**
 *店铺列表adapter，包含店铺名和店铺地址
 *
 */
public class ShopAdapter extends ArrayAdapter<Shop> {
    List<Shop> shopList ;
    public ShopAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<Shop> shopList) {
        super(context, textViewResourceId, shopList);
        this.shopList = shopList;
    }

    @Override
    public int getCount() {
        return shopList.size();
    }

    @Override
    public int getPosition(@Nullable Shop item) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ShopViewHolder viewHolder ;
        View v;
        if(convertView==null){
            v= LayoutInflater.from(getContext()).inflate(R.layout.menu_item,parent,false);
            viewHolder=new ShopViewHolder();
            viewHolder.name=v.findViewById(R.id.name);
            viewHolder.address.findViewById(R.id.address);
            v.setTag(viewHolder);
        }
        else {
            v=convertView;
            viewHolder= (ShopViewHolder) v.getTag();
        }
        Shop shop = shopList.get(position);
        viewHolder.name.setText(shop.getName());
        viewHolder.address.setText(shop.getAddress());
        return v;
    }
}
