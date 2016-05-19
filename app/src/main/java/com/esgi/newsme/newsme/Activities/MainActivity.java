package com.esgi.newsme.newsme.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.esgi.newsme.newsme.Adapters.ArticleAdapter;
import com.esgi.newsme.newsme.Models.Article;
import com.esgi.newsme.newsme.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView mainNewsList ;
    ArticleAdapter articleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();
        prepareList();
    }

    public void assignViews(){
        mainNewsList = (ListView) findViewById(R.id.listViewMain);
    }

    public void prepareList(){
        articleAdapter = new ArticleAdapter(this);
      //  ArrayList<Article> newsList = new ArrayList<>();

        Article article = new Article();
        article.setTitle("L’Ile-de-France adopte le principe des tests salivaires de détection de drogue dans les lycées\n");
        article.setSource("lemonde.fr");
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.image_test);
        article.setImage(largeIcon);

        Article article2 = new Article();
        article2.setTitle("Conflit israélo-palestinien : la conférence de Paris aura lieu le 3 juin, en présence de Kerry\n");
        article2.setSource("lemonde.fr");
        Bitmap largeIcon2 = BitmapFactory.decodeResource(getResources(), R.drawable.image_test3);
        article2.setImage(largeIcon2);

        articleAdapter.addItem(article);
        articleAdapter.addItem(article2);

        mainNewsList.setAdapter(articleAdapter);


    }
}
