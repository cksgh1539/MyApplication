package com.example.b10626.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hp on 2018-05-02.
 */

public class Contents_Adapter  extends BaseAdapter {
    private Context mContext;
    private int mResource;
    private ArrayList<MyItem> mItems = new ArrayList<MyItem>();

    public Contents_Adapter(Context context, int resource, ArrayList<MyItem> items) {
        mContext = context;
        mItems = items;
        mResource = resource;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent,false);
        }
        // Set Icon
        ImageView icon = (ImageView) convertView.findViewById(R.id.Contents_Image);
        icon.setImageResource(mItems.get(position).mImage);

       /* // Set Text 01
        TextView name = (TextView) convertView.findViewById(R.id.Contents_Name);
        name.setText(mItems.get(position).mName);

        // Set Text 02
        TextView age = (TextView) convertView.findViewById(R.id.Contents_Price);
        age.setText(mItems.get(position).mPrice);*/

        return convertView;
    }
}

class MyItem {
    int mImage; // image resource
    String mName; // text
    String mPrice;  // text

    MyItem(int aImage, String aName, String aPrice) {
        mImage = aImage;
        mName = aName;
        mPrice = aPrice;
    }
}