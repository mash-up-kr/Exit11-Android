package io.mashup.exit11.util;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by jonghunlee on 2018. 2. 2..
 */

public class LocationChoiceObserver {

    private LocationChoiceObserver() {
    }

    public static LocationChoiceObserver getInstance() {
        return LazyHolder.INSTANCE;
    }

    private PublishSubject<String> bus = PublishSubject.create();

    public void send(String o) {
        bus.onNext(o);
    }

    public Observable<String> getObservable() {
        return bus;
    }

    private static class LazyHolder {

        private static final LocationChoiceObserver INSTANCE = new LocationChoiceObserver();
    }

}
