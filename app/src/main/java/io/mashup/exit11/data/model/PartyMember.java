package io.mashup.exit11.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tak on 2017. 11. 5..
 */

public class PartyMember {
    @SerializedName("PartyMemberId")
    private int partyMemberId;

    @SerializedName("PartyId")
    private int partyId;

    @SerializedName("MemberId")
    private int memberId;

    @SerializedName("MemberLevel")
    private int memberLevel;

    @SerializedName("MemberStatus")
    private int memberStatus;

    @SerializedName("InsertDate")
    private String insertDate;

    @SerializedName("EditDate")
    private String editDate;

    @SerializedName("detailInfo")
    private Member detailInfo;

    public int getPartyMemberId() {
        return partyMemberId;
    }

    public int getPartyId() {
        return partyId;
    }

    public int getMemberId() {
        return memberId;
    }

    public int getMemberLevel() {
        return memberLevel;
    }

    public int getMemberStatus() {
        return memberStatus;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public String getEditDate() {
        return editDate;
    }

    public Member getDetailInfo() {
        return detailInfo;
    }
}
