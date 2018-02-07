package io.mashup.exit11.data.repository;

import io.mashup.exit11.data.remote.api.ApiService;

/**
 * Created by jonghunlee on 2018. 1. 22..
 */

public class RemoteRepository {

    protected final ApiService service;

    public RemoteRepository(ApiService service) {
        this.service = service;
    }
}
