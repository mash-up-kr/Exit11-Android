package io.mashup.exit11.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import io.mashup.exit11.R;
import io.mashup.exit11.ui.fragment.MapFragment;
import io.mashup.exit11.util.CommonUtil;
import io.mashup.exit11.util.PermissionUtils;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int MESSAGE_BACKKEY_TIMEOUT = 2;
    private static final long TIMEOUT_BACKKEY_DELAY = 2000;
    private boolean isBackPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        if (PermissionUtils.checkPermissions(this,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION) && CommonUtil.isNetworkWorking(this)) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.layout_content, MapFragment.newInstance())
                    .commit();
        }
        else {
            PermissionUtils.requestPermissions(this,
                    0,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.layout_content, MapFragment.newInstance())
                        .commit();
        }
    }

    @Override
    public void onBackPressed() {

        if (!isBackPressed) {
            isBackPressed = true;
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            backKeyHandler.sendEmptyMessageDelayed(MESSAGE_BACKKEY_TIMEOUT, TIMEOUT_BACKKEY_DELAY);
        }
        else {
            backKeyHandler.removeMessages(MESSAGE_BACKKEY_TIMEOUT);
            super.onBackPressed();
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler backKeyHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_BACKKEY_TIMEOUT:
                    isBackPressed = false;
                    break;
            }
        }
    };
}
