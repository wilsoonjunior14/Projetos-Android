package com.example.wilson.recognitionvoice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Wilson on 15/03/2017.
 */

public class DataBase extends SQLiteOpenHelper{

    private static final String nome_database = "wnotes";
    private static final int version = 1;

    public DataBase(Context context) {
        super(context,nome_database,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS notas(_id Integer Primary Key AutoIncrement,nome Varchar(45),data_criacao Varchar(45),data_lembrete Varchar(45),texto Varchar(1000));";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
