package com.example.b10626.myapplication;

/**
 * Created by hp on 2018-04-03.
 */

public class userInfoItem {

    private String[] mData;

    public userInfoItem(String[] data ){

        mData = data;
}

    public userInfoItem(String date ,String name, String money, String total){

        mData = new String[4];
        mData[0] = date;
      //  mData[1] = uid;
        mData[1] = name;
       // mData[3] = ins;
        mData[2] = money;
        mData[3] = total;

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

