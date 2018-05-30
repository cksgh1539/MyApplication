package com.example.b10626.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by B10626 on 2018-05-21.
 */

public class Z_Organlist extends AppCompatActivity {
    Contents_Adapter adapter;
    ListView listView;
    int mCurCheckPosition = -1;
    String ID,PWD;
    //  Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organlist);

        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");
        PWD = intent.getStringExtra("password");

        //데이터 준비
        ArrayList<MyItem> data = new ArrayList<MyItem>();
        data.add(new MyItem(R.drawable.list_unicef, "UNICEF", "자세히 보기"));
        data.add(new MyItem(R.drawable.list_good, "Good Neighbors", "자세히 보기"));
        data.add(new MyItem(R.drawable.list_world, "World vision", "자세히 보기"));


        adapter = new Contents_Adapter(this, R.layout.test_list, data);

        //어댑터 연결
        listView = (ListView) findViewById(R.id.organlist);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              /*  switch (position){
                    case 0:
                        break;

                    case 1:
                        break;

                    case 2:
                        break;
                }*/
                Intent intent = new Intent(Z_Organlist.this,Z_Goodneighbors.class);
           /*     int image = ((MyItem)adapter.getItem(position)).mImage;
                String name = ((MyItem)adapter.getItem(position)).mName;
                String price = ((MyItem) adapter.getItem(position)).mPrice;
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),image);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();*/

                intent.putExtra("ID",ID);
                intent.putExtra("first_PWD",PWD);
                intent.putExtra("Position",position);

                startActivity(intent);
            }
        });

    }
}


