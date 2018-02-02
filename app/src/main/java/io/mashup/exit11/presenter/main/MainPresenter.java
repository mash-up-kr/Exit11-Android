package io.mashup.exit11.presenter.main;

import javax.inject.Inject;

import io.mashup.exit11.data.model.AddParty;
import io.mashup.exit11.data.repository.PartyRepository;
import io.mashup.exit11.presenter.Presenter;

/**
 * Created by jonghunlee on 2018. 2. 3..
 */

public class MainPresenter extends Presenter<MainView> {

    private final PartyRepository repository;

    @Inject
    MainPresenter(PartyRepository repository) {
        this.repository = repository;
    }

    public void addParty(AddParty addParty) {
        checkAttachView();
        
        repository.addParty(addParty);
    }
}
