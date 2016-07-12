package com.esgi.newsme.newsme.Xml;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Toast;

import com.esgi.newsme.newsme.Activities.MainActivity;
import com.esgi.newsme.newsme.Adapters.ArticleAdapter;
import com.esgi.newsme.newsme.R;
import com.esgi.newsme.newsme.SQL.DAO.ArticleDAO;

/**
 * Created by mohsen raeisi on 28/05/2016.
 */
public class AllRss extends AsyncTask<Void, Void, Void> {


    ArticleAdapter articleAdapter;
    Context context;
    SwipeRefreshLayout loader;

    public AllRss(ArticleAdapter adapter, Context context, SwipeRefreshLayout loader) {
        articleAdapter = adapter;
        this.context = context;
        this.loader = loader;
    }
int numberOfArticlesBefore;
    int numberOfArticlesAfter;
    ArticleDAO articleDAO;

    @Override
    protected void onPreExecute() {
        articleDAO = new ArticleDAO(context);
        articleDAO.open();

        numberOfArticlesBefore = articleDAO.getCount();
        // Toast.makeText(context, "articles before : "+numberOfArticlesBefore, Toast.LENGTH_SHORT).show();

        BfmRss bfmRss = new BfmRss(context, articleAdapter, false, loader);
        Rss01net rss01net = new Rss01net(context, articleAdapter, false, loader);
        LemondeRss lemondeRss = new LemondeRss(context, articleAdapter, false, loader);

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




        if (articleAdapter != null && loader != null) {
            articleAdapter.notifyDataSetChanged();
            loader.setRefreshing(false);
        }else{

            numberOfArticlesAfter = articleDAO.getCount();
            // Toast.makeText(context, "articles after : "+numberOfArticlesAfter, Toast.LENGTH_SHORT).show();

            if (numberOfArticlesAfter - numberOfArticlesBefore > 0)
                createNotification(numberOfArticlesAfter - numberOfArticlesBefore);

            articleDAO.close();

        }
        super.onPostExecute(aVoid);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void createNotification(int count) {

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);

        Notification noti = new Notification.Builder(context)
                .setContentTitle("NewsMe")
                .setContentText(count + " articles non lus").setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_nav))
                .setContentIntent(pIntent).build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, noti);

    }
}
