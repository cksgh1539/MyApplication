/*
package com.example.b10626.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;

*/
/**
 * Created by hp on 2018-05-01.
 *//*


public class Contents_dbHelper extends SQLiteOpenHelper {

    public Contents_dbHelper(Context context) {
        super(context, Contents_db.DB_NAME, null, Contents_db.DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Contents_db.Content.CREATE_TABLE);

        // db.execSQL("CREATE TABLE favorite_table ( id INTEGER primary key autoincrement, phoneNumber TEXT, title TEXT, thumbnail TEXT UNIQUE, method TEXT, videourl TEXT, intensity TEXT, frequency TEXT, time TEXT);");

    }

    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(Contents_db.Content.DELETE_TABLE);
       
        onCreate(db);
    }

    //맛집 정보 db에 저장---------------------------------------------------------------------------
    public long insertRSByMethod(String ImageRS,String name, String price, String comment) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contents_db.Content.KEY_ImageRS,ImageRS);
        values.put(Contents_db.Content.KEY_name, name);
        values.put(Contents_db.Content.KEY_price, price);
        values.put(Contents_db.Content.KEY_comment, comment);

        return db.insert(Contents_db.Content.TABLE_NAME, null, values);
    }


    //db에 저장된 모든 Content_____________________________________________________________________
    public Cursor getRSByMethod() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(Contents_db.Content.TABLE_NAME,null,null,null,null,null,null);
    }

    

    //받아온 이름과 일치하는 Content select
    public Cursor getRSbyName (String name) {
        SQLiteDatabase db = getReadableDatabase();
        String sql1 = "Select * FROM " + Contents_db.Content.TABLE_NAME
                + " Where " + Contents_db.Content.KEY_name + " = '" + name + "'";
        return db.rawQuery(sql1, null);
    }


}*/
