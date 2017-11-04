package io.mashup.exit11.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

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

    @BindView(R.id.layout_bottom_sheet)
    ConstraintLayout clBottomSheet;

    ApiService apiService;
    PartyRepository repository;

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
