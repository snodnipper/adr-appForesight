package com.livenation.foresight.graph;

import android.util.Pair;

import com.livenation.foresight.service.ForecastApi;
import com.livenation.foresight.service.model.Coordinates;
import com.livenation.foresight.service.model.Report;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.subjects.ReplaySubject;

@Singleton public class ForecastPresenter {
    private final ForecastApi api;
    private final LocationPresenter location;
    private final PreferencesManager preferences;

    public final ReplaySubject<Report> forecast = ReplaySubject.create(1);

    @Inject public ForecastPresenter(ForecastApi api,
                                     LocationPresenter location,
                                     PreferencesManager preferences) {
        this.api = api;
        this.location = location;
        this.preferences = preferences;

        reload();
    }

    public void reload() {
        Observable<Pair<Coordinates, String>> data = Observable.combineLatest(location.coordinates, preferences.unitSystem, Pair::new);
        data.subscribe(params -> {
            Coordinates location = params.first;
            String units = params.second;

            api.forecast(location.latitude, location.longitude, units, getLanguage())
               .subscribe(r -> {
                   forecast.onNext(r);
               });
        });
    }

    public String getLanguage() {
        return ForecastApi.DEFAULT_LANGUAGE;
    }
}
