package com.example.b10626.myapplication;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by hp on 2018-05-21.
 */

public class Z_PagerViewAdapter extends PagerAdapter {

    private int[] images1 = {R.drawable.unicef,R.drawable.goodneighbors,R.drawable.world_vision_logo};
    private int[] images2 = {R.drawable.goodneighbors,R.drawable.world_vision_logo,R.drawable.unicef};
    private int[] images3 = {R.drawable.world_vision_logo,R.drawable.unicef,R.drawable.goodneighbors};
    private LayoutInflater inflater;
    private Context context;
    private int OrganNum;

    public Z_PagerViewAdapter(Context context, int num){
        this.context = context;
        OrganNum = num;
    }

    @Override
    public int getCount() {
        int imageNum=0;
        switch (OrganNum){
            case 0:
                imageNum = images1.length;
                break;
            case 1:
                imageNum=images2.length;
                break;
            case 2:
                imageNum= images3.length;
                break;
        }
        return imageNum;

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    public Object instantiateItem(ViewGroup container, int position){
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.viewpager, container, false);
        ImageView imageView = (ImageView)v.findViewById(R.id.pagerImage);
        if (OrganNum == 0){
            imageView.setImageResource(images1[position]);
        }else if(OrganNum == 1){
            imageView.setImageResource(images2[position]);
        }else if(OrganNum == 2){
            imageView.setImageResource(images3[position]);
        }
        container.addView(v);
        return v;
    }

    public void destroyItem(ViewGroup container, int position, Object object){
        container.invalidate();
    }
}
