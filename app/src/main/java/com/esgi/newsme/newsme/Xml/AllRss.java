package com.esgi.newsme.newsme.Xml;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;

import com.esgi.newsme.newsme.Adapters.ArticleAdapter;

/**
 * Created by mohsen raeisi on 28/05/2016.
 */
public class AllRss extends AsyncTask<Void,Void,Void> {


    ArticleAdapter articleAdapter;
    Context context;
    SwipeRefreshLayout loader;

    public AllRss(ArticleAdapter adapter , Context context , SwipeRefreshLayout loader){
        articleAdapter = adapter;
        this.context = context;
        this.loader = loader;
    }

    @Override
    protected void onPreExecute() {
        BfmRss   bfmRss = new BfmRss(context,articleAdapter , false , loader);
        Rss01net rss01net = new Rss01net(context,articleAdapter , false, loader);
        LemondeRss lemondeRss = new LemondeRss(context,articleAdapter , false,loader);

        bfmRss.execute();
        rss01net.execute();
        lemondeRss.execute();

        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {

        return null;

    }

    @Override
    protected void onPostExecute(Void aVoid) {

       articleAdapter.notifyDataSetChanged();
        loader.setRefreshing(false);
        super.onPostExecute(aVoid);
    }
}
