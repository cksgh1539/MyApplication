package com.example.b10626.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by hp on 2018-05-01.
 */

public class Contents_list extends AppCompatActivity{
    Contents_Adapter adapter;
    ListView listView;
    int mCurCheckPosition = -1;
    String ID,PWD;
  //  Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contents_layout);

        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");
        PWD = intent.getStringExtra("password");

        //데이터 준비
        ArrayList<MyItem> data = new ArrayList<MyItem>();
        data.add(new MyItem(R.drawable.content1, "하루 식사", "1000"));
        data.add(new MyItem(R.drawable.content2, "일주일 식사", "2000"));
        data.add(new MyItem(R.drawable.content3, "한달 식사", "5000"));


        adapter = new Contents_Adapter(this, R.layout.contests_list, data);

        //어댑터 연결
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(Contents_list.this,content_detail.class);



        int image = ((MyItem)adapter.getItem(position)).mImage;
        String name = ((MyItem)adapter.getItem(position)).mName;
        String price = ((MyItem) adapter.getItem(position)).mPrice;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),image);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        intent.putExtra("content_image",byteArray);
        intent.putExtra("content_name",name);
        intent.putExtra("content_price",price);
        intent.putExtra("content_position",position);
        intent.putExtra("ID",ID);
        intent.putExtra("first_PWD",PWD);

        startActivity(intent);
    }
});

    }
}
