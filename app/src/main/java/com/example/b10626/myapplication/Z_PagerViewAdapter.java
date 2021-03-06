package com.example.b10626.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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



    private int[] images0 = {R.drawable.uni3,R.drawable.good1,R.drawable.wor2};
    private int[] images1 = {R.drawable.unicef5,R.drawable.uni1,R.drawable.uni2};
    private int[] images2 = {R.drawable.goodneighbors5,R.drawable.good2,R.drawable.good4};
    private int[] images3 = {R.drawable.wor1,R.drawable.wor3,R.drawable.wor2};

    //private Bitmap[] login_image;

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
                imageNum = images0.length;
                break;
            case 1:
                imageNum=images1.length;
                break;
            case 2:
                imageNum= images2.length;
                break;
            case 3:
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

        BitmapFactory.Options options =  new BitmapFactory.Options();
        options.inSampleSize = 2;

        Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(),R.drawable.uni3,options);
        Bitmap bitmap2 = BitmapFactory.decodeResource(context.getResources(),R.drawable.good1,options);
        Bitmap bitmap3 = BitmapFactory.decodeResource(context.getResources(),R.drawable.wor2,options);

      /*  Bitmap bitmap4 = BitmapFactory.decodeResource(context.getResources(),R.drawable.unicef5,options);
        Bitmap bitmap5 = BitmapFactory.decodeResource(context.getResources(),R.drawable.uni1,options);
        Bitmap bitmap6 = BitmapFactory.decodeResource(context.getResources(),R.drawable.uni2,options);*/

        Bitmap login_image[] = {bitmap1, bitmap2,bitmap3};
      //  Bitmap login_image2[] = {bitmap4, bitmap5,bitmap6};


        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.viewpager, container, false);
        ImageView imageView = (ImageView)v.findViewById(R.id.pagerImage);

        if (OrganNum == 0){
            imageView.setImageResource(images0[position]);
        }else if(OrganNum == 1){
            imageView.setImageResource(images1[position]);
        }else if(OrganNum == 2){
            imageView.setImageResource(images2[position]);
        }else if(OrganNum == 3){
            imageView.setImageResource(images3[position]);
        }

        container.addView(v);
        return v;
    }

    public void destroyItem(ViewGroup container, int position, Object object){


        container.invalidate();
        System.gc();
    }




}
