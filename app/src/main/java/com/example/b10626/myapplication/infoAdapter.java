package com.example.b10626.myapplication;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hp on 2018-04-04.
 */

public class infoAdapter extends BaseAdapter {
    private ArrayList<userInfoItem> fData;
    private Context fContext;
    private int fResource;
    private int count;

    public infoAdapter(ArrayList<userInfoItem> data, Context context, int resource) {
        fData = data;
        fContext = context;
        fResource = resource;
        this.count = fData.size();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) fContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(fResource, parent,false);
        }


        TextView info = convertView.findViewById(R.id.info);
        try{
            info.setText("date :" + fData.get(position).getData(0) + "\nname:" + fData.get(position).getData(1)
                    + "\nmoney:" + fData.get(position).getData(2) + "\ntotal:" + fData.get(position).getData(3));
        }catch (IndexOutOfBoundsException ioe){

        }

        return convertView;

    }
    @Override
    public int getCount() {
       // return fData.size();
        return count;
    }

    @Override
    public Object getItem(int position) {
        return fData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



}
