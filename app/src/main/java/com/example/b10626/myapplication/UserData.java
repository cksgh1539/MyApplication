package com.example.b10626.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

/**
 * Created by hp on 2018-03-30.
 */

public class UserData extends Activity {

    TextView txt,txt2;
    ListView listView;
    php task;
    ArrayList<userInfoItem> listItem = new ArrayList<>();
    userInfoItem Item;
    String ID,PWD;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_db);

        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");
        PWD = intent.getStringExtra("password");

        task = new php();
        txt = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt2);
        listView = (ListView)findViewById(R.id.listView);
        task.execute("http://113.198.80.147/login_done.php",ID,PWD);
    }

    private class php extends AsyncTask<String, Void, String> {


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
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
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

            // String type = urls[0];
            StringBuilder jsonHtml = new StringBuilder();
            //  String done_url = "http://223.194.128.68/test_login.php";
            //   if (type.equals("success")) {
            try {
                // 연결 url 설정
                URL url = new URL(urls[0]);
                // 커넥션 객체 생성
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                // 연결되었으면.
                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    // 연결되었음 코드가 리턴되면.
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        for (; ; ) {
                            // 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
                            String line = br.readLine();
                            if (line == null) break;
                            // 저장된 텍스트 라인을 jsonHtml에 붙여넣음
                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return jsonHtml.toString();
         //   return null;

        }


        protected void onPostExecute(String str) {

        String date,uid,name,ins,deposit,total;
        String array[];
        int length=0;
        try{
            JSONObject root = new JSONObject(String.valueOf(str));
            JSONArray ja = root.getJSONArray("results");
            length = ja.length();
            for(int i=0; i<ja.length(); i++){
                JSONObject jo = ja.getJSONObject(i);
                date = jo.getString("date");
                uid = jo.getString("uid");
                name = jo.getString("name");
                ins = jo.getString("ins");
                deposit = jo.getString("deposit");
                total = jo.getString("total");

                listItem.add(new userInfoItem(date,uid,name,ins,deposit,total));
            }

        }catch(JSONException e){
            e.printStackTrace();
        }
            infoAdapter adapter = new infoAdapter(listItem,getApplicationContext(),R.layout.infolist);
          listView.setAdapter(adapter);
          listView.setDividerHeight(10);

           // ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItem4);
       /* for(int i=0;i<length;i++) {
            txt.setText("date :" + listItem.get(i).getData(0) + "\nuid:" + listItem.get(0).getData(1) + "\nname:" + listItem.get(0).getData(2)
                    + "\nins:" + listItem.get(0).getData(3) + "\ndeposit:" + listItem.get(0).getData(4) + "\ntotal:" + listItem.get(0).getData(5));
            txt2.setText("date :" + listItem.get(1).getData(0) + "\nuid:" + listItem.get(1).getData(1) + "\nname:" + listItem.get(1).getData(2)
                    + "\nins:" + listItem.get(1).getData(3) + "\ndeposit:" + listItem.get(1).getData(4) + "\ntotal:" + listItem.get(1).getData(5));
        }*/
        }

    }
}


