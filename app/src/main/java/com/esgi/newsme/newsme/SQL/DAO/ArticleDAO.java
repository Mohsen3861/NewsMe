package com.esgi.newsme.newsme.SQL.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.net.ParseException;
import android.util.Log;

import com.esgi.newsme.newsme.Models.Article;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private static final String KEY_URL ="url";
    private static final String KEY_URL_IMAGE ="image_url";
    private static final String KEY_DATE = "article_date";
    private static final String KEY_SOURCE = "source";
    private static final String KEY_SAVED = "saved";//0 non , 1 oui


    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
           + KEY_TITLE + " TEXT UNIQUE, "
            + KEY_DESCRIPTION + " TEXT UNIQUE, "
            + KEY_URL + " TEXT UNIQUE, "
            + KEY_URL_IMAGE + " TEXT, "
            + KEY_SOURCE + " TEXT, "
            + KEY_SAVED + " TEXT DEFAULT 0, "
            + KEY_DATE + " DATE);";


    public ArticleDAO(Context context){
        super(context);
    }

    public void add(Article article){
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, article.getTitle());
        contentValues.put(KEY_DESCRIPTION, article.getDescription());
        contentValues.put(KEY_URL, article.getUrl());
        contentValues.put(KEY_URL_IMAGE, article.getImgUrl());
        contentValues.put(KEY_SOURCE, article.getSource());
        //Gestion de la date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        contentValues.put(KEY_DATE, dateFormat.format(article.getDateArticle()));
        //Insertion dans la BDD

        try{
            getSqliteDb().insert(TABLE_NAME, null, contentValues);
        }catch (SQLException e) {
            Log.e("SQL UNIQUE","ALREADY SAVED IN DATABASE");

        }



    }




    public Article get(int id) {
        Cursor cursor = getSqliteDb().query(TABLE_NAME,
                new String[]{KEY_ID, KEY_TITLE, KEY_DESCRIPTION, KEY_URL, KEY_URL_IMAGE,KEY_SOURCE,KEY_SAVED, KEY_DATE},
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
                pubDate = dateFormat.parse(cursor.getString(7));
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }

            Boolean savedStat = (cursor.getInt(6) == 1);

            article = new Article(cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    pubDate,
                    cursor.getString(5),
                    savedStat);
        }
        return article;
    }


    public ArrayList<Article> getAllArticles(){
        ArrayList<Article> articlesList = new ArrayList<>();


        Cursor cursor = getSqliteDb().query(TABLE_NAME,
                new String[]{KEY_ID, KEY_TITLE, KEY_DESCRIPTION, KEY_URL, KEY_URL_IMAGE,KEY_SOURCE,KEY_SAVED, KEY_DATE},
                null,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Article currentArticle;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date pubDate = null;
                try {
                    pubDate = dateFormat.parse(cursor.getString(7));
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }


                Boolean savedStat = (cursor.getInt(6) == 1);


                currentArticle = new Article(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        pubDate,
                        cursor.getString(5),
                        savedStat);


                articlesList.add(currentArticle);
            }
        }

        return articlesList;
    }

    public ArrayList<Article> getArticleBySource(String src){
        ArrayList<Article> articlesList = new ArrayList<>();


        Cursor cursor = getSqliteDb().query(TABLE_NAME,
                new String[]{KEY_ID, KEY_TITLE, KEY_DESCRIPTION, KEY_URL, KEY_URL_IMAGE,KEY_SOURCE,KEY_SAVED, KEY_DATE},
                KEY_SOURCE + "=?",
                new String[]{src},
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Article currentArticle;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date pubDate = null;
                try {
                    pubDate = dateFormat.parse(cursor.getString(7));
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }

                Boolean savedStat = (cursor.getInt(6) == 1);


                currentArticle = new Article(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        pubDate,
                        cursor.getString(5),
                        savedStat);

                articlesList.add(currentArticle);
            }
        }

        return articlesList;
    }



    public ArrayList<Article> getFavoritArticles(){
        ArrayList<Article> articlesList = new ArrayList<>();


        Cursor cursor = getSqliteDb().query(TABLE_NAME,
                new String[]{KEY_ID, KEY_TITLE, KEY_DESCRIPTION, KEY_URL, KEY_URL_IMAGE,KEY_SOURCE,KEY_SAVED, KEY_DATE},
                KEY_SAVED + "=?",
                new String[]{"1"},
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Article currentArticle;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date pubDate = null;
                try {
                    pubDate = dateFormat.parse(cursor.getString(7));
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }

                Boolean savedStat = (cursor.getInt(6) == 1);


                currentArticle = new Article(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        pubDate,
                        cursor.getString(5),
                        savedStat);

                articlesList.add(currentArticle);
            }
        }

        return articlesList;
    }





    public void updateArticle(Article article){

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, article.getTitle());
        contentValues.put(KEY_DESCRIPTION, article.getDescription());
        contentValues.put(KEY_URL, article.getUrl());
        contentValues.put(KEY_URL_IMAGE, article.getImgUrl());
        contentValues.put(KEY_SOURCE, article.getSource());

        //Gestion de la date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        contentValues.put(KEY_SAVED, article.isSaved());
        contentValues.put(KEY_DATE, dateFormat.format(article.getDateArticle()));

        //mettre à jour
        try{
            getSqliteDb().update(TABLE_NAME,contentValues,KEY_ID+"="+article.getId(),null);
        }catch (SQLException e) {
            Log.e("SQL UNIQUE","ALREADY SAVED IN DATABASE");

        }

    }



    public void printTableData(){


        Cursor cur = getSqliteDb().rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if(cur.getCount() != 0){
            cur.moveToFirst();

            do{
                String row_values = "";

                for(int i = 0 ; i < cur.getColumnCount(); i++){
                    row_values = row_values + " || " + cur.getString(i);
                }

                Log.d("LOG_TAG_HERE", row_values);

            }while (cur.moveToNext());
        }
    }
}
