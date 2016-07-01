package com.esgi.newsme.newsme.SQL.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.esgi.newsme.newsme.Models.User;

import java.text.SimpleDateFormat;

/**
 * Created by Promobile on 01/07/2016.
 */
public class UserDAO extends AbstracDAO <User> {

    //Variable pour notre table
    private static final String TABLE_NAME = "user";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS "
            + TABLE_NAME + ";";
    private static final String KEY_ID="id";
    private static final String KEY_NOM = "nom";
    private static final String KEY_PRENOM ="prenom";
    private static final String KEY_EMAIL ="email";
    private static final String KEY_MDP ="mdp";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NOM + " TEXT UNIQUE, "
            + KEY_PRENOM + " TEXT UNIQUE, "
            + KEY_EMAIL + " TEXT UNIQUE, "
            + KEY_MDP + " TEXT UNIQUE);";



    public UserDAO(Context context) {
        super(context);
    }

    @Override
    public void add(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NOM, user.getNom());
        contentValues.put(KEY_PRENOM, user.getPrenom());
        contentValues.put(KEY_EMAIL, user.getEmail());
        contentValues.put(KEY_MDP, user.getMdp());
        //Insertion dans la BDD

        try{
            getSqliteDb().insert(TABLE_NAME, null, contentValues);
        }catch (SQLException e) {
            Log.e("SQL UNIQUE", "ALREADY SAVED IN DATABASE");

        }

    }

    @Override
    public User get(int id) {
        Cursor cursor = getSqliteDb().query(TABLE_NAME,
                new String[]{KEY_ID, KEY_NOM, KEY_PRENOM, KEY_EMAIL, KEY_MDP},
                KEY_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null
        );

        User user = null;
        if (cursor != null){
            cursor.moveToFirst();
        }

        user = new User(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3));
        return user;
    }
}
