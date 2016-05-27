package com.esgi.newsme.newsme.Utils;

import android.text.Html;

/**
 * Created by mohsen raeisi on 26/05/2016.
 */
public class StringUtils {

    public static String html2text(String html) {
            return Html.fromHtml(html).toString();

    }
}
