package com.esgi.newsme.newsme.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.esgi.newsme.newsme.SQL.DAO.ArticleDAO;
import com.esgi.newsme.newsme.Xml.AllRss;

/**
 * Created by mohsen raeisi on 03/07/2016.
 */
public class NewsReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        AllRss allRss = new AllRss(null,context,null);
        allRss.execute();


    }
}
