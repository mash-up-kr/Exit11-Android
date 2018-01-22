package io.mashup.exit11.presenter;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by jonghunlee on 2018. 1. 22..
 */

public abstract class Presenter<V extends View> {

    protected final CompositeDisposable disposable;
    protected V view;

    public Presenter() {
        this.disposable = new CompositeDisposable();
    }

    public void attachView(V view) {
        this.view = view;
    }

    protected void checkAttachView() {
        if (view == null) {
            throw new RuntimeException("View is Null");
        }
    }

    public void detachView() {
        disposable.dispose();

        view = null;
    }
}
