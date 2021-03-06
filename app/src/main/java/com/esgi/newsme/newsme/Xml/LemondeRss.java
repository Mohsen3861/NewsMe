package com.esgi.newsme.newsme.Xml;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.esgi.newsme.newsme.Adapters.ArticleAdapter;
import com.esgi.newsme.newsme.Models.Article;
import com.esgi.newsme.newsme.R;
import com.esgi.newsme.newsme.SQL.DAO.ArticleDAO;

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
 * Created by Promobile on 25/05/2016.
 */
public class LemondeRss extends AsyncTask<Void, Void, Void> {
    Context context;
    URL url;
    ArrayList<Article> articles = new ArrayList<>();
    ArticleAdapter articleAdapter;
    Boolean shouldLoad;

    ArticleDAO articleDAO;
    SwipeRefreshLayout loader;

    public LemondeRss(Context context , ArticleAdapter adapter , boolean shouldLoad , SwipeRefreshLayout loader){
        this.context = context;
        articleAdapter = adapter;
        this.shouldLoad = shouldLoad;
        this.loader = loader;

        //inistialize DB
        articleDAO = new ArticleDAO(context);
        articleDAO.open();
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
                            article.setTitle(current.getTextContent());
                        }else if (current.getNodeName().equalsIgnoreCase("description")){
                            article.setDescription(current.getTextContent());
                        }else if (current.getNodeName().equalsIgnoreCase("pubDate")){
                            String dateString = current.getTextContent();
                            String left = dateString.substring(0, dateString.length() - 6);

                            DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
                            Date date = formatter.parse(left);
                            article.setDateArticle(date);

/*

                            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                            Log.e("date" , "Time: " + format.format(date) + " original: " +dateString);
*/
                        }else if (current.getNodeName().equalsIgnoreCase("guid")){
                            article.setUrl(current.getTextContent());
                        }else if (current.getNodeName().equalsIgnoreCase("enclosure")){
                            String uri = current.getAttributes().item(0).getTextContent();
                            article.setImgUrl(uri);
                        }
                        //Log.e("channel", "items :" +article.getDateArticle());
                    }
                    article.setSource(context.getString(R.string.lemonde));

                    if(article.getImgUrl() != null && !article.getImgUrl().equals("") &&
                            article.getDescription() != null && !article.getDescription().equals("") && !article.getDescription().equals("\n\n") ){

                        articleDAO.add(article);
                        articles.add(article);

                    }

                }
            }
        }

    }

    @Override
    protected void onPreExecute() {
      //  progressDialog.show();
        super.onPreExecute();
    }

    public Document GetData(){
        String adress = context.getString(R.string.url_lemonde);
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
        articles = articleDAO.getArticleBySource(context.getResources().getString(R.string.lemonde));

        if(articleAdapter!=null) {
            articleAdapter.addItemsCollection(articles);

            if (shouldLoad) {

                articleAdapter.notifyDataSetChanged();

                if (loader != null)
                    loader.setRefreshing(false);
            }
        }
        articleDAO.close();
        super.onPostExecute(aVoid);
    }
}
