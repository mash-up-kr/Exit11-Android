package io.mashup.exit11.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.mashup.exit11.R;
import io.mashup.exit11.data.model.Party;
import io.mashup.exit11.data.remote.NetworkManager;
import io.mashup.exit11.data.remote.api.ApiService;
import io.mashup.exit11.data.repository.PartyRepository;
import io.mashup.exit11.ui.fragment.MapFragment;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;

import static android.support.design.widget.BottomSheetBehavior.STATE_COLLAPSED;
import static android.support.design.widget.BottomSheetBehavior.STATE_SETTLING;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int MESSAGE_BACKKEY_TIMEOUT = 2;
    private static final long TIMEOUT_BACKKEY_DELAY = 2000;
    private boolean isBackPressed = false;

    @BindView(R.id.layout_bottom_sheet)
    ConstraintLayout clBottomSheet;

    ApiService apiService;
    PartyRepository repository;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_BACKKEY_TIMEOUT:
                    isBackPressed = false;
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        repository = new PartyRepository();
        apiService = NetworkManager.getInstance().getService();

        getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, MapFragment.newInstance()).commit();

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

            }
        });
    }


    @Override
    public void onBackPressed() {
        if (!isBackPressed) {
            isBackPressed = true;
            Toast.makeText(MainActivity.this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(MESSAGE_BACKKEY_TIMEOUT, TIMEOUT_BACKKEY_DELAY);
        } else {
            mHandler.removeMessages(MESSAGE_BACKKEY_TIMEOUT);
            super.onBackPressed();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        Single<List<Party>> partys = repository.getPartys();
        partys.subscribe(new Consumer<List<Party>>() {
            @Override
            public void accept(List<Party> parties) throws Exception {
                Log.d(TAG, "Success " + parties.get(0).getId() + "");
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.d(TAG, "error " + throwable.getMessage());
            }
        });

    }
}
