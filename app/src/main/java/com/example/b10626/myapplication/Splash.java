package com.example.b10626.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.felipecsl.gifimageview.library.GifImageView;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hp on 2018-05-21.
 */

public class Splash extends AppCompatActivity{

    private GifImageView gifImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.splash);

        gifImageView = (GifImageView)findViewById(R.id.gifImageView);

        try{
            InputStream inputStream = getAssets().open("pig.gif");
            byte[] bytes = IOUtils.toByteArray(inputStream);
            gifImageView.setBytes(bytes);
            gifImageView.startAnimation();
        }catch (IOException ex){

        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Splash.this.startActivity(new Intent(Splash.this,MainActivity.class));
                Splash.this.finish();
            }
        },5500);
    }

}
