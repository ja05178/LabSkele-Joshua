package com.example.labskeletest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements Favorites.OnFragmentInteractionListener, Map.OnFragmentInteractionListener,List.OnFragmentInteractionListener {
    static String uniqueID = null;
    private static final String PREF_UNIQUE_ID = "PREF_UNOQUE_ID";
//    DBConfiguration dbc = new DBConfiguration();
    static DBAccess dba = new DBAccess();
    public static ArrayList<Lab> listOfLabs = new ArrayList<Lab>();
    public static  ArrayList<String> listOfFavorites = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Context context = getApplicationContext();

        if(uniqueID == null){
            SharedPreferences sharedPrefs = context.getSharedPreferences(PREF_UNIQUE_ID, Context.MODE_PRIVATE);
            uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID,null);

            if(uniqueID == null){
                uniqueID = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(PREF_UNIQUE_ID, uniqueID);
                editor.commit();

                dba.saveUser(uniqueID);
            }
        }



        populateLabList("CEIT");


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
         tabLayout.addTab(tabLayout.newTab());
         tabLayout.addTab(tabLayout.newTab());
         tabLayout.addTab(tabLayout.newTab());
         tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewpager = (ViewPager) findViewById(R.id.pager);
       // tabLayout.setupWithViewPager(viewpager, true);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewpager.setAdapter(adapter);
        viewpager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewpager.setCurrentItem(1);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public String getUUID(){
        return uniqueID;
    }

    public void refreshFavoritesList(){
        listOfFavorites = populateFavoritesList(uniqueID);
    }

    public ArrayList<String> populateFavoritesList(String UUID){
        ArrayList<String> listOfFavorites = new ArrayList<String>();
        try {
            ResultSet rs = dba.getFavorites(uniqueID);
            while (rs.next()) {
                String lab = rs.getString("LabID");
                //lab = lab.substring(lab.length() - 4);

                listOfFavorites.add(lab);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return listOfFavorites;
    }

    public static void  populateLabList(String building){
        dba = new DBAccess();

        try {
            ResultSet rs = dba.getLabs(building);

            while (rs.next()) {
                String lab = rs.getString("LabID");
                //lab = lab.substring(lab.length() - 4);

                listOfLabs.add(new Lab(lab));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
