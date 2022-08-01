package com.ma.growiser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.ma.growiser.ui.gallery.GalleryFragment;
import com.ma.growiser.ui.help.HelpFragment;
import com.ma.growiser.ui.home.HomeFragment;
import com.ma.growiser.ui.location.LocationFragment;
import com.ma.growiser.ui.setting.SettingFragment;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private AnimatedBottomBar bottomBar;
    private FragmentManager fragmentManager;
    private DrawerLayout drawerLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();
        initViews(savedInstanceState);
//        initNavComponent();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setToolbarNavigationClickListener(view -> drawer.openDrawer(GravityCompat.START));
        toggle.setHomeAsUpIndicator(R.drawable.ic_menu);
        toggle.syncState();

        NavigationView nav = findViewById(R.id.nav_view);

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
//                Class fragmentClass;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        loadFragment(new HomeFragment());
                        break;
                    case R.id.nav_setting:
                        loadFragment(new SettingFragment());
                        break;
                }

                drawerLayout.closeDrawer(GravityCompat.START);

                return true;


            }
        });


    }

//    private void initNavComponent() {
//        NavigationView nav = findViewById(R.id.nav_view);
//
//        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
////                Fragment fragment = null;
////                Class fragmentClass;
//                int id = item.getItemId();
//                switch (item.getItemId()) {
//                    case R.id.nav_home:
//                        loadFragment(new HomeFragment());
//                        break;
//                    case R.id.nav_setting:
//                        loadFragment(new SettingFragment());
//                        break;
//                }
//
//                drawerLayout.closeDrawer(GravityCompat.START);
//
//                return true;
//
//
//            }
//        });
//    }

    private void loadFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }


    @SuppressLint("NonConstantResourceId")
    private void initViews(Bundle savedInstanceState) {

        bottomBar = findViewById(R.id.bottom_nav);

        if(savedInstanceState==null){
            bottomBar.selectTabById(R.id.nav_gallery, true);
            fragmentManager  = getSupportFragmentManager();
            GalleryFragment galleryFragment = new GalleryFragment();
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, galleryFragment).commit();

        }
        bottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex,
                                      @NonNull AnimatedBottomBar.Tab newTab) {
                Fragment fragment = null;
                switch (newTab.getId()){
                    case R.id.nav_gallery:
                        fragment = new GalleryFragment();
                        break;
                    case R.id.nav_location:
                        fragment = new LocationFragment();
                        break;
                    case R.id.nav_help:
                        fragment = new HelpFragment();
                        break;
                }

                if (fragment!=null){
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
                }
                            }

            @Override
            public void onTabReselected(int i, @NonNull AnimatedBottomBar.Tab tab) {
            }
        });

//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//
//        drawer.addDrawerListener(toggle);
//        toggle.setDrawerIndicatorEnabled(false);
//        toggle.setToolbarNavigationClickListener(view -> drawer.openDrawer(GravityCompat.START));
//        toggle.setHomeAsUpIndicator(R.drawable.ic_menu);
//        toggle.syncState();

    }



    public void setToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.END);
        }
        else
        super.onBackPressed();
    }
}