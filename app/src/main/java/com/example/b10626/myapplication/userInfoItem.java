package com.example.b10626.myapplication;

/**
 * Created by hp on 2018-04-03.
 */

public class userInfoItem {

    private String[] mData;

    public userInfoItem(String[] data ){

        mData = data;
}

    public userInfoItem(String date ,String name, String deposit,String Sub, String total){

        mData = new String[5];
        mData[0] = date;
        mData[1] = name;
        mData[2] = deposit;
        mData[3] = Sub;
        mData[4] = total;


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

