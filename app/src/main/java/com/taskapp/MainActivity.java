package com.taskapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.taskapp.onboard.OnBoardActivity;
import com.taskapp.ui.home.HomeFragment;
import com.taskapp.ui.lottie.LottieFragment;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
        boolean isShown = preferences.getBoolean("isShown", false);
        if (!isShown) {
            startActivity(new Intent(this, OnBoardActivity.class));
            finish();
            return;
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, FormActivity.class), 100);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_lottie:
                        startActivity(new Intent(getBaseContext(), LottieFragment.class));
                        break;
                }
                 return false;
            }
        });
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send,R.id.nav_lottie)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        //initFile();
    }

    private void initFile() {
        File folder = new File(Environment.getExternalStorageDirectory(), "TaskApp");
        folder.mkdir();
        File file = new File(folder, "note.jpg");
        try {
            FileUtils.copyURLToFile(new URL("https://klike.net/uploads/posts/2019-01/1547365376_1.jpg"), file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

     private  boolean sort;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear:
                SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                    preferences.edit().clear().apply();
                }
                finish();
                break;
            case R.id.action_sort:
                if (sort) {
                    Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                    ((HomeFragment) navHostFragment.getChildFragmentManager().getFragments().get(0)).sortList();
                    Log.d("ololo", " sort");
                    sort = false;

                } else {
                    Fragment navHostFragment1 = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                    ((HomeFragment) navHostFragment1.getChildFragmentManager().getFragments().get(0)).initialList();
                    Log.d("ololo", " initlist");
                    sort = true;
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("TAG", "onActivityResult activity");
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navHostFragment.getChildFragmentManager().getFragments().get(0).onActivityResult(requestCode, resultCode, data);
    }
}
