package io.mashup.exit11.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import io.mashup.exit11.R;
import io.mashup.exit11.Util.CommonUtil;
import io.mashup.exit11.Util.PermissionUtils;
import io.mashup.exit11.ui.fragment.MapFragment;

import static android.support.design.widget.BottomSheetBehavior.STATE_COLLAPSED;
import static android.support.design.widget.BottomSheetBehavior.STATE_EXPANDED;
import static android.support.design.widget.BottomSheetBehavior.STATE_SETTLING;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindViews({R.id.check_pizza, R.id.check_chicken, R.id.check_hamburger})
    List<CheckBox> checkBoxes;

    @BindView(R.id.edit_tag)
    EditText etTag;
    @BindView(R.id.list_tag)
    RecyclerView listTag;

    private static final int MESSAGE_BACKKEY_TIMEOUT = 2;
    private static final long TIMEOUT_BACKKEY_DELAY = 2000;
    private boolean isBackPressed = false;

    @BindView(R.id.layout_bottom_sheet)
    RelativeLayout clBottomSheet;

    @BindView(R.id.view_background)
    View viewBackground;

    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        viewBackground.setAlpha(0);
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

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(clBottomSheet);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case STATE_COLLAPSED:
                        break;
                    case STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                if (slideOffset < 0.7f) {
                    viewBackground.setAlpha(slideOffset);
                }
            }
        });
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
        if (checkExpandedBottomSheet()) {
            bottomSheetBehavior.setState(STATE_COLLAPSED);
            return;
        }

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

    private boolean checkExpandedBottomSheet() {
        return bottomSheetBehavior.getState() == STATE_EXPANDED;
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
