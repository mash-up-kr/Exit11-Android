package io.mashup.exit11;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.mashup.exit11.fragment.MapFragment;

import static android.support.design.widget.BottomSheetBehavior.STATE_COLLAPSED;
import static android.support.design.widget.BottomSheetBehavior.STATE_SETTLING;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.layout_content)
    FrameLayout flContent;

    @BindView(R.id.layout_bottom_sheet)
    ConstraintLayout clBottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

//        MapView mapView = new MapView(this);
//        mapView.setDaumMapApiKey(MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY);
//        ViewGroup viewGroup = findViewById(R.id.layout_content);
//        viewGroup.addView(mapView);

        FragmentManager fm = getSupportFragmentManager();

        fm.beginTransaction()
                .replace(R.id.layout_content, MapFragment.newInstance(null,null))
                .commit();



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
}
