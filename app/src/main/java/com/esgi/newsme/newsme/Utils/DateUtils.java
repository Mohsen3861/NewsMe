package com.esgi.newsme.newsme.Utils;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mohsen raeisi on 28/05/2016.
 */
public class DateUtils {

    private static final double SECOND_MILLIS = 1000;
    private static final double MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final double HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final double DAY_MILLIS = 24 * HOUR_MILLIS;


    public static String getTimeAgo(long time, Context ctx) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = new Date().getTime();
        if (time > now || time <= 0) {
            return null;
        }


        final double diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "Maintenant";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "Il y a une minute";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return "Il y a " + (int) Math.ceil(diff / MINUTE_MILLIS) + " minutes";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "Il y a une heure";
        } else if (diff < 24 * HOUR_MILLIS) {
            return "Il y a " + (int) Math.ceil((diff / HOUR_MILLIS)) + " heures";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "Hier";
        } else {
            return "Il y a " + (int) Math.ceil((diff / DAY_MILLIS)) + " jours";
        }
    }

    public static String getDateText(Date date) {

        String day;
        String hour;

        SimpleDateFormat formatDay = new SimpleDateFormat("dd/MM");
        SimpleDateFormat formatHour = new SimpleDateFormat("HH:mm");

        double time = date.getTime();
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = new Date().getTime();
        if (time > now || time <= 0) {
            return null;
        }

        final double diff = now - time;

        if (diff < 24 * HOUR_MILLIS) {
            day = "Aujourd'hui";
        }
        else if (diff < 48 * HOUR_MILLIS) {
            day = "Hier";
        }else {
            day = formatDay.format(date);
        }

        hour = formatHour.format(date);
        return day + " • " + hour;
    }

}
