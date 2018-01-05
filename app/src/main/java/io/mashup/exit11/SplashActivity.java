package io.mashup.exit11;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import io.mashup.exit11.ui.activity.LoginActivity;
import io.mashup.exit11.ui.activity.MainActivity;

public class SplashActivity extends Activity {
    private final int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        ImageView iv = findViewById(R.id.splash_imageView);

            Glide.with(this)
                    .asGif()
                    .load(R.raw.splash2)
                    .into(iv);

//        Glide.with(SplashActivity.this)
//                .load(R.raw.splash)
//                .into(imageViewTarget);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivity();
            }
        }, SPLASH_TIME_OUT);
    }

    //change
    private void startMainActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
