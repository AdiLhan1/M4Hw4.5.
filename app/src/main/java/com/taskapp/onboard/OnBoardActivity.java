package com.taskapp.onboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.taskapp.MainActivity;
import com.taskapp.R;

public class OnBoardActivity extends AppCompatActivity {
    Button press;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);
        initViewPager();
    }
    private void initViewPager() {
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), 0));

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager, true);
    }

    public void onClick(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
