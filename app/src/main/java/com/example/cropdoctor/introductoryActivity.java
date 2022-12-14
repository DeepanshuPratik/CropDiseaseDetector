package com.example.cropdoctor;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import maes.tech.intentanim.CustomIntent;

public class introductoryActivity extends AppCompatActivity {

    TextView appName;
    //ImageView splashImage;
    LottieAnimationView lottieAnimationView;

    private static  final int NUM_PAGES =3;
    private ViewPager viewPager;
    private ScreenSlidePageAdapter pageAdapter;

    Animation anim;

    private static  int SPLASH_TIME_OUT = 4900;
    SharedPreferences mSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);

        appName = (TextView)findViewById(R.id.appname);
        //splashImage = (ImageView)findViewById(R.id.img);
        lottieAnimationView =  findViewById(R.id.lottie);

        viewPager = findViewById(R.id.pager);
        pageAdapter = new ScreenSlidePageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);

        anim = AnimationUtils.loadAnimation(this,R.anim.o_b_anim);
        viewPager.startAnimation(anim);

        Animation animation = new TranslateAnimation(0, 50,0, 0);
        animation.setDuration(2700);
        animation.setFillAfter(true);

        lottieAnimationView.startAnimation(animation);


//        splashImage.animate().translationY(-2700).setDuration(1000).setStartDelay(4000);
        appName.animate().translationY(-1400).setDuration(2700).setStartDelay(0);
        lottieAnimationView.animate().translationX(2000).setDuration(2000).setStartDelay(2900);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSharedPref = getSharedPreferences("SharedPref",MODE_PRIVATE);
                boolean isFirstTime = mSharedPref.getBoolean("firstTime",true);

                if(isFirstTime) {
                    SharedPreferences.Editor editor = mSharedPref.edit();
                    editor.putBoolean("firstTime",false);
                    editor.commit();

                }
                else
                {
                    Intent i = new Intent(getApplicationContext(),DescriptionPlant1.class);
                    startActivity(i);
                    //CustomIntent.customType(getApplicationContext(),"right-to-left");
                    overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                    finish();
                }
            }
        },SPLASH_TIME_OUT);


    }

    private class  ScreenSlidePageAdapter extends FragmentStatePagerAdapter{

        public ScreenSlidePageAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
                    OnBoardingFragment1 tab1 = new OnBoardingFragment1();
                    return tab1;
                case 1:
                    OnBoardingFragment2 tab2 = new OnBoardingFragment2();
                    return tab2;
                case 2:
                    OnBoardingFragment3 tab3 = new OnBoardingFragment3();
                    return tab3;


            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}