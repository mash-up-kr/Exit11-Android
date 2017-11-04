package io.mashup.exit11.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tak on 2017. 11. 5..
 */

class Member {
    @SerializedName("MemberId")
    private int memberId;

    @SerializedName("MemberLoginId")
    private String memberLoginId;

    @SerializedName("MemberName")
    private String memberName;

    @SerializedName("MemberProfileUrl")
    private String memberProfileUrl;

    @SerializedName("Status")
    private int status;

    @SerializedName("InsertDate")
    private String insertDate;

    @SerializedName("EditDate")
    private String editDate;
}
