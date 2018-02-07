package io.mashup.exit11.presenter.map;

import javax.inject.Inject;

import io.mashup.exit11.common.Const;
import io.mashup.exit11.data.repository.PartyRepository;
import io.mashup.exit11.presenter.Presenter;

/**
 * Created by jonghunlee on 2018. 1. 22..
 */

public class MapPresenter extends Presenter<MapView> {

    private final PartyRepository repository;

    @Inject
    MapPresenter(PartyRepository repository) {
        this.repository = repository;
    }

    public void getParties(double latitude, double longitude) {
        checkAttachView();

        repository.getParties(latitude, longitude, Const.DEFAULT_MAP_DISTANCE)
                .subscribe(parties -> view.resultParties(parties),
                        throwable -> view.showErrorMessage(throwable));
    }
}

