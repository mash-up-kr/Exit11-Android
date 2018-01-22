package io.mashup.exit11.presenter.map;

import java.util.List;

import io.mashup.exit11.data.model.Party;
import io.mashup.exit11.presenter.View;

/**
 * Created by jonghunlee on 2018. 1. 22..
 */

public interface MapView extends View {

    void resultParties(List<Party> parties);
}
