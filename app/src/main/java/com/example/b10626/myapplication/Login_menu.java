package com.example.b10626.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

/**
 * Created by hp on 2018-04-09.
 */

public class Login_menu extends AppCompatActivity {
    TextView user_total,user_name;
    AlertDialog.Builder alertDialog;
    String ID,PWD;
    php task;
    private final double finish_interval_time=2000;
    private double backPressedTime =0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_menu);

        user_total = (TextView)findViewById(R.id.user_total);
        user_name = (TextView)findViewById(R.id.user_name);
        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");
        PWD = intent.getStringExtra("password");

        task = new php();
        task.execute("http://113.198.80.147/login_menu.php",ID,PWD);
    }

    private class php extends AsyncTask<String, Void, String> { //---------------로그인 할 때 받은 아이디 , 패스워드 php에서 비교

        @Override
        protected String doInBackground(String... urls) {

            try {
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

            String total="";
            String name="";

            try{
                JSONObject root = new JSONObject(String.valueOf(str));
                JSONArray ja = root.getJSONArray("results");
                for(int i=0; i<ja.length(); i++){
                    JSONObject jo = ja.getJSONObject(i);
                    total = jo.getString("total");
                    name = jo.getString("name");
                }

            }catch(JSONException e){
                e.printStackTrace();
            }

            user_name.setText("안녕하세요" +name+" 님");
            user_total.setText(name+"님의 총 적립금은 "+ total +"원 입니다.");
        }
    }

    public void trade_detail(View view) { //------------------상세 거래내역
        String username = ID;
        String password = PWD;
   //     String type = "trade_detail";

        Intent intent = new Intent(this,UserData.class);
        intent.putExtra("ID", username);
        intent.putExtra("password",password);
        startActivity(intent);


     //    BackgroundWork backgroundWork = new BackgroundWork((this));
     //   backgroundWork.execute(type, username, password);

    }

    public void graph(View view) { //------------------------그래프
        String username = ID;
        String password = PWD;
        String type = "trade_detail";

        BackgroundWork backgroundWork = new BackgroundWork((this));
        backgroundWork.execute(type, username, password);

    }
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