package com.kinitoapps.handcraftsxyz.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kinitoapps.handcraftsxyz.fragments.AllPurposeProductListFragment;
import com.kinitoapps.handcraftsxyz.fragments.HomeFragment;
import com.kinitoapps.handcraftsxyz.fragments.ProductPageFragment;
import com.kinitoapps.handcraftsxyz.R;
import com.kinitoapps.handcraftsxyz.fragments.StorePageFragment;
import com.kinitoapps.handcraftsxyz.fragments.shop_by_categoryFragment;
import com.kinitoapps.handcraftsxyz.helper.SQLiteHandler;
import com.kinitoapps.handcraftsxyz.helper.SessionManager;

import java.util.HashMap;

import static com.bumptech.glide.Glide.with;

public class RootActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnFragmentInteractionListener,shop_by_categoryFragment.OnFragmentInteractionListener,
        ProductPageFragment.OnFragmentInteractionListener, StorePageFragment.OnFragmentInteractionListener, AllPurposeProductListFragment.OnFragmentInteractionListener {
//    private static final String URL_PRODUCTS ="http://handicraft-com.stackstaging.com/myapi/api.php";
//    ImageView tile1,tile2,tile3,tile4,tile5,tile6,tile7,tile8;
    int selected;
    boolean mDrawerItemClicked = false;
    SessionManager session;
    TextView bannername,signup;
    LinearLayout loggedinoptions;
    private SQLiteHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = new SessionManager(getApplicationContext());

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        TextView home_text = findViewById(R.id.home_text);
        TextView shop_by_cat_text = findViewById(R.id.shop_by_cat_text);
        loggedinoptions = findViewById(R.id.drawer_loggedin_options);
        TextView new_arrivals_text = findViewById(R.id.new_arrivals_text);
        TextView discover_text = findViewById(R.id.discover_text);
        signup = findViewById(R.id.signup);
        bannername = findViewById(R.id.banner_name);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RootActivity.this,LoginActivity.class));
            }
        });
        bannername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RootActivity.this,LoginActivity.class));
            }
        });
        home_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected = 1;
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    mDrawerItemClicked = true;
                    drawer.closeDrawer(GravityCompat.START);

                }
            }
        });

        shop_by_cat_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected = 2;
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    mDrawerItemClicked = true;
                    drawer.closeDrawer(GravityCompat.START);
                }
            }
        });

        new_arrivals_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected = 3;
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    mDrawerItemClicked = true;
                    drawer.closeDrawer(GravityCompat.START);
                }
            }
        });
        discover_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected = 4;
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    mDrawerItemClicked = true;
                    drawer.closeDrawer(GravityCompat.START);
                }
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        android.support.v4.app.Fragment fragment = null;
        Class fragmentClass = null;
        fragmentClass = HomeFragment.class;
        try {
            fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
            selected = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, fragment,"homeFragment").addToBackStack("homeFragment").commit();
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                android.support.v4.app.Fragment fragment = null;
                Class fragmentClass = null;

                if(mDrawerItemClicked){
                    if(selected == 1){
                        fragmentClass = HomeFragment.class;
                        try {
                            fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        FragmentManager fragmentManager = getSupportFragmentManager();

                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_left).replace(R.id.main_content, fragment, "homeFragment").addToBackStack("homeFragment").commit();
                    }
                    else if(selected == 2){
                        fragmentClass = shop_by_categoryFragment.class;
                        try {
                            fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_left).replace(R.id.main_content, fragment, "shopByCategory").addToBackStack("shopByCategory").commit();
                    }

                    else if(selected == 3){
                        fragmentClass = AllPurposeProductListFragment.class;
                        try {
                            fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_left).replace(R.id.main_content, fragment, "newArrivals").addToBackStack("newArrivals").commit();
                    }

                    else if(selected == 4){
                        startActivity(new Intent(RootActivity.this,DiscoverActivity.class));
                    }

                    mDrawerItemClicked = false;
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
//        ViewPager viewPager = findViewById(R.id.viewpager);
//        ViewPagerAdapterRootSlider viewPagerAdapter = new ViewPagerAdapterRootSlider(this);
//        tile1 = findViewById(R.id.tile_image1);
//        tile2 = findViewById(R.id.tile_image2);
//        tile3 = findViewById(R.id.tile_image3);
//        tile4 = findViewById(R.id.tile_image4);
//        tile5 = findViewById(R.id.tile_image5);
//        tile6 = findViewById(R.id.tile_image6);
//        tile7 = findViewById(R.id.tile_image7);
//        tile8 = findViewById(R.id.tile_image8);
//        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
//        viewPager.setAdapter(viewPagerAdapter);
//        indicator.setViewPager(viewPager);


    }


    @Override
    protected void onResume() {
        super.onResume();
        if (session.isLoggedIn()) {
            loggedinoptions.setVisibility(View.VISIBLE);
            bannername.setVisibility(View.VISIBLE);
            signup.setVisibility(View.GONE);
            db = new SQLiteHandler(getApplicationContext());
            HashMap<String, String> user = db.getUserDetails();
            String name = user.get("name");
            bannername.setText("Hello "+name+"!");

        }
        else {
            loggedinoptions.setVisibility(View.GONE);
            bannername.setVisibility(View.GONE);
            signup.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            HomeFragment myFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("homeFragment");
            if(myFragment != null && myFragment.isVisible()){
                finish();
            }
            else {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                }
                else
                    finish();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.root, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the HomeFragment/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        mDrawerItemClicked = true;
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
