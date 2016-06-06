package com.esgi.newsme.newsme.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.esgi.newsme.newsme.DAO.ArticleDAO;

/**
 * Created by Promobile on 01/06/2016.
 */
public class DataBaseHandler extends SQLiteOpenHelper {

    private static final String DB_NAME ="database.db" ;
    private static final int DB_VERSION = 1;

    public DataBaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ArticleDAO.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}