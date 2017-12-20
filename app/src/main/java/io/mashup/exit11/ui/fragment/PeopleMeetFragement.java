package io.mashup.exit11.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.mashup.exit11.R;

public class PeopleMeetFragement extends Fragment {

    @BindView(R.id.people_num_seekbar)
    SeekBar seekBar;

    @BindView(R.id.two)
    TextView two;

    @BindView(R.id.three)
    TextView three;

    @BindView(R.id.four)
    TextView four;

    private int peopleNumber = 3;

    private OnAdapterItemClickLIstener listener;

    public PeopleMeetFragement() {}
    public static PeopleMeetFragement newInstance() {
        PeopleMeetFragement fragment = new PeopleMeetFragement();

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_people_meet_fragement, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK) {
                    getFragmentManager().popBackStack();
                    return true;
                }
                return false;
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                if(progress > 25 && progress < 75) {
                    seekBar.setProgress(50);
                    peopleNumber = 2;

                    two.setTextColor(Color.parseColor("#808080"));
                    two.setTextSize(15);
                    four.setTextColor(Color.parseColor("#808080"));
                    four.setTextSize(15);
                    three.setTextColor(Color.parseColor("#ae1f24"));
                    three.setTextSize(17);
                } else if(progress <= 25) {
                    seekBar.setProgress(25);
                    peopleNumber = 3;

                    two.setTextColor(Color.parseColor("#ae1f24"));
                    two.setTextSize(17);
                    four.setTextColor(Color.parseColor("#808080"));
                    four.setTextSize(15);
                    three.setTextColor(Color.parseColor("#ae1f24"));
                    three.setTextSize(15);
                } else {
                    seekBar.setProgress(100);
                    peopleNumber = 4;

                    two.setTextColor(Color.parseColor("#808080"));
                    two.setTextSize(15);
                    four.setTextColor(Color.parseColor("#ae1f24"));
                    four.setTextSize(17);
                    three.setTextColor(Color.parseColor("#808080"));
                    three.setTextSize(15);
                }
            }
        });
    }

    public interface OnAdapterItemClickLIstener {
        void onAdapterItemClick(int num);
    }

    public void setOnAdapterItemClickListener(OnAdapterItemClickLIstener listener) {
        this.listener = listener;
    }

    @OnClick(R.id.ok)
    public void okClicked() {
        listener.onAdapterItemClick(peopleNumber);
        getFragmentManager().popBackStack();
    }
}
