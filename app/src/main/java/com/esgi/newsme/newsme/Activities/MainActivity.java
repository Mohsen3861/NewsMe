package com.esgi.newsme.newsme.Activities;

import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.newsme.newsme.Commun.Constants;
import com.esgi.newsme.newsme.Fragments.HomeFragment;
import com.esgi.newsme.newsme.Models.User;
import com.esgi.newsme.newsme.R;
import com.esgi.newsme.newsme.Utils.UserUtils;
import com.esgi.newsme.newsme.recievers.NewsReciever;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //mettre à 0 le nombre d'artciles non lus
        SharedPreferences.Editor editor = getSharedPreferences("ARTICLES", MODE_PRIVATE).edit();
        editor.putInt("unreads", 0);
        editor.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if(savedInstanceState == null){
            HomeFragment home = new HomeFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("source" , 4);
            home.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, home).commit();
        }

        User user = UserUtils.getUserInfo(this);

        View headerView = navigationView.getHeaderView(0);
        TextView username = (TextView) headerView.findViewById(R.id.nav_header_text);
        if(user !=null) {
            username.setText(user.getPrenom() + " " + user.getNom());
        }


        initService();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_lemonde) {
            HomeFragment home = new HomeFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("source" , 0);
            home.setArguments(bundle);

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, home).commit();

            setTitle(R.string.lemonde);

        } else if (id == R.id.nav_bfm) {

            HomeFragment home = new HomeFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("source" , 1);
            home.setArguments(bundle);

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, home).commit();

            setTitle(R.string.BFM);

        } else if (id == R.id.nav_01net) {

            HomeFragment home = new HomeFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("source" , 2);
            home.setArguments(bundle);

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, home).commit();

            setTitle(R.string.net);

        }else if(id == R.id.nav_20mn){

            HomeFragment home = new HomeFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("source" , 3);
            home.setArguments(bundle);

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, home).commit();

            setTitle(R.string.minuite);

        }else if(id == R.id.nav_all){
            HomeFragment home = new HomeFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("source" , 4);
            home.setArguments(bundle);

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, home).commit();

            setTitle(R.string.app_name);

        }else if(id == R.id.nav_favorit){
            HomeFragment home = new HomeFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("source" , 5);
            home.setArguments(bundle);

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, home).commit();

            setTitle("Vos articles favoris");

        }else if(id == R.id.nav_connexion){
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void initService(){
        SharedPreferences prefs = getSharedPreferences("ALARM",MODE_PRIVATE);

        boolean isSet = prefs.getBoolean("alarm",false);
        if(!isSet) {
            PendingIntent pendingIntent;
            Intent alarmIntent = new Intent(MainActivity.this, NewsReciever.class);
            pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);

            //clearing
            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            manager.cancel(pendingIntent);

            //setting
            manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            int interval = Constants.BACKGROUND_REFRESH_PERIOD;
            manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);

            SharedPreferences.Editor editor = getSharedPreferences("ALARM", MODE_PRIVATE).edit();
            editor.putBoolean("alarm", true);
            editor.commit();
        }

    }
}
