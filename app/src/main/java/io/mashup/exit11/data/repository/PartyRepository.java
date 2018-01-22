package io.mashup.exit11.data.repository;

import java.util.List;

import javax.inject.Inject;

import io.mashup.exit11.data.model.Party;
import io.mashup.exit11.data.remote.api.ApiService;
import io.reactivex.Single;

/**
 * Created by jonghunlee on 2017. 11. 5..
 */

public class PartyRepository extends RemoteRepository {

    @Inject
    public PartyRepository(ApiService service) {
        super(service);
    }


    public Single<List<Party>> getParties(double latitude, double longitude, int distance) {
        return service.getParties(latitude, longitude, distance);
    }
}
