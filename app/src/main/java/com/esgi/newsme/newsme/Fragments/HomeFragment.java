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
        //  ArrayList<Article> newsList = new ArrayList<>();
/*
        Article article = new Article();
        article.setTitle("L’Ile-de-France adopte le principe des tests salivaires de détection de drogue dans les lycées\n");
        article.setSource("lemonde.fr");
        article.setDescription("Il n’est pas sûr que Grigory Rodchenkov soit un lecteur assidu de John Le Carré. L’histoire que l’ancien patron du laboratoire antidopage de Moscou a racontée aux journalistes du New York Times, publiée le 12 mai, aurait pourtant pu constituer un excellent scénario pour le spécialiste britannique des romans d’espionnage.\n"
        );
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.image_test);
        article.setImgUrl(R.drawable.image_test+"");
        article.setImage(largeIcon);
        article.setSaved(false);

        Article article2 = new Article();
        article2.setTitle("Conflit israélo-palestinien : la conférence de Paris aura lieu le 3 juin, en présence de Kerry\n");
        article2.setSource("lemonde.fr");
        article2.setDescription("Ce sont peut-être les dernières images d’Adelma. Elles ont été tournées le mardi 8 mars sur l’esplanade qui borde la gare de Bruxelles-Central. Pour la Journée internationale des droits des femmes, plusieurs associations féministes s’étaient rassemblées au cœur de la capitale belge. Aux discours qui se succédaient sur une tribune de fortune");
        Bitmap largeIcon2 = BitmapFactory.decodeResource(getResources(), R.drawable.image_test3);
        article2.setImgUrl(R.drawable.image_test3+"");
        article2.setImage(largeIcon2);

        article2.setSaved(false);
*/

        Bundle bundle = getArguments();
        int source = bundle.getInt("source");
        ReadRss readRss = new ReadRss(getActivity(),articleAdapter);
        BfmRss bfmRss = new BfmRss(getActivity(), articleAdapter );
        Rss01net rss01net = new Rss01net(getActivity(), articleAdapter);

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
                readRss.execute();
                bfmRss.execute();
                rss01net.execute();
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
