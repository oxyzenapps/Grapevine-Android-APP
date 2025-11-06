package com.oxyzenhomes.grapevine.oxyzenrental;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyHelper extends SQLiteOpenHelper {
    public static final String dbname="glivebooks";
    public static final int version=1;
    public MyHelper(Context context){
        super (context,dbname,null,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String cmd="CREATE TABLE TESTING(_ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,DESCRIPTION TEXT)";
        db.execSQL(cmd);
        insertData("ABC","TESTING RECORD1",db);
        insertData("XYZ","TESTING RECORD2",db);
    }

    private void insertData(String Name,String Description,SQLiteDatabase db){
        ContentValues values=new ContentValues();
        values.put("NAME",Name);
        values.put("DESCRIPTION",Description);
        db.insert("TESTING",null,values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
