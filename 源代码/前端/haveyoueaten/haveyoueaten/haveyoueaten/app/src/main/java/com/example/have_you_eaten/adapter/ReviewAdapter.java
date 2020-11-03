package com.example.have_you_eaten.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.have_you_eaten.R;
import com.example.have_you_eaten.bean.Review;
import java.util.List;


/**
 * 评价的适配器adapter
 */
public class ReviewAdapter extends ArrayAdapter<Review> {
    //初始化List
    private List<Review> reviewList;
    public ReviewAdapter(Context context,  int textViewResourceId, List<Review> reviewList ){
        super(context, textViewResourceId, reviewList);
        this.reviewList=reviewList;
    }

    /**
     * 返回reviewList长度
     * @return
     */
    @Override
    public int getCount(){
        return reviewList.size();
    }


    @Override
    public long getItemId(int position){
        return 0;
    }

    /**
     * 更新adapter
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        ReviewViewHolder viewHolder;
        View v;
        if(convertView==null){
            v= LayoutInflater.from(getContext()).inflate(R.layout.review_item,parent,false);
            viewHolder=new ReviewViewHolder();
            viewHolder.name=v.findViewById(R.id.name);
            viewHolder.note.findViewById(R.id.note);
            v.setTag(viewHolder);
        }
        else {
            v=convertView;
            viewHolder= (ReviewViewHolder) v.getTag();
        }
        Review review = reviewList.get(position);
        viewHolder.name.setText(review.getNote());
        viewHolder.note.setText(review.getNote());
        return v;
    }
}
