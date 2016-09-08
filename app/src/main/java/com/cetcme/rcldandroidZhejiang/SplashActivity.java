package com.cetcme.rcldandroidZhejiang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


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
