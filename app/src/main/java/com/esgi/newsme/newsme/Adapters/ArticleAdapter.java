package com.esgi.newsme.newsme.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.newsme.newsme.Activities.articleActivity;
import com.esgi.newsme.newsme.Models.Article;
import com.esgi.newsme.newsme.R;
import com.esgi.newsme.newsme.SQL.DAO.ArticleDAO;
import com.esgi.newsme.newsme.Utils.DateUtils;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mohsen raeisi on 19/05/2016.
 */
public class ArticleAdapter extends BaseAdapter{

    private List<Article> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;
    private int lastPosition = 0;
    private ArticleDAO articleDAO;

    public ArticleAdapter (Context context){
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    this.articleDAO = new ArticleDAO(context);
        this.articleDAO.open();
    }

    public void addItem( Article item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void addItemsCollection(List<Article> elements){
       // mData.clear();
        mData.addAll(elements);

        if(mData.size()>0)
        Collections.sort(mData,Collections.reverseOrder());

    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Article getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ArticleViewHolder holder = null;
        final Article currentItem = mData.get(position);
        if( convertView == null){
            convertView = mInflater.inflate(R.layout.article_cell,null);

            holder = new ArticleViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.textView_title);
            holder.source = (TextView) convertView.findViewById(R.id.textView_source);
            holder.image = (ImageView) convertView.findViewById(R.id.imageView_article);
            holder.dateTextView = (TextView) convertView.findViewById(R.id.textView_date) ;
            holder.shareButton = (ImageView) convertView.findViewById(R.id.imageView_share);
            holder.favoritButton = (ImageView) convertView.findViewById(R.id.imageView_favorit);
            holder.sourceImage = (ImageView) convertView.findViewById(R.id.source_image_article_page);

            convertView.setTag(holder);
        }else{
            holder = (ArticleViewHolder) convertView.getTag();
        }
        holder.title.setText(currentItem.getTitle());

        String source = "• " +currentItem.getSource();
        holder.source.setText(source);

        //image
        Picasso.with(context).load(currentItem.getImgUrl()).placeholder(R.drawable.default_placeholder).into(holder.image);

        //SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
       // holder.dateTextView.setText( format.format(mData.get(position).getDateArticle()));


        //la date
        holder.dateTextView.setText(DateUtils.getTimeAgo(currentItem.getDateArticle().getTime(),context));

        //favorit button
        if(currentItem.isSaved())
            holder.favoritButton.setImageResource((R.drawable.heart));
        else
            holder.favoritButton.setImageResource((R.drawable.heart_outline));


        //icon de source
        switch (currentItem.getSource()){
            case "BFM":
                holder.sourceImage.setImageResource(R.drawable.ic_bfm);
                break;
            case "Le monde":
                holder.sourceImage.setImageResource(R.drawable.ic_lemond);

                break;
            case "01-NET":
                holder.sourceImage.setImageResource(R.drawable.ic_01net_logo);
                break;
        }

        final Article currentArticle = mData.get(position);
        final ArticleViewHolder finalHolder = holder;

        holder.favoritButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!currentArticle.isSaved()) {
                    finalHolder.favoritButton.setImageResource(R.drawable.heart);
                    Toast.makeText(context, "l'article enregistré", Toast.LENGTH_SHORT).show();
                    // TODO code for saving article

                    currentArticle.setSaved(true);
                    articleDAO.updateArticle(currentArticle);

                    //end
                    mData.get(position).setSaved(true);
                }else{
                    finalHolder.favoritButton.setImageResource(R.drawable.heart_outline);
                    mData.get(position).setSaved(false);

                    currentArticle.setSaved(false);
                    articleDAO.updateArticle(currentArticle);
                }
            }
        });

        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = currentItem.getTitle() + " " + currentItem.getUrl() ;
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, currentItem.getTitle());
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                context.startActivity(Intent.createChooser(sharingIntent, currentItem.getTitle()));
            }
        });

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, articleActivity.class);
                intent.putExtra("title" , currentItem.getTitle());
                intent.putExtra("desc" , currentItem.getDescription());
                intent.putExtra("source" , currentItem.getSource());
                intent.putExtra("image",currentItem.getImgUrl());
                intent.putExtra("url",currentItem.getUrl());
                intent.putExtra("date" , currentItem.getDateArticle().getTime());

                context.startActivity(intent);
            }
        });

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, articleActivity.class);
                intent.putExtra("title" , currentItem.getTitle());
                intent.putExtra("desc" , currentItem.getDescription());
                intent.putExtra("source" , currentItem.getSource());
                intent.putExtra("image",currentItem.getImgUrl());
                intent.putExtra("url",currentItem.getUrl());
                intent.putExtra("date" , currentItem.getDateArticle().getTime());

                context.startActivity(intent);
            }
        });


        //animation

        Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        convertView.startAnimation(animation);
        lastPosition = position;

        return convertView;
    }

    static class ArticleViewHolder {
        public TextView title;
        public TextView source;
        public ImageView image;
        public ImageView favoritButton;
        public ImageView shareButton;
        public TextView dateTextView;
        public ImageView sourceImage;
    }
}
