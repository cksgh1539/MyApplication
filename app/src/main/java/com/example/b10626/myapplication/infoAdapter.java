package com.example.b10626.myapplication;

import android.content.Context;
import android.net.Uri;
import android.util.SparseArray;
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

    static class ViewHolder {
        TextView date,content_name,ins_money,sub_money,total,ins_point,sub_point,point_total;
        TextView ins_money1,sub_money2,total3,ins_point4,sub_point5,point_total6;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder holder;
        holder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) fContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(fResource, parent,false);

            TextView info = convertView.findViewById(R.id.info);
            holder.date = convertView.findViewById(R.id.date);
            holder.content_name = convertView.findViewById(R.id.content_name0);

            holder.ins_money = convertView.findViewById(R.id.ins_money);
            holder.sub_money = convertView.findViewById(R.id.sub_money);
            holder.total = convertView.findViewById(R.id.total);
            holder.ins_point = convertView.findViewById(R.id.ins_point);
            holder.sub_point = convertView.findViewById(R.id.sub_point);
            holder.point_total = convertView.findViewById(R.id.point_total);

            holder.ins_money1 = convertView.findViewById(R.id.ins_money1);
            holder.sub_money2 = convertView.findViewById(R.id.sub_money2);
            holder.total3 = convertView.findViewById(R.id.total3);
            holder.ins_point4 = convertView.findViewById(R.id.ins_point4);
            holder.sub_point5 = convertView.findViewById(R.id.sub_point5);
            holder.point_total6 = convertView.findViewById(R.id.point_total6);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        try{
            holder.date.setText( fData.get(position).getData(0));
            holder.content_name.setText(fData.get(position).getData(1));

            if(fData.get(position).getData(2).equals("0")){
            //    holder.ins_money.setVisibility(convertView.GONE);
             //   holder.ins_money1.setVisibility(convertView.GONE);
            }else{
                holder. ins_money1.setText(fData.get(position).getData(2));
            }

            if(fData.get(position).getData(3).equals("0")){
            //    holder. sub_money.setVisibility(convertView.GONE);
            //    holder. sub_money2.setVisibility(convertView.GONE);
            }else{
                holder. sub_money2.setText(fData.get(position).getData(3));
            }

            holder. total3.setText(fData.get(position).getData(4));

            if(fData.get(position).getData(5).equals("0")){
            //    holder. ins_point.setVisibility(convertView.GONE);
           //     holder. ins_point4.setVisibility(convertView.GONE);
            }else{
                holder.ins_point4.setText(fData.get(position).getData(5));
            }

            if(fData.get(position).getData(6).equals("0")){
            //    holder. sub_point.setVisibility(convertView.GONE);
             //   holder. sub_point5.setVisibility(convertView.GONE);
            }else{
                holder. sub_point5.setText(fData.get(position).getData(6));
            }

            holder.point_total6.setText(fData.get(position).getData(7));

        /*    info.setText("날짜 :" + fData.get(position).getData(0) + "\n이름:" + fData.get(position).getData(1)
                    + "\n입금:" + fData.get(position).getData(2) + "\n출금:" + fData.get(position).getData(3)
                    + "\n잔액:" + fData.get(position).getData(4) + "\n포인트 적립:" + fData.get(position).getData(5)
                    + "\n포인트 사용:" + fData.get(position).getData(6) + "\n포인트 잔액:" + fData.get(position).getData(7));*/
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
