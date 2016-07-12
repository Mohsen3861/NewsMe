package com.esgi.newsme.newsme.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.newsme.newsme.Models.Article;
import com.esgi.newsme.newsme.R;
import com.esgi.newsme.newsme.Utils.DateUtils;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class articleActivity extends AppCompatActivity {

    ImageView image;
    TextView titleTextView;
    TextView descTextView;
    FloatingActionButton fabShareButton;
    TextView sourceTextView;
    ImageView sourceImage;
    TextView articleDate;
    TextView suiteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        assignViews();
        prepareView();
    }

    public void assignViews() {

        image = (ImageView) findViewById(R.id.image_article_page);
        titleTextView = (TextView) findViewById(R.id.title_article_page);
        descTextView = (TextView) findViewById(R.id.desc_article_page);
        sourceTextView = (TextView) findViewById(R.id.source_article_page);
        fabShareButton = (FloatingActionButton) findViewById(R.id.share_article_page);
        sourceImage = (ImageView) findViewById(R.id.source_image_article_page);
        articleDate = (TextView) findViewById(R.id.date_article_page);
        suiteTextView = (TextView) findViewById(R.id.article_suite);
    }

    public void prepareView() {
        Intent intent = getIntent();
        final Article article = new Article();
        article.setTitle(intent.getStringExtra("title"));
        article.setDescription(intent.getStringExtra("desc"));
        article.setSource(intent.getStringExtra("source"));
        article.setImgUrl(intent.getStringExtra("image"));
        article.setUrl(intent.getStringExtra("url"));

        Date date = new Date();
        date.setTime(intent.getLongExtra("date", 0));
        article.setDateArticle(date);

        Log.e("article image", article.getImgUrl());
        Picasso.with(articleActivity.this).load(article.getImgUrl()).placeholder(R.drawable.default_placeholder).into(image);

        // image.setImageBitmap(article.getImage());
        titleTextView.setText(article.getTitle());
        descTextView.setText(article.getDescription());
        sourceTextView.setText(article.getSource());

        //icon de source
        switch (article.getSource()) {
            case "BFM":
                sourceImage.setImageResource(R.drawable.ic_bfm);
                break;
            case "Le monde":
                sourceImage.setImageResource(R.drawable.ic_lemond);
                break;
            case "01-NET":
                sourceImage.setImageResource(R.drawable.ic_01net_logo);
                break;
            case "20-MINUTES":
                sourceImage.setImageResource(R.mipmap.ic_20minutes_logo);
                break;
        }

        //date
        articleDate.setText(DateUtils.getDateText(article.getDateArticle()));


        fabShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = article.getTitle() + " " + article.getUrl();
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, article.getTitle());
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                articleActivity.this.startActivity(Intent.createChooser(sharingIntent, article.getTitle()));

            }
        });

        suiteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webViewIntent = new Intent(articleActivity.this , WebViewActivity.class);
                webViewIntent.putExtra("url" , article.getUrl());
                webViewIntent.putExtra("src" , article.getSource());

                startActivity(webViewIntent);
            }
        });
    }
}
