package com.livenation.foresight.graph;

import com.livenation.foresight.service.ForecastApi;
import com.livenation.foresight.service.model.Report;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.subjects.ReplaySubject;

@Singleton public class ForecastPresenter {
    private final ForecastApi api;
    private final LocationPresenter location;

    public final ReplaySubject<Report> forecast = ReplaySubject.create(1);

    @Inject public ForecastPresenter(ForecastApi api, LocationPresenter location) {
        this.api = api;
        this.location = location;

        reload();
    }

    public void reload() {
        location.coordinates.subscribe(l -> api.forecast(l.latitude, l.longitude).subscribe(forecast));
    }
}
