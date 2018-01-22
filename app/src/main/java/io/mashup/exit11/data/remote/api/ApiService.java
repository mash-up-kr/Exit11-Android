package io.mashup.exit11.data.remote.api;

import java.util.List;

import io.mashup.exit11.data.model.Party;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jonghunlee on 2017. 11. 5..
 */

public interface ApiService {

    @GET("Party")
    Single<List<Party>> getParties(@Query("latitude") double latitude, @Query("longitude") double longitude,
                                   @Query("distance") int distance);
}
