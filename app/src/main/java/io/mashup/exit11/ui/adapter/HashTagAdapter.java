package io.mashup.exit11.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.mashup.exit11.R;

import static io.mashup.exit11.common.Const.HASH_TAG;

/**
 * Created by jonghunlee on 2017. 12. 20..
 */

public class HashTagAdapter extends RecyclerView.Adapter<HashTagAdapter.HashTagViewHolder> {

    private List<String> hashTags = new ArrayList<>();

    @Override
    public HashTagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HashTagViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hash_tag_list, parent, false));
    }

    @Override
    public void onBindViewHolder(HashTagViewHolder holder, int position) {
        holder.bind(hashTags.get(position));
    }

    @Override
    public int getItemCount() {
        return hashTags.size();
    }

    public void add(String hashTag) {
        hashTags.add(HASH_TAG + hashTag);
        notifyDataSetChanged();
    }

    public void clear() {
        hashTags.clear();
        notifyDataSetChanged();
    }

    class HashTagViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_hash_tag)
        TextView tvHashTag;

        HashTagViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(String hashTag) {
            tvHashTag.setText(hashTag);
        }

        @OnClick(R.id.button_delete_hash_tag)
        void onClickRemove() {
            hashTags.remove(getAdapterPosition());
            notifyDataSetChanged();
        }
    }

}
