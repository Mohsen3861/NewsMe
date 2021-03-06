package com.esgi.newsme.newsme.Utils;

import android.text.Html;

/**
 * Created by mohsen raeisi on 26/05/2016.
 */
public class StringUtils {

    public static String html2text(String html) {
        html =  html.replaceAll("s/<(.*?)>//g","");
        html = html.replaceAll("<img.+?>", "");
        html = html.replaceAll("[\\r\\n]+", "");
        html = html.replaceAll("&"+"nbsp;", " ");
        html = html.replaceAll(String.valueOf((char) 160), " ");
        html = html.trim();

        return Html.fromHtml(html).toString();

    }
}
