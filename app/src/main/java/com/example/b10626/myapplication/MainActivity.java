package com.example.b10626.myapplication;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    EditText usernameEt, PasswordEt;
    private final double finish_interval_time=2000;
    private double backPressedTime =0;
    ImageView logo_image,logo_login,logo_password;
    Bitmap_decode bitmap_decode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logo_image = (ImageView)findViewById(R.id.logo_image);
        logo_login = (ImageView)findViewById(R.id.logo_login);
        logo_password = (ImageView)findViewById(R.id.logo_password);

        logo_image.setImageBitmap(bitmap_decode.decodeSampledBitmapFromResource(getResources(),R.drawable.app_logo_icon,100,100));
        logo_login.setImageBitmap(bitmap_decode.decodeSampledBitmapFromResource(getResources(),R.drawable.app_login_icon,100,100));
        logo_password.setImageBitmap(bitmap_decode.decodeSampledBitmapFromResource(getResources(),R.drawable.app_pass_icon,100,100));

        usernameEt = (EditText) findViewById(R.id.userID);
        PasswordEt = (EditText) findViewById(R.id.userPW);

    }


    public void OnLogin(View view) { //--------------------로그인
        String username = usernameEt.getText().toString();
        String password = PasswordEt.getText().toString();
        String type = "login";

        BackgroundWork backgroundWork = new BackgroundWork((this));
        backgroundWork.execute(type, username, password);
    }

    public void OpenReg(View view) { //-----------------회원가입
        Intent intent = new Intent(this, Join.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY); // 호출 액티비티 스택 안쌓임
        startActivity(intent);
        //finish();
    }

    @Override
    public void onBackPressed() { //---------------------뒤로가기 앱 종료
        double tempTime = System.currentTimeMillis();
        double intervaleTime = tempTime - backPressedTime;

        if(0 <= intervaleTime && finish_interval_time >= intervaleTime){
            super.onBackPressed();
            finish();
        }else{
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "앱을 종료하시려면 한번 더 눌러주세요!!", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onPause() {
        Log.v("chanho","pause");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.v("chanho","pause");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.v("chanho","pause");
        super.onStop();
    }



}


