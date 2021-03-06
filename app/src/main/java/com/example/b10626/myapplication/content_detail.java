package com.example.b10626.myapplication;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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

/**
 * Created by hp on 2018-05-03.
 */

public class content_detail extends AppCompatActivity{
    String Name,First_PWD,ID,price_button;
    int Position,OrganPosition;
    Bitmap Image;

    TextView Content_Name, Content_Price, Content_Comment;
    ImageView Content_Image;
    RadioButton RB1,RB2,RB3;
   // php task;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contents_detail);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.etc_titlebar);
        TextView titlebar =(TextView)findViewById(R.id.etc_titlebar_text);
        titlebar.setText("후원 상세설명");

        Content_Image = (ImageView) findViewById(R.id.Image);
        Content_Name = (TextView) findViewById(R.id.Name);
        Content_Price = (TextView) findViewById(R.id.Price);
        Content_Comment = (TextView) findViewById(R.id.Comment);

        RB1 = (RadioButton)findViewById(R.id.radio1);
        RB2 = (RadioButton)findViewById(R.id.radio2);
        RB3 = (RadioButton)findViewById(R.id.radio3);

        Intent intent = getIntent();
        byte[] arr = getIntent().getByteArrayExtra("content_image");
        Image = BitmapFactory.decodeByteArray(arr, 0, arr.length);

        Position = intent.getIntExtra("content_position", 0);
        OrganPosition = intent.getIntExtra("organ_position",0);

        Name = intent.getStringExtra("content_name");
        First_PWD = intent.getStringExtra("first_PWD");
        ID = intent.getStringExtra("ID");

        Content_Image.setImageBitmap(Image);
        Content_Name.setText(Name);
    //    Content_Price.setText(Price);
        Content_Comment.setText(Content_showlist.CONTENTS[OrganPosition][Position]);

       // task = new php();
      //  task.execute("http://113.198.80.147/login_menu.php");
    }

    public void donation(View view){
        price_button = "0000";
        if(RB1.isChecked() == true){
            price_button = "1000";
        }else if(RB2.isChecked() == true){
            price_button = "2000";
        }else if(RB3.isChecked() == true){
            price_button = "3000";
        }
        AlertDialog.Builder check = new AlertDialog.Builder((content_detail.this));

        check.setTitle("비밀번호입력");
        check.setMessage("입력시 바로 기부됩니다!");

        final EditText ET = new EditText((content_detail.this));
       // ET.setInputType(InputType.TYPE_CLASS_NUMBER);
        PasswordTransformationMethod pass_void = new PasswordTransformationMethod();
        ET.setTransformationMethod(pass_void);
        check.setView(ET);

        check.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.v("Chanho", "확인");

                // Text 값 받아서 로그 남기기
                String Confirm_PWD = ET.getText().toString();
                String type = "Donation";

                BackgroundWork backgroundWork = new BackgroundWork((content_detail.this));
                backgroundWork.execute(type, ID,Confirm_PWD, price_button,Name);

                dialog.dismiss();     //닫기

            }
        });

// 취소 버튼 설정
        check.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.v("Chanho","취소");
                dialog.dismiss();     //닫기
                // Event
            }
        });
// 창 띄우기
        check.show();
    }

    public void back(View view){
        Intent intent = new Intent(content_detail.this,Z_Goodneighbors.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //해당 액티비티 위에 스택 삭제
        intent.putExtra("ID",ID);
        intent.putExtra("first_PWD",First_PWD);
        startActivity(intent);
    }


}
