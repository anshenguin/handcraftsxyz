package com.kinitoapps.handcraftsxyz.activities;

import android.net.Uri;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kinitoapps.handcraftsxyz.fragments.DefaultDiscoverFragment;
import com.kinitoapps.handcraftsxyz.fragments.DiscoverProductFragment;
import com.kinitoapps.handcraftsxyz.fragments.DiscoverStoreFragment;
import com.kinitoapps.handcraftsxyz.R;

import java.util.ArrayList;
import java.util.Random;

public class DiscoverActivity extends AppCompatActivity implements DefaultDiscoverFragment.OnFragmentInteractionListener,DiscoverStoreFragment.OnFragmentInteractionListener,
        DiscoverProductFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);
        android.support.v4.app.Fragment fragment = null;
        Class fragmentClass = null;
        fragmentClass = DefaultDiscoverFragment.class;
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView mTitle = findViewById(R.id.toolbar_title);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        try {
            fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, fragment).commit();
        FloatingActionButton fab = findViewById(R.id.fab);
//        centerTitle();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.Fragment fragment = null;
                Class fragmentClass = null;
                Random random = new Random();
                if(random.nextInt(2)==0){
                    fragmentClass = DiscoverProductFragment.class;
                    try {
                        fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.main_content, fragment).commit();
                }
                else {
                    fragmentClass = DiscoverStoreFragment.class;
                    try {
                        fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.main_content, fragment).commit();
                }

            }
        });
    }

    private void centerTitle() {
        ArrayList<View> textViews = new ArrayList<>();

        getWindow().getDecorView().findViewsWithText(textViews, getTitle(), View.FIND_VIEWS_WITH_TEXT);

        if(textViews.size() > 0) {
            AppCompatTextView appCompatTextView = null;
            if(textViews.size() == 1) {
                appCompatTextView = (AppCompatTextView) textViews.get(0);
            } else {
                for(View v : textViews) {
                    if(v.getParent() instanceof Toolbar) {
                        appCompatTextView = (AppCompatTextView) v;
                        break;
                    }
                }
            }

            if(appCompatTextView != null) {
                ViewGroup.LayoutParams params = appCompatTextView.getLayoutParams();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                appCompatTextView.setLayoutParams(params);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    appCompatTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
            }
        }
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
