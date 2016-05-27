package com.esgi.newsme.newsme.Xml;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.esgi.newsme.newsme.Adapters.ArticleAdapter;
import com.esgi.newsme.newsme.Models.Article;
import com.esgi.newsme.newsme.R;
import com.esgi.newsme.newsme.Utils.StringUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Promobile on 26/05/2016.
 */
public class Rss01net extends AsyncTask<Void, Void, Void> {
    Context context;
    URL url;
    ArrayList<Article> articles = new ArrayList<>();
    ArticleAdapter articleAdapter;

    public Rss01net(Context context ,  ArticleAdapter adapter){
        this.context = context;
        articleAdapter = adapter;


    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            ParseXMl(GetData());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void ParseXMl(Document data) throws ParseException {
        if (data != null){
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();
            for (int i =0; i<items.getLength(); i++){
                Node currentChild = items.item(i);
                if (currentChild.getNodeName().equalsIgnoreCase("item")){
                    Article article = new Article();
                    NodeList itemChilds = currentChild.getChildNodes();
                    for (int j = 0; j<itemChilds.getLength(); j++){
                        Node current = itemChilds.item(j);
                        if (current.getNodeName().equalsIgnoreCase("title")){
                            article.setTitle(current.getTextContent().trim());

                        }else if (current.getNodeName().equalsIgnoreCase("description")){
                            article.setDescription(StringUtils.html2text(current.getTextContent()));
                        }else if (current.getNodeName().equalsIgnoreCase("pubDate")){

                            String dateString = current.getTextContent();
                            String left = dateString.substring(0, dateString.length() - 6);

                            DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss",Locale.ENGLISH);
                            Date date = formatter.parse(left);
                            article.setDateArticle(date);

                        }else if (current.getNodeName().equalsIgnoreCase("guid")){
                            article.setUrl(current.getTextContent());
                        }else if (current.getNodeName().equalsIgnoreCase("enclosure")){
                            String uri = current.getAttributes().item(0).getTextContent();
                            article.setImgUrl(uri);
                        }

                    }
                    article.setSource("01-net");

                    if(article.getImgUrl() != null && !article.getImgUrl().equals("") &&
                            article.getDescription() != null && !article.getDescription().equals("") )
                    articles.add(article);

                    /*
                    Log.e("title-Rss01net", article.getTitle());
                    Log.e("description-Rss01net", article.getDescription());
                  //  Log.e("pubDate-Rss01net", article.getPubDate());
                    Log.e("guid-Rss01net", article.getUrl());
                    Log.e("enclosure-Rss01net", article.getImgUrl());
*/



                }
            }
            //Log.e("ROOt", data.getDocumentElement().getNodeName());
        }

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    public Document GetData(){
        String adress = context.getString(R.string.url_01net);

        try {
            url = new URL(adress);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            Document doc = builder.parse(inputStream);
            return doc;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        articleAdapter.addItemsCollection(articles);
        super.onPostExecute(aVoid);
    }
}
