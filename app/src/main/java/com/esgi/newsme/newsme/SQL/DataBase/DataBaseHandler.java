package com.esgi.newsme.newsme.SQL.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.esgi.newsme.newsme.SQL.DAO.ArticleDAO;
import com.esgi.newsme.newsme.SQL.DAO.UserDAO;

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
        db.execSQL(UserDAO.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
