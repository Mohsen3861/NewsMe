package com.esgi.newsme.newsme.Adapters;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.newsme.newsme.Activities.articleActivity;
import com.esgi.newsme.newsme.Models.Article;
import com.esgi.newsme.newsme.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohsen raeisi on 19/05/2016.
 */
public class ArticleAdapter extends BaseAdapter{

    private List<Article> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    public ArticleAdapter (Context context){
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    public void addItem( Article item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void addItemsCollection(List<Article> elements){
        mData.clear();
        mData.addAll(elements);
        notifyDataSetChanged();

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
        if( convertView == null){
            convertView = mInflater.inflate(R.layout.article_cell,null);

            holder = new ArticleViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.textView_title);
            holder.source = (TextView) convertView.findViewById(R.id.textView_source);
            holder.image = (ImageView) convertView.findViewById(R.id.imageView_article);
            holder.shareButton = (ImageView) convertView.findViewById(R.id.imageView_share);
            holder.favoritButton = (ImageView) convertView.findViewById(R.id.imageView_favorit);



            convertView.setTag(holder);
        }else{
            holder = (ArticleViewHolder) convertView.getTag();
        }
        holder.title.setText(mData.get(position).getTitle());
        holder.source.setText(mData.get(position).getSource());
        holder.image.setImageBitmap(mData.get(position).getImage());

       final Article currentArticle = mData.get(position);

        final ArticleViewHolder finalHolder = holder;

        holder.favoritButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!currentArticle.isSaved()) {
                    finalHolder.favoritButton.setImageResource(R.drawable.heart);
                    Toast.makeText(context, "l'article enregistré", Toast.LENGTH_SHORT).show();
                    // TODO code for saving article

                    //end
                    mData.get(position).setSaved(true);
                }else{
                    finalHolder.favoritButton.setImageResource(R.drawable.heart_outline);
                    mData.get(position).setSaved(false);

                }
            }
        });

        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(context,"share Clicked at : "+ position,Toast.LENGTH_SHORT).show();

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = mData.get(position).getDescription() ;
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mData.get(position).getTitle());
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                context.startActivity(Intent.createChooser(sharingIntent, mData.get(position).getTitle()));
            }
        });

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Article article = mData.get(position);

                Intent intent = new Intent(context, articleActivity.class);
                intent.putExtra("title" , article.getTitle());
                intent.putExtra("desc" , article.getDescription());
                intent.putExtra("source" , article.getSource());
                intent.putExtra("image",article.getImgUrl());

                context.startActivity(intent);
            }
        });

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Article article = mData.get(position);

                Intent intent = new Intent(context, articleActivity.class);
                intent.putExtra("title" , article.getTitle());
                intent.putExtra("desc" , article.getDescription());
                intent.putExtra("source" , article.getSource());
                intent.putExtra("image",article.getImgUrl());

                context.startActivity(intent);
            }
        });

        return convertView;
    }

    static class ArticleViewHolder {
        public TextView title;
        public TextView source;
        public ImageView image;
        public ImageView favoritButton;
        public ImageView shareButton;
    }
}
