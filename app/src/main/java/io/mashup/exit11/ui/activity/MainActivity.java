package io.mashup.exit11.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import io.mashup.exit11.R;
import io.mashup.exit11.ui.fragment.MapFragment;

public class MainActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    private MapFragment mapFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mapFragment != null) {
            mapFragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
