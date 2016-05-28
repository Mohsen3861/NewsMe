package com.esgi.newsme.newsme.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.esgi.newsme.newsme.Activities.articleActivity;
import com.esgi.newsme.newsme.Adapters.ArticleAdapter;
import com.esgi.newsme.newsme.Models.Article;
import com.esgi.newsme.newsme.R;
import com.esgi.newsme.newsme.Xml.AllRss;
import com.esgi.newsme.newsme.Xml.BfmRss;
import com.esgi.newsme.newsme.Xml.ReadRss;
import com.esgi.newsme.newsme.Xml.Rss01net;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * Created by mohsen raeisi on 23/05/2016.
 */
public class HomeFragment extends Fragment {

    ListView mainNewsList ;
    ArticleAdapter articleAdapter;
    private SwipeRefreshLayout swipeContainer;
    int source;

    public HomeFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.home_fragment, container, false);

        assignViews(rootView);
        prepareList();

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                prepareList();
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        return rootView;
    }


    public void assignViews(View view){
        mainNewsList = (ListView) view.findViewById(R.id.listViewMain);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);

    }



    public void prepareList(){
        articleAdapter = new ArticleAdapter(getActivity());

        Bundle bundle = getArguments();
        int source = bundle.getInt("source");
        ReadRss readRss = new ReadRss(getActivity(),articleAdapter , true);
        BfmRss bfmRss = new BfmRss(getActivity(), articleAdapter,true );
        Rss01net rss01net = new Rss01net(getActivity(), articleAdapter,true);

        switch (source){
            case 0 :
                readRss.execute();
                break;
            case 1 :
                bfmRss.execute();
                break;
            case 2 :
                rss01net.execute();
                break;
            case 3 :

                AllRss allRss = new AllRss(articleAdapter,getActivity());

                allRss.execute();
                break;
        }
/*




*/



        mainNewsList.setAdapter(articleAdapter);

        mainNewsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });

        swipeContainer.setRefreshing(false);


    }
}
