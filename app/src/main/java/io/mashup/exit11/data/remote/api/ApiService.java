package io.mashup.exit11.data.remote.api;

import java.util.List;

import io.mashup.exit11.data.model.Party;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * Created by jonghunlee on 2017. 11. 5..
 */

public interface ApiService {

    @GET("party")
    Single<List<Party>> getPartys();
}
