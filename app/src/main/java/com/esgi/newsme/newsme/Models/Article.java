package com.esgi.newsme.newsme.Models;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by mohsen raeisi on 14/05/2016.
 */
public class Article implements Serializable ,Comparable<Article>{

    private String title;
    private String description;
    private String source;
    private Date dateCreation;
    private Date dateArticle;
    private String url;
    private String id;
    private String imgUrl;
    private Bitmap image;
    private String userId;

    private boolean saved;

    public Article(String id, String title, String description, String url, String imgUrl, Date dateArticle, String source,Boolean saved) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.url = url;
        this.imgUrl = imgUrl;
        this.dateArticle = dateArticle;
        this.source = source;
        this.saved = saved;
    }


    //KEY_ID, KEY_TITLE, KEY_DESCRIPTION, KEY_URL, KEY_URL_IMAGE, KEY_DATE

    public Article(){}

    public Article(String title, String description, Date dateCreation, Date dateArticle, String url, String imgUrl, Bitmap image,boolean
                   saved) {
        this.title = title;
        this.description = description;
        this.dateCreation = dateCreation;
        this.dateArticle = dateArticle;
        this.url = url;
        this.imgUrl = imgUrl;
        this.image = image;
        this.saved = saved;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateArticle() {
        return dateArticle;
    }

    public void setDateArticle(Date dateArticle) {
        this.dateArticle = dateArticle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    @Override
    public int compareTo(Article another) {
         return (getDateArticle().compareTo(another.getDateArticle()));
    }
}
