package com.example.have_you_eaten.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.have_you_eaten.R;
import com.example.have_you_eaten.bean.FoodMenu;
import java.util.List;

/**
 * TODO 有待重构  增加viewHolder  后期没时间不修改也行
 */
public class FoodMenuAdapter extends ArrayAdapter<FoodMenu> {
    private int resourceId;
    public FoodMenuAdapter(Context context, int textViewResourceId , List<FoodMenu> object){
        super(context,textViewResourceId,object);
        resourceId=textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView , ViewGroup parent){
        final FoodMenu menu = getItem(position);

        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        final TextView textView=view.findViewById(R.id.foodnum);
        final ImageButton btn = (ImageButton) view.findViewById(R.id.ib_1);
        final ImageButton btn2 = (ImageButton) view.findViewById(R.id.IB_minus);
        btn.setTag(position);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Integer count = Integer.parseInt(textView.getText().toString());
                count++;
                textView.setText(count.toString());
                Toast.makeText(getContext(),menu.getName()+" is selected",Toast.LENGTH_SHORT).show();
            }
        });
        btn2.setTag(position);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Integer count = Integer.parseInt(textView.getText().toString());
                if(count==0){
                    Toast.makeText(getContext(), "there is no "+menu.getName(), Toast.LENGTH_SHORT).show();
                }else {
                    count--;
                    textView.setText(count.toString());
                    Toast.makeText(getContext(), menu.getName() + " is deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ImageView MenuImage = (ImageView) view.findViewById(R.id.iv_1);
        TextView MenuName = (TextView) view.findViewById(R.id.tvname);
        MenuImage.setImageResource(menu.getImageId());
        MenuName.setText(menu.getName());
        return view;
    }
}
