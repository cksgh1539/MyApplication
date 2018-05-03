package com.example.b10626.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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
import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;

/**
 * Created by hp on 2018-03-30.
 */

public class UserData extends AppCompatActivity {
   AlertDialog.Builder alertDialog;
    ListView listView;
    php task;
    ArrayList<userInfoItem> listItem = new ArrayList<>();
    SwipeRefreshLayout SRlayout;
    LinearLayout Linear;
    String ID,PWD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_db);

        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");
        PWD = intent.getStringExtra("password");
        task = new php();
        //logout = (Button)findViewById(R.id.logout);
        listView = (ListView) findViewById(R.id.listView);
        task.execute("http://113.198.80.147/login_done.php", ID, PWD);

        SRlayout = (SwipeRefreshLayout) findViewById(R.id.SRlayout);
        Linear = (LinearLayout) findViewById(R.id.Linear);
        if (Linear.getScrollY() == 0) {
            SRlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    listItem.clear();
                    task = new php();
                    task.execute("http://113.198.80.147/login_done.php", ID, PWD);
                    Toast.makeText(getApplicationContext(), "갱신 중입니다!", Toast.LENGTH_SHORT).show();
                    SRlayout.setRefreshing(false);
                }
            });
        }



        /*SRlayout.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if(Linear.getScrollY() == 0){
                    SRlayout.setEnabled(true);
                }else {
                    SRlayout.setEnabled(false);
                }
            }
        });*/
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
           // StringBuilder jsonHtml = new StringBuilder();

      /*      try {
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
            }*/

           // return jsonHtml.toString();
            return null;
        }

        protected void onPostExecute(String str) {

        String date,uid,name,ins,money,total;

        try{
            JSONObject root = new JSONObject(String.valueOf(str));
            JSONArray ja = root.getJSONArray("results");
            for(int i=0; i<ja.length(); i++){
                JSONObject jo = ja.getJSONObject(i);
                date = jo.getString("date");
             //   uid = jo.getString("uid");
                name = jo.getString("name");
              //  ins = jo.getString("ins");
                money = jo.getString("money");
                total = jo.getString("total");

                listItem.add(new userInfoItem(date,name,money,total));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
           infoAdapter adapter = new infoAdapter(listItem,getApplicationContext(),R.layout.infolist);
          listView.setAdapter(adapter);
          listView.setDividerHeight(10);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

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
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
                alertDialog.setNegativeButton("취소",null);
                alertDialog.show();
                //  return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


