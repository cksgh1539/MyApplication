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

public class PagerViewAdapter extends PagerAdapter {

    private int[] images = {R.drawable.content1,R.drawable.content2,R.drawable.content3};
    private LayoutInflater inflater;
    private Context context;

    public PagerViewAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    public Object instantiateItem(ViewGroup container, int position){
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.viewpager, container, false);
        ImageView imageView = (ImageView)v.findViewById(R.id.pagerImage);
        imageView.setImageResource(images[position]);
        container.addView(v);
        return v;
    }

    public void destroyItem(ViewGroup container, int position, Object object){
        container.invalidate();
    }
}
