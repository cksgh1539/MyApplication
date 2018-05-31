package com.example.b10626.myapplication;

import android.app.ActionBar;
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
import android.widget.TextView;

import com.rd.PageIndicatorView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by B10626 on 2018-05-21.
 */

public class Z_Goodneighbors extends AppCompatActivity {
    Contents_Adapter adapter;
    ListView listView;
    Bitmap_decode bitmap_decode;
    int mCurCheckPosition = 0;
    String ID,PWD;
    Z_PagerViewAdapter Pageadapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contents_layout);

        bitmap_decode = new Bitmap_decode();

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.etc_titlebar);
        TextView titlebar =(TextView)findViewById(R.id.etc_titlebar_text);
    //    titlebar.setText("기부 단체 리스트");

        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");
        PWD = intent.getStringExtra("first_PWD");
        mCurCheckPosition = intent.getIntExtra("Position",0);

        viewPager = (ViewPager)findViewById(R.id.view);
        Pageadapter = new Z_PagerViewAdapter(this,mCurCheckPosition+1);
        viewPager.setAdapter(Pageadapter);

        final PageIndicatorView pageIndicatorView = findViewById(R.id.pageIndicatorView);
        pageIndicatorView.setCount(3); // specify total count of indicators
        pageIndicatorView.setSelection(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageIndicatorView.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        //데이터 준비
        ArrayList<MyItem> data = new ArrayList<MyItem>();
        if(mCurCheckPosition == 0) {
            titlebar.setText("Unicef");

            data.add(new MyItem(bitmap_decode.decodeSampledBitmapFromResource(getResources(),R.drawable.item1_1,100,100), "국제보건사업", "보기"));
            data.add(new MyItem(bitmap_decode.decodeSampledBitmapFromResource(getResources(),R.drawable.item1_22,100,100), "국제에이즈예방사업", "보기"));
            data.add(new MyItem(bitmap_decode.decodeSampledBitmapFromResource(getResources(),R.drawable.item1_33,100,100), "국제식수사업", "보기"));
        }else if(mCurCheckPosition == 1){
           titlebar.setText("GoodNeighbors");
            data.add(new MyItem(bitmap_decode.decodeSampledBitmapFromResource(getResources(),R.drawable.item3_1,100,100), "국내아동지원", "보기"));
            data.add(new MyItem(bitmap_decode.decodeSampledBitmapFromResource(getResources(),R.drawable.item3_2,100,100), "북한아동지원사업", "보기"));
            data.add(new MyItem(bitmap_decode.decodeSampledBitmapFromResource(getResources(),R.drawable.item3_3,100,100), "해외아동교육지원", "보기"));
        }else if(mCurCheckPosition == 2){
            titlebar.setText("World Vision");
            data.add(new MyItem(bitmap_decode.decodeSampledBitmapFromResource(getResources(),R.drawable.item2_1,100,100), "국내위기가정아동지원", "보기"));
            data.add(new MyItem(bitmap_decode.decodeSampledBitmapFromResource(getResources(),R.drawable.item2_2,100,100), "대북농업사업", "보기"));
            data.add(new MyItem(bitmap_decode.decodeSampledBitmapFromResource(getResources(),R.drawable.item2_3,100,100), "국제식량위기대응사업", "보기"));
        }


        adapter = new Contents_Adapter(this, R.layout.contents_list, data);

        //어댑터 연결
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Z_Goodneighbors.this,content_detail.class);



                Bitmap image = ((MyItem)adapter.getItem(position)).mImage;
                String name = ((MyItem)adapter.getItem(position)).mName;
                String price = ((MyItem) adapter.getItem(position)).mPrice;
              //  Bitmap bitmap = BitmapFactory.decodeResource(getResources(),image);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
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

    public void back(View view){
        Intent intent = new Intent(Z_Goodneighbors.this,Z_Organlist.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //해당 액티비티 위에 스택 삭제
        intent.putExtra("ID",ID);
        intent.putExtra("password",PWD);
        startActivity(intent);
    }
}