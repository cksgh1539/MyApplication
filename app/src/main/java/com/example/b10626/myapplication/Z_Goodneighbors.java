package com.example.b10626.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by B10626 on 2018-05-21.
 */

public class Z_Goodneighbors extends AppCompatActivity {
    Contents_Adapter adapter;
    ListView listView;
    int mCurCheckPosition = 0;
    String ID,PWD;
    Z_PagerViewAdapter Pageadapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contents_layout);

        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");
        PWD = intent.getStringExtra("first_PWD");
        mCurCheckPosition = intent.getIntExtra("Position",0);

        viewPager = (ViewPager)findViewById(R.id.view);
        Pageadapter = new Z_PagerViewAdapter(this,mCurCheckPosition+1);
        viewPager.setAdapter(Pageadapter);

        //데이터 준비
        ArrayList<MyItem> data = new ArrayList<MyItem>();
        if(mCurCheckPosition == 0) {
            data.add(new MyItem(R.drawable.item1_1, "국제보건사업", "보기"));
            data.add(new MyItem(R.drawable.item1_22, "국제에이즈예방사업", "보기"));
            data.add(new MyItem(R.drawable.item1_33, "국제식수사업", "보기"));
        }else if(mCurCheckPosition == 1){
            data.add(new MyItem(R.drawable.item3_1, "국내아동지원", "보기"));
            data.add(new MyItem(R.drawable.item3_2, "북한아동지원사업", "보기"));
            data.add(new MyItem(R.drawable.item3_3, "해외아동교육지원", "보기"));
        }else if(mCurCheckPosition == 2){
            data.add(new MyItem(R.drawable.item2_1, "국내위기가정아동지원", "보기"));
            data.add(new MyItem(R.drawable.item2_2, "대북농업사업", "보기"));
            data.add(new MyItem(R.drawable.item2_3, "국제식량위기대응사업", "보기"));
        }


        adapter = new Contents_Adapter(this, R.layout.contents_list, data);

        //어댑터 연결
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Z_Goodneighbors.this,content_detail.class);



                int image = ((MyItem)adapter.getItem(position)).mImage;
                String name = ((MyItem)adapter.getItem(position)).mName;
                String price = ((MyItem) adapter.getItem(position)).mPrice;
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),image);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                intent.putExtra("content_image",byteArray);
                intent.putExtra("content_name",name);
                intent.putExtra("content_position",position);
                intent.putExtra("organ_position",mCurCheckPosition);
                intent.putExtra("ID",ID);
                intent.putExtra("first_PWD",PWD);

                startActivity(intent);
            }
        });

    }
}