package com.livenation.foresight.stubs;

import android.support.annotation.NonNull;

import com.livenation.foresight.graph.presenters.LocationPresenter;
import com.livenation.foresight.graph.presenters.PreferencesPresenter;
import com.livenation.foresight.service.model.Coordinates;

public class StubLocationPresenter extends LocationPresenter {
    public StubLocationPresenter(@NonNull PreferencesPresenter preferences) {
        //noinspection ConstantConditions
        super(null, preferences);
    }


    @Override
    protected void reloadFromLocationManager() {
        coordinates.onNext(Coordinates.DEFAULT);
    }
}
