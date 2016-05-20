package com.esgi.newsme.newsme.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.esgi.newsme.newsme.Adapters.ArticleAdapter;
import com.esgi.newsme.newsme.Models.Article;
import com.esgi.newsme.newsme.R;

import java.io.ByteArrayOutputStream;
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
        article.setDescription("Il n’est pas sûr que Grigory Rodchenkov soit un lecteur assidu de John Le Carré. L’histoire que l’ancien patron du laboratoire antidopage de Moscou a racontée aux journalistes du New York Times, publiée le 12 mai, aurait pourtant pu constituer un excellent scénario pour le spécialiste britannique des romans d’espionnage.\n"
                );
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.image_test);
        article.setImgUrl(R.drawable.image_test+"");
        article.setImage(largeIcon);

        Article article2 = new Article();
        article2.setTitle("Conflit israélo-palestinien : la conférence de Paris aura lieu le 3 juin, en présence de Kerry\n");
        article2.setSource("lemonde.fr");
        article2.setDescription("Ce sont peut-être les dernières images d’Adelma. Elles ont été tournées le mardi 8 mars sur l’esplanade qui borde la gare de Bruxelles-Central. Pour la Journée internationale des droits des femmes, plusieurs associations féministes s’étaient rassemblées au cœur de la capitale belge. Aux discours qui se succédaient sur une tribune de fortune");
        Bitmap largeIcon2 = BitmapFactory.decodeResource(getResources(), R.drawable.image_test3);
        article2.setImgUrl(R.drawable.image_test3+"");
        article2.setImage(largeIcon2);

        articleAdapter.addItem(article);
        articleAdapter.addItem(article2);

        mainNewsList.setAdapter(articleAdapter);

        mainNewsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Article article = articleAdapter.getItem(position);

                Intent intent = new Intent(MainActivity.this, articleActivity.class);
                intent.putExtra("title" , article.getTitle());
                intent.putExtra("desc" , article.getDescription());
                intent.putExtra("source" , article.getSource());
                intent.putExtra("image",article.getImgUrl());

                startActivity(intent);

            }
        });

    }
}
