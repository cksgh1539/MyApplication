package com.example.b10626.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by B10626 on 2018-03-28.
 */

public class Join extends AppCompatActivity {
    EditText UID, Name, ID,Password;
    String str_UID,str_Name,str_ID,str_Password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);
        UID = (EditText)findViewById(R.id.uid);
        Name = (EditText)findViewById(R.id.name);
        ID = (EditText)findViewById(R.id.id);
        Password = (EditText)findViewById(R.id.password);

    }

    public void Submit(View view){
        String str_UID = UID.getText().toString();
        String str_Name = Name.getText().toString();
        String str_ID = ID.getText().toString();
        String str_Password = Password.getText().toString();
        String type = "submit";

        BackgroundWork backgroundWork = new BackgroundWork((this));
        backgroundWork.execute(type, str_UID, str_Name ,str_ID ,str_Password);

    }

}
