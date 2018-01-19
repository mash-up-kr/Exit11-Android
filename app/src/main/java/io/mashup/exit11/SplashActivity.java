package io.mashup.exit11;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import io.mashup.exit11.ui.activity.MainActivity;

public class SplashActivity extends Activity {

    private static final int REQUEST_LOCATION_PERMISSION_CODE = 1001;

    private static final int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView iv = findViewById(R.id.splash_imageView);

        Glide.with(this).asGif().load(R.raw.splash2).into(iv);

        new Handler(Looper.getMainLooper()).postDelayed(this::checkLocationPermission, SPLASH_TIME_OUT);
    }

    private void checkLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !checkLocationPermissionGranted()) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                            Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION_CODE);
        }
        else {
            startMainActivity();
        }
    }

    @TargetApi(23)
    private boolean checkLocationPermissionGranted() {
        return checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) ==
               PackageManager.PERMISSION_GRANTED ||
               checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
               PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startMainActivity();
            }
            else {
                finish();
            }
        }
    }

    private void startMainActivity() {
        MainActivity.start(this);
        finish();
    }
}
