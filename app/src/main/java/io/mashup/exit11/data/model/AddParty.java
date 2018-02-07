package io.mashup.exit11.data.model;

import java.util.List;

/**
 * Created by jonghunlee on 2018. 2. 3..
 */

public class AddParty {

    @Menu
    private int menu;

    private List<String> hashTags;

    private PartyDetail partyDetail;

    public AddParty(@Menu int menu, List<String> hashTags, PartyDetail partyDetail) {
        this.menu = menu;
        this.hashTags = hashTags;
        this.partyDetail = partyDetail;
    }

    @Menu
    public int getMenu() {
        return menu;
    }

    public List<String> getHashTags() {
        return hashTags;
    }

    public PartyDetail getPartyDetail() {
        return partyDetail;
    }
}
