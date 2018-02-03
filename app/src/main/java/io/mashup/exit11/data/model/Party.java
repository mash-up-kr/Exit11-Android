package io.mashup.exit11.data.model;

import java.util.List;

/**
 * Created by jonghunlee on 2017. 11. 5..
 */
public class Party {

    private int partyId;
    private int foodId;
    private int partyLeaderId;

    private String partyName;
    private String meetTime;

    private float latitude;
    private float longitude;

    private int tortalPeople;

    private HashTag tags;

    private String detail;

    private String partyLeader;

    private List<String> partyMembers;

    public int getPartyId() {
        return partyId;
    }

    public int getFoodId() {
        return foodId;
    }

    public int getPartyLeaderId() {
        return partyLeaderId;
    }

    public String getPartyName() {
        return partyName;
    }

    public String getMeetTime() {
        return meetTime;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public int getTortalPeople() {
        return tortalPeople;
    }

    public HashTag getTags() {
        return tags;
    }

    public String getDetail() {
        return detail;
    }

    public String getPartyLeader() {
        return partyLeader;
    }

    public List<String> getPartyMembers() {
        return partyMembers;
    }


    @Override
    public String toString() {
        return "Party{" + "partyId=" + partyId + ", foodId=" + foodId + ", partyLeaderId=" + partyLeaderId +
               ", partyName='" + partyName + '\'' + ", meetTime='" + meetTime + '\'' + ", latitude=" +
               latitude + ", longitude=" + longitude + ", tortalPeople=" + tortalPeople + ", tags=" + tags +
               ", detail='" + detail + '\'' + ", partyLeader='" + partyLeader + '\'' + ", partyMembers=" +
               partyMembers + '}';
    }
}
