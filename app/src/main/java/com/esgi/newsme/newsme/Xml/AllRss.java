package com.esgi.newsme.newsme.Xml;

import android.content.Context;
import android.os.AsyncTask;

import com.esgi.newsme.newsme.Adapters.ArticleAdapter;

/**
 * Created by mohsen raeisi on 28/05/2016.
 */
public class AllRss extends AsyncTask<Void,Void,Void> {


    ArticleAdapter articleAdapter;
    Context context;

    public AllRss(ArticleAdapter adapter , Context context){
        articleAdapter = adapter;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        BfmRss   bfmRss = new BfmRss(context,articleAdapter , false);
        Rss01net rss01net = new Rss01net(context,articleAdapter , false);
        LemondeRss lemondeRss = new LemondeRss(context,articleAdapter , false);

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
        super.onPostExecute(aVoid);
    }
}
