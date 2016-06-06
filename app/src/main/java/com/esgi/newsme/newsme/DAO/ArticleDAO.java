package com.esgi.newsme.newsme.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.ParseException;

import com.esgi.newsme.newsme.Models.Article;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Promobile on 01/06/2016.
 */
public class ArticleDAO extends AbstracDAO <Article> {

    //Variable pour notre table
    private static final String TABLE_NAME = "article";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS "
            + TABLE_NAME + ";";
    private static final String KEY_ID="id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION ="description";
    private static final String KEY_GUID ="guid";
    private static final String KEY_ENCLOSURE ="enclosure";
    private static final String KEY_DATE = "pubDate";

    public static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE_NAME + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_TITLE + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_GUID + " TEXT, "
            + KEY_ENCLOSURE + " TEXT, "
            + KEY_DATE + " DATE);";


    public ArticleDAO(Context context){
        super(context);
    }

    public void add(Article article){
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, article.getTitle());
        contentValues.put(KEY_DESCRIPTION, article.getDescription());
        contentValues.put(KEY_GUID, article.getGuid());
        contentValues.put(KEY_ENCLOSURE, article.getEnclosure());
        //Gestion de la date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        contentValues.put(KEY_DATE, dateFormat.format(article.getPubDate()));
        //Insertion dans la BDD
        getSqliteDb().insert(TABLE_NAME, null, contentValues);
    }

    public Article get(int id) {
        Cursor cursor = getSqliteDb().query(TABLE_NAME,
                new String[]{KEY_ID, KEY_TITLE, KEY_DESCRIPTION,KEY_GUID,KEY_ENCLOSURE, KEY_DATE},
                KEY_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null
        );
        Article article = null;
        if (cursor != null) {
            cursor.moveToFirst();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date pubDate = null;
            try {
                pubDate = dateFormat.parse(cursor.getString(4));
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }

            article = new Article(cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    pubDate);
        }
        return article;
    }


}
