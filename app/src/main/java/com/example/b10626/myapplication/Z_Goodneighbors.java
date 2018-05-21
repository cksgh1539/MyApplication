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
    int mCurCheckPosition = -1;
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
        Pageadapter = new Z_PagerViewAdapter(this,mCurCheckPosition);
        viewPager.setAdapter(Pageadapter);

        //데이터 준비
        ArrayList<MyItem> data = new ArrayList<MyItem>();
        data.add(new MyItem(R.drawable.content1, "난민", "보기"));
        data.add(new MyItem(R.drawable.content2, "아동", "보기"));
        data.add(new MyItem(R.drawable.content3, "에이즈", "보기"));


        adapter = new Contents_Adapter(this, R.layout.contests_list, data);

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
                intent.putExtra("ID",ID);
                intent.putExtra("first_PWD",PWD);

                startActivity(intent);
            }
        });

    }
}