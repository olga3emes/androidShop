package com.example.olga.shop.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by olga on 17/5/17.
 */

public class AuthSQLite extends SQLiteOpenHelper {

    String sqlCreate = "CREATE TABLE USERS (id INTEGER primary key autoincrement, name STRING, address STRING, email STRING, phone INTEGER, password STRING)";

    String sqlDrop ="DROP TABLE IF EXISTS USERS";

    public AuthSQLite (Context context,String name, SQLiteDatabase.
            CursorFactory factory, int version){

        super(context,name,factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //db.execSQL(sqlDrop);
        //db.execSQL(sqlCreate);
    }

    //public void onInsert (SQLiteDatabase db, String name, String address, String email, )
}
