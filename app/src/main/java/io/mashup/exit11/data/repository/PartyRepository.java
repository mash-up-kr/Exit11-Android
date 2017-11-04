package io.mashup.exit11.data.repository;

import java.util.List;

import io.mashup.exit11.data.model.Party;
import io.mashup.exit11.data.remote.NetworkManager;
import io.mashup.exit11.data.remote.api.ApiService;
import io.reactivex.Single;

/**
 * Created by jonghunlee on 2017. 11. 5..
 */

public class PartyRepository {

    private final ApiService service;

    public PartyRepository() {
        this.service = NetworkManager.getInstance().getService();
    }

    public Single<List<Party>> getPartys() {
        return service.getPartys();
    }

}
