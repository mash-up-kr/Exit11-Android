package io.mashup.exit11.data.model;

/**
 * Created by jonghunlee on 2018. 2. 3..
 */

public class PartyDetail {

    private String date;
    private String address;
    private int partyMember;

    public PartyDetail(String date, String address, int partyMember) {
        this.date = date;
        this.address = address;
        this.partyMember = partyMember;
    }

    public String getDate() {
        return date;
    }

    public String getAddress() {
        return address;
    }

    public int getPartyMember() {
        return partyMember;
    }
}
