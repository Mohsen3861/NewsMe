package com.esgi.newsme.newsme.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.esgi.newsme.newsme.Adapters.ArticleAdapter;
import com.esgi.newsme.newsme.Models.Article;
import com.esgi.newsme.newsme.Models.User;
import com.esgi.newsme.newsme.R;
import com.esgi.newsme.newsme.SQL.DAO.ArticleDAO;
import com.esgi.newsme.newsme.Utils.UserUtils;
import com.esgi.newsme.newsme.Xml.AllRss;
import com.esgi.newsme.newsme.Xml.BfmRss;
import com.esgi.newsme.newsme.Xml.LemondeRss;
import com.esgi.newsme.newsme.Xml.Rss01net;

import java.util.ArrayList;


/**
 * Created by mohsen raeisi on 23/05/2016.
 */
public class HomeFragment extends Fragment {

    ListView mainNewsList;
    ArticleAdapter articleAdapter;
    private SwipeRefreshLayout swipeContainer;
    int source;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.home_fragment, container, false);

        assignViews(rootView);

        swipeContainer.post(new Runnable() {
                                @Override
                                public void run() {
                                    swipeContainer.setRefreshing(true);

                                    prepareList();
                                }
                            }
        );


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


    public void assignViews(View view) {
        mainNewsList = (ListView) view.findViewById(R.id.listViewMain);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);

    }


    public void prepareList() {
        articleAdapter = new ArticleAdapter(getActivity());

        Bundle bundle = getArguments();
        int source = bundle.getInt("source");
        LemondeRss lemondeRss = new LemondeRss(getActivity(), articleAdapter, true, swipeContainer);
        BfmRss bfmRss = new BfmRss(getActivity(), articleAdapter, true, swipeContainer);
        Rss01net rss01net = new Rss01net(getActivity(), articleAdapter, true, swipeContainer);

        switch (source) {
            case 0:
                lemondeRss.execute();
                break;
            case 1:
                bfmRss.execute();
                break;
            case 2:
                rss01net.execute();
                break;
            case 3:

                AllRss allRss = new AllRss(articleAdapter, getActivity(), swipeContainer);

                allRss.execute();
                break;

            case 4:
                getFavorits();

        }


        mainNewsList.setAdapter(articleAdapter);

        mainNewsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });


    }

    public void getFavorits() {
        ArticleDAO articleDAO = new ArticleDAO(getActivity());
        articleDAO.open();

        User user = UserUtils.getUserInfo(getActivity());

        ArrayList<Article> favoritArticles = new ArrayList<>();
        if (user != null) {
            favoritArticles = articleDAO.getFavoritArticles(user.getId());
        }
        articleAdapter.addItemsCollection(favoritArticles);
        articleAdapter.notifyDataSetChanged();

        swipeContainer.setRefreshing(false);
    }


}
