package io.mashup.exit11.data.remote.api;

import java.util.List;

import io.mashup.exit11.data.model.Party;
import io.mashup.exit11.data.model.PartyMember;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by jonghunlee on 2017. 11. 5..
 */

public interface ApiService {

    @GET("party")
    Single<List<Party>> getPartys();

    @GET("partymember/{partyId}")
    Single<List<PartyMember>> getPartyMember(@Path("partyId") int partyId);

    @GET("member/{memberId}")
    Single<List<PartyMember>> getMember(@Path("memberId") int memberId);
}
