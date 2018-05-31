package com.example.b10626.myapplication;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rd.PageIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2018-04-09.
 */

public class Login_menu extends AppCompatActivity {
    TextView user_total,user_name,user_point;
    ImageView refreshbtn;
    AlertDialog.Builder alertDialog;
    String ID,PWD;
    Bitmap_decode bitmap_decode;

    Z_PagerViewAdapter adapter;
    private ViewPager viewPager;

    int deposit_total = 0;
    int minus_total = 0;
    int point_total = 0;
    String total="0";
    php task;
    SwipeRefreshLayout SRlayout;
    private final double finish_interval_time=2000;
    private double backPressedTime =0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_menu);

        bitmap_decode = new Bitmap_decode();

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.login_menu_titlebar);
        TextView titlebar =(TextView)findViewById(R.id.titlebar_text);
        refreshbtn = (ImageView) findViewById(R.id.refresh_button);
        refreshbtn.setImageBitmap(bitmap_decode.decodeSampledBitmapFromResource(getResources(),R.drawable.app_refresh_icon,100,100));
        titlebar.setText("Drop the Bit");
        Log.v("chanho","타이틀바");

        final PageIndicatorView pageIndicatorView = findViewById(R.id.pageIndicatorView);
        pageIndicatorView.setCount(3); // specify total count of indicators
        pageIndicatorView.setSelection(0);
        Log.v("chanho","인디케이터");

        viewPager = (ViewPager)findViewById(R.id.view);
        adapter = new Z_PagerViewAdapter(this,0);
        viewPager.setAdapter(adapter);
        Log.v("chanho","뷰페이저");
        //viewPager.setOffscreenPageLimit(3);

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


        user_total = (TextView)findViewById(R.id.user_total);
        user_name = (TextView)findViewById(R.id.user_name);
        user_point = (TextView)findViewById(R.id.user_point);
        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");
        PWD = intent.getStringExtra("password");
        Log.v("chanho","ID받음 "+ ID + PWD);
        task = new php();
        Log.v("chanho","php 생성");

        String URL = "http://113.198.80.147/login_menu.php";
        task.execute(URL,ID,PWD);
        Log.v("chanho","task 실행");

      //SRlayout = (SwipeRefreshLayout) findViewById(R.id.SRlayout);

        /*  SRlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                task = new php();
                task.execute("http://113.198.80.147/login_menu.php",ID,PWD);
                SRlayout.setRefreshing(false);
            }
        });*/
    }



    private class php extends AsyncTask<String, Void, String> { //---------------로그인 할 때 받은 아이디 , 패스워드 php에서 비교

        @Override
        protected String doInBackground(String... urls) {

            try {
                Log.v("chanho","try중");
                String user_id = urls[1];
                String user_password = urls[2];
                URL url = new URL(urls[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_id,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(user_password,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                String result = "";
                String line;

                while((line = bufferedReader.readLine())!=null){
                    result += line;
                }
                bufferedReader.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String str) { //-----------서버에서 총 금액 및 이름 받기

            Log.v("chanho","postExecute");
            String name="";
            String deposit="";
            String minus="";
            String point="";
            String ex_total="";
            Log.v("chanho","str"+str);

            try{
                Log.v("chanho","post try시작");
                JSONObject root = new JSONObject(String.valueOf(str));
                Log.v("chanho","root"+root);
                JSONArray ja = root.getJSONArray("results");
                Log.v("chanho","array"+ja);
                for(int i=0; i<ja.length(); i++){
                    JSONObject jo = ja.getJSONObject(i);
                    total = jo.getString("total");
                    name = jo.getString("name");
                    deposit = jo.getString("deposit");
                    minus = jo.getString("minus");
                    point = jo.getString("ins_point"); //적립포인트
                    ex_total = jo.getString("point_total"); //남은 포인트

                    minus_total += Integer.parseInt(minus);
                    deposit_total += Integer.parseInt(deposit);
                    point_total += Integer.parseInt(point); // 누적 포인트
                    Log.v("chanho",total+name+deposit+minus+point+ex_total);
                }

            }catch(JSONException e){
                Log.v("chanho","오류");
                e.printStackTrace();
            }

            user_name.setText("안녕하세요!! " +name+"님 :D");
            user_total.setText(total +" 원");
            user_point.setText(ex_total + " 점"); //남은 포인트
        }
    }
    public void refresh(View view){
                task = new php();
                task.execute("http://113.198.80.147/login_menu.php",ID,PWD);
                  Toast.makeText(getApplicationContext(), "새로 고침 중 입니다!", Toast.LENGTH_SHORT).show();
    }

    public void trade_detail(View view) { //------------------상세 거래내역
        String username = ID;
        String password = PWD;
   //     String type = "trade_detail";

        Intent intent = new Intent(this,UserData.class);
        intent.putExtra("ID", username);
        intent.putExtra("password",password);
        intent.putExtra("point_total",point_total);
        intent.putExtra("deposit_total",deposit_total);
        intent.putExtra("minus_total",minus_total);
        startActivity(intent);


     //    BackgroundWork backgroundWork = new BackgroundWork((this));
     //   backgroundWork.execute(type, username, password);

    }
    public void Contents_list(View view) { //------------------------그래프
        String username = ID;
        String password = PWD;
        // String type = "trade_detail";

        Intent intent = new Intent(this,Z_Organlist.class);
        intent.putExtra("ID", username);
        intent.putExtra("password",password);
        startActivity(intent);

    }

    /*public void graph(View view) { //------------------------그래프
        String username = ID;
        String password = PWD;
       // String type = "trade_detail";

        Intent intent = new Intent(this,user_chart.class);
        intent.putExtra("ID", username);
        intent.putExtra("password",password);
        intent.putExtra("point_total",point_total);
        intent.putExtra("deposit_total",deposit_total);
        intent.putExtra("minus_total",minus_total);
        startActivity(intent);

    }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //------------------------------로그아웃 옵션메뉴
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_logout:
                alertDialog = new AlertDialog.Builder(this);
                alertDialog.setMessage("로그아웃을 하시겠습니까??");
                alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //해당 액티비티 위에 스택 삭제
                        startActivity(intent);
                    }
                });
                alertDialog.setNegativeButton("취소",null);
                alertDialog.show();
                // return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() { //---------------------뒤로가기 앱 종료
        double tempTime = System.currentTimeMillis();
        double intervaleTime = tempTime - backPressedTime;

        if(0 <= intervaleTime && finish_interval_time >= intervaleTime){
            super.onBackPressed();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

           // finish();
        }else{
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한번 더 누르시면 로그인 화면으로 돌아갑니다!", Toast.LENGTH_SHORT).show();
        }
    }

}