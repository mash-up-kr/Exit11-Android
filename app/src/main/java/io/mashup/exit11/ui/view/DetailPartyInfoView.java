package io.mashup.exit11.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.mashup.exit11.R;

/**
 * Created by Tak on 2017. 12. 20..
 */

public class DetailPartyInfoView extends FrameLayout {

    @BindView(R.id.party_info_active)
    ImageView infoActiveImg;

    @BindView(R.id.question)
    TextView questionTv;

    @BindView(R.id.result)
    TextView resultTv;

    public DetailPartyInfoView(@NonNull Context context) {
        super(context);
        init(null);
    }

    public DetailPartyInfoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public DetailPartyInfoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        View view = inflate(getContext(), R.layout.view_detail_party_info, this);
        ButterKnife.bind(this, view);


    }

    public void setActiveImg(int img) {
        infoActiveImg.setImageResource(img);
    }

    public void setQuestion(String question) {
        questionTv.setText(question);
    }

    public void setResult(String result) {
        resultTv.setText(result);
    }
}
