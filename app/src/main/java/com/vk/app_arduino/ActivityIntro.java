package com.vk.app_arduino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ActivityIntro extends AppCompatActivity {

    IntroViewPagerAdapter introViewPagerAdapter ;
    LinearLayout layoutOnboardingIndicator;
    TabLayout tabIndicator;
    Button btnNext;
    int position = 0 ;
    Button btnGetStarted;
    TextView tvSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        layoutOnboardingIndicator = findViewById(R.id.layoutOnboardingIndicator);

        setupScreenItems();

        ViewPager2 introViewPager = findViewById(R.id.screen_viewpager);
        introViewPager.setAdapter(introViewPagerAdapter);

        setupOnboardingIndicators();

    }

    // Adapter для Интро(ViewHolder)
    private void setupScreenItems(){

        List<ScreenItem> screenItems = new ArrayList<>();

        ScreenItem itemConvenientApp = new ScreenItem();
        itemConvenientApp.setTitle("Life without music would be boring and dull.");
        itemConvenientApp.setDescription("Have you ever noticed that people");
        itemConvenientApp.setScreenImg(R.drawable.img1);

        ScreenItem itemManageYourHome = new ScreenItem();
        itemConvenientApp.setTitle("Life without music would be boring and dull.");
        itemConvenientApp.setDescription("Have you ever noticed that people");
        itemConvenientApp.setScreenImg(R.drawable.img2);

        ScreenItem itemHomeProtection = new ScreenItem();
        itemConvenientApp.setTitle("Life without music would be boring and dull.");
        itemConvenientApp.setDescription("Have you ever noticed that people");
        itemConvenientApp.setScreenImg(R.drawable.img3);

        screenItems.add(itemConvenientApp);
        screenItems.add(itemManageYourHome);
        screenItems.add(itemHomeProtection);

        introViewPagerAdapter = new IntroViewPagerAdapter(screenItems);
    }

    private void setupOnboardingIndicators(){
        ImageView[] indicator = new ImageView[introViewPagerAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i<indicator.length; i++){
            indicator[i] = new ImageView(getApplicationContext());
            indicator[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.indicator_default
            ));
            indicator[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicator.addView(indicator[i]);
        }
    }
}