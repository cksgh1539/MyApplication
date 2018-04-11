package com.example.b10626.myapplication;

/**
 * Created by hp on 2018-04-03.
 */

public class userInfoItem {

    private String[] mData;

    public userInfoItem(String[] data ){

        mData = data;
}

    public userInfoItem(String uid, String date,String name,String ins, String deposit, String total){

        mData = new String[6];
        mData[0] = uid;
        mData[1] = date;
        mData[2] = name;
        mData[3] = ins;
        mData[4] = deposit;
        mData[5] = total;

    }

    public String[] getData(){
        return mData;
    }

    public String getData(int index){
        return mData[index];
    }

    public void setData(String[] data){
        mData = data;
    }

}

