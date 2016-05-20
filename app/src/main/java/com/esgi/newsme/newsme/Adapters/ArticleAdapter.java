package com.esgi.newsme.newsme.Adapters;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

    public ArticleAdapter (Context context){
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
    public View getView(int position, View convertView, ViewGroup parent) {
        ArticleViewHolder holder = null;
        if( convertView == null){
            convertView = mInflater.inflate(R.layout.article_cell,null);

            holder = new ArticleViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.textView_title);
            holder.source = (TextView) convertView.findViewById(R.id.textView_source);
            holder.image = (ImageView) convertView.findViewById(R.id.imageView_article);
            holder.shareButton = (ImageView) convertView.findViewById(R.id.imageView_share);
            holder.shareButton = (ImageView) convertView.findViewById(R.id.imageView_favorit);



            convertView.setTag(holder);
        }else{
            holder = (ArticleViewHolder) convertView.getTag();
        }
        holder.title.setText(mData.get(position).getTitle());
        holder.source.setText(mData.get(position).getSource());
        holder.image.setImageBitmap(mData.get(position).getImage());

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
