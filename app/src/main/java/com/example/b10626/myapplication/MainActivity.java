package com.example.b10626.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText usernameEt, PasswordEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEt = (EditText)findViewById(R.id.userID);
        PasswordEt = (EditText)findViewById(R.id.userPW);
    }

    public void OnLogin(View view) {
        String username = usernameEt.getText().toString();
        String password = PasswordEt.getText().toString();
        String type = "login";

        BackgroundWork backgroundWork = new BackgroundWork((this));
        backgroundWork.execute(type, username, password);
    }

    public void OpenReg(View view) {
        Intent intent = new Intent(this,Join.class);
      startActivity(intent);
    }
}
