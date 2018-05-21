package com.example.b10626.myapplication;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText usernameEt, PasswordEt;
    private final double finish_interval_time=2000;
    private double backPressedTime =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEt = (EditText) findViewById(R.id.userID);
        PasswordEt = (EditText) findViewById(R.id.userPW);



        // Intent intent = new Intent(this,Login_menu.class);
        //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // intent = new Intent(this, ActivityTest1.class);
        //   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //  startActivity(intent);


        //  intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        // PasswordEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
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

}


