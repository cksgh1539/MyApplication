package com.example.b10626.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Toast;

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

import static android.support.v4.os.LocaleListCompat.create;

/**
 * Created by B10626 on 2018-03-26.
 */

public class BackgroundWork extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog.Builder alertDialog;
    BackgroundWork (Context ctx){
        context = ctx;
    }
    String user_id, user_password;

    @Override
    protected String doInBackground(String... voids) {
        String type = voids[0];
        String login_url = "http://113.198.80.147/test_login.php";
        String Join_url = "http://113.198.80.147/join.php";
        String Donation_Url = "http://113.198.80.147/donation.php";
        if(type.equals("login")) {
            try {
                user_id = voids[1];
                user_password = voids[2];
                URL url = new URL(login_url);
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
        } else if(type.equals("submit")){
            try {
                String uid = voids[1];
                String name = voids[2];
                String  id = voids[3];
                String password = voids[4];
                String password2 = voids[5];
                URL url = new URL(Join_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("uid","UTF-8")+"="+URLEncoder.encode(uid,"UTF-8")+"&"+
                URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                URLEncoder.encode("user","UTF-8")+"="+URLEncoder.encode(id,"UTF-8")+"&"+
                URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"+
                        URLEncoder.encode("password2","UTF-8")+"="+URLEncoder.encode(password2,"UTF-8");

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
        }else if(type.equals("Donation")){
            try {
                user_id = voids[1];
                user_password = voids[2];
                String second_pwd = voids[3];
                String  price = voids[4];
                String content_name = voids[5];
                int point = Integer.parseInt(price);
                String Point = String.valueOf(point/10);

                URL url = new URL(Donation_Url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("first","UTF-8")+"="+URLEncoder.encode(user_password,"UTF-8")+"&"+
                        URLEncoder.encode("second","UTF-8")+"="+URLEncoder.encode(second_pwd,"UTF-8")+"&"+
                        URLEncoder.encode("price","UTF-8")+"="+URLEncoder.encode(price,"UTF-8")+"&"+
                        URLEncoder.encode("ID","UTF-8")+"="+URLEncoder.encode(user_id,"UTF-8")+"&"+
                        URLEncoder.encode("Point","UTF-8")+"="+URLEncoder.encode(Point,"UTF-8")+"&"+
                        URLEncoder.encode("content_name","UTF-8")+"="+URLEncoder.encode(content_name,"UTF-8");

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
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
       // alertDialog = new AlertDialog.Builder(context).create();
        alertDialog = new AlertDialog.Builder(context);

      //  alertDialog.setTitle("Login Status");
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onPostExecute(String result) {

        Handler handler = new Handler();
        alertDialog.setMessage(result);

        if(result.equals("로그인 성공!")) {
     //   alertDialog.setMessage("확인되었습니다");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(context,Login_menu.class);
                intent.putExtra("ID",user_id);
                intent.putExtra("password",user_password);
                context.startActivity(intent);
            }
        },1000);


    }else if(result.equals("회원 가입 성공!")){

            alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //해당액티비티 위에 스택 지움
                    context.startActivity(intent);
                }
            });
        }else if(result.equals("이미 등록된 아이디입니다!")){
            alertDialog.setPositiveButton("확인",null);
        }else if(result.equals("로그인 실패!")){
        alertDialog.setPositiveButton("확인",null);
        }else if(result.equals("기부왕!")){

            alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(context,Login_menu.class);
                    intent.putExtra("ID",user_id);
                    intent.putExtra("password",user_password);
                    context.startActivity(intent);
                }
            });
        }else if(result.equals("비밀번호를 다시 입력해주세요!")){
            alertDialog.setPositiveButton("확인",null);
        }else if(result.equals("금액이 부족합니다")){
            alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(context,Login_menu.class);
                    intent.putExtra("ID",user_id);
                    intent.putExtra("password",user_password);
                    context.startActivity(intent);
                }
            });
        }
        alertDialog.show();

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
