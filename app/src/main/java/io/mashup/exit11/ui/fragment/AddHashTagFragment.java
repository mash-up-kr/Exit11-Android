package io.mashup.exit11.ui.fragment;

import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.mashup.exit11.R;
import io.mashup.exit11.ui.adapter.HashTagAdapter;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by jonghunlee on 2017. 12. 18..
 */

public class AddHashTagFragment extends Fragment {

    public static AddHashTagFragment newInstance() {
        return new AddHashTagFragment();
    }

    @BindView(R.id.edit_add_hash_tag)
    EditText etAddHashTag;

    @BindView(R.id.list_hash_tag)
    RecyclerView rvHashTag;

    private PublishSubject<String> hashTagSubject = PublishSubject.create();
    private HashTagAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_hash_tag, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    public void init() {
        adapter = new HashTagAdapter();
        rvHashTag.setAdapter(adapter);

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getActivity());
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        rvHashTag.setLayoutManager(layoutManager);

        etAddHashTag.setOnEditorActionListener((tv, actionId, event) -> {
            switch (actionId) {
                case EditorInfo.IME_ACTION_DONE:
                    if (tv.getText().length() == 0) {
                        return false;
                    }

                    if (adapter.getItemCount() == 3) {
                        return false;
                    }

                    String hashTag = tv.getText().toString();
                    adapter.add(hashTag);
                    hashTagSubject.onNext(hashTag);
                    tv.setText("");

                    return true;
            }

            return false;
        });
    }

    public Observable<String> getHashTagSubject() {
        return hashTagSubject;
    }
}
