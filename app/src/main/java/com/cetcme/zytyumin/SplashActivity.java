package com.cetcme.zytyumin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;


public class SplashActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        android:theme="@style/SplashTheme"
//        setContentView(R.layout.activity_splash);


        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();

    }

}
