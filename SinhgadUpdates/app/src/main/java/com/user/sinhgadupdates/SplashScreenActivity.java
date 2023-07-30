package com.user.sinhgadupdates;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashScreenActivity extends AppCompatActivity {

    private int SPLASH_SCREEN_TIMEOUT = 3000;
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor editor;
    private String TAG = "SplashScreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mPrefs = getSharedPreferences("credentials", MODE_PRIVATE);
        editor = mPrefs.edit();

        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
//                        if ((mPrefs.getString("mobile", "").equals("") || mPrefs.getString("mobile", "") == null)
//                                && (mPrefs.getString("password", "").equals("") || mPrefs.getString("password", "") == null))

                        Log.e(TAG, "Mobile: " + mPrefs.getString("mobile", null));
                        Log.e(TAG, "Password: " + mPrefs.getString("password", null));

//                        if (mPrefs.getString("mobile", null) == null || mPrefs.getString("password", null) == null)
//                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                        else
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                },
                SPLASH_SCREEN_TIMEOUT
        );
    }
}