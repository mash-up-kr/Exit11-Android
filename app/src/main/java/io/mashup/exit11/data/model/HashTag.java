package io.mashup.exit11.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jonghunlee on 2018. 2. 3..
 */

public class HashTag {

    @SerializedName("object")
    private List<String> tags;

    public List<String> getTags() {
        return tags;
    }
}
