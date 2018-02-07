package io.mashup.exit11.data.repository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.mashup.exit11.data.model.AddParty;
import io.mashup.exit11.data.model.Party;
import io.mashup.exit11.data.remote.api.ApiService;
import io.reactivex.Observable;

/**
 * Created by jonghunlee on 2017. 11. 5..
 */
public class PartyRepository extends RemoteRepository {

    private List<Party> parties = new ArrayList<>();

    @Inject
    public PartyRepository(ApiService service) {
        super(service);
    }

    public Observable<List<Party>> getParties(double latitude, double longitude, int distance) {
        return service.getParties(latitude, longitude, distance)
                .flatMapIterable(it -> it)
                .filter(newParty -> {
                    for (Party party : this.parties) {
                        if (party.getPartyId() == newParty.getPartyId()) {
                            return false;
                        }
                    }

                    this.parties.add(newParty);
                    
                    return true;
                })
                .toList()
                .toObservable();
    }

    public Observable<Object> addParty(AddParty addParty) {
        return null;
    }
}
