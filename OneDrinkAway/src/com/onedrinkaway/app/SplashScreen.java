package com.onedrinkaway.app;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.onedrinkaway.R;

public class SplashScreen extends Activity {
    
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2500; // ms
 
    @SuppressLint("InlinedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.activity_splash);
        
        // start animation
        ImageView splashView = (ImageView) findViewById(R.id.imgLogo);
        splashView.setBackgroundResource(R.drawable.splash_animation);
        AnimationDrawable splashAnimation = (AnimationDrawable) splashView.getBackground();
        splashAnimation.start();
        
        new Handler().postDelayed(new Runnable() {
 
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
 
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreen.this, HomePage.class);
                startActivity(i);
 
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
