package com.esgi.newsme.newsme.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.newsme.newsme.Models.Article;
import com.esgi.newsme.newsme.R;

public class articleActivity extends AppCompatActivity {

    ImageView image;
    TextView titleTextView;
    TextView descTextView;
    FloatingActionButton fabShareButton;
    TextView sourceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        assignViews();
        prepareView();
    }

    public void assignViews(){

        image = (ImageView) findViewById(R.id.image_article_page);
        titleTextView = (TextView) findViewById(R.id.title_article_page);
        descTextView= (TextView) findViewById(R.id.desc_article_page);
        sourceTextView = (TextView) findViewById(R.id.source_article_page);
        fabShareButton = (FloatingActionButton) findViewById(R.id.fab_share);
    }

    public void prepareView(){
        Intent intent = getIntent();
        Article article = new Article();
        article.setTitle(intent.getStringExtra("title"));
        article.setDescription(intent.getStringExtra("desc"));
        article.setSource(intent.getStringExtra("source"));

        int imageId =Integer.parseInt(intent.getStringExtra("image"));
        Bitmap img = BitmapFactory.decodeResource(getResources(), imageId);

        article.setImage(img);

        image.setImageBitmap(article.getImage());
        titleTextView.setText(article.getTitle());
        descTextView.setText(article.getDescription());
        sourceTextView.setText(article.getSource());

        fabShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(articleActivity.this,"fab clicked",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
