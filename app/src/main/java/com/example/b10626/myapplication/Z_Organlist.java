package com.example.b10626.myapplication;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by B10626 on 2018-05-21.
 */

public class Z_Organlist extends AppCompatActivity {
    Contents_Adapter adapter;
    ListView listView;
    Bitmap_decode bitmap_decode;
    int mCurCheckPosition = -1;
    String ID,PWD;
    //  Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organlist);

        bitmap_decode = new Bitmap_decode();

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.etc_titlebar);
        TextView titlebar =(TextView)findViewById(R.id.etc_titlebar_text);
        titlebar.setText("기부 단체 리스트");

        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");
        PWD = intent.getStringExtra("password");

        //데이터 준비
        ArrayList<MyItem> data = new ArrayList<MyItem>();
        data.add(new MyItem(bitmap_decode.decodeSampledBitmapFromResource(getResources(),R.drawable.list_unicef1,100,100), "UNICEF", "자세히"));
        data.add(new MyItem(bitmap_decode.decodeSampledBitmapFromResource(getResources(),R.drawable.list_good1,100,100), "Good", "자세히"));
        data.add(new MyItem(bitmap_decode.decodeSampledBitmapFromResource(getResources(),R.drawable.list_world1,100,100), "World", "자세히"));


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

    public void back(View view){
        Intent intent = new Intent(Z_Organlist.this,Login_menu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //해당 액티비티 위에 스택 삭제
        intent.putExtra("ID",ID);
        intent.putExtra("password",PWD);
        startActivity(intent);
    }
}


