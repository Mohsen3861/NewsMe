package com.esgi.newsme.newsme.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.esgi.newsme.newsme.Models.User;

/**
 * Created by mohsen raeisi on 02/07/2016.
 */
public class UserUtils {
    public static String  MY_PREFS_NAME ="USER_INFO";

    public static void saveUserInfo(User user, Context context){

        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE).edit();
        editor.putString("id", user.getId());
        editor.putString("nom", user.getNom());
        editor.putString("prenom", user.getPrenom());
        editor.putString("email", user.getEmail());
        editor.putString("password", user.getMdp());
        editor.commit();
    }

    public static User getUserInfo(Context context){
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE);

        User user = new User();
        user.setId(prefs.getString("id",null));
        user.setNom(prefs.getString("nom",null));
        user.setPrenom(prefs.getString("prenom",null));
        user.setEmail(prefs.getString("email",null));
        user.setMdp(prefs.getString("password",null));

        if(user.getId()!=null){
            return user;
        }else
            return null;
    }
}
