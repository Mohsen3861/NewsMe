package com.esgi.newsme.newsme.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.esgi.newsme.newsme.DataBase.DataBaseHandler;

/**
 * Created by Promobile on 01/06/2016.
 */
public abstract class AbstracDAO <T>{

    private DataBaseHandler dbHandler;

    private SQLiteDatabase sqliteDb;

    public AbstracDAO(Context context) {
        this.dbHandler = new DataBaseHandler(context);
    }

    public SQLiteDatabase getSqliteDb() {
        return sqliteDb;
    }

    public SQLiteDatabase open() {
        sqliteDb = this.dbHandler.getWritableDatabase();
        return sqliteDb;
    }

    public void close() {
        sqliteDb.close();
    }

    public abstract void add(T object);
    public abstract T get(int id);
}
