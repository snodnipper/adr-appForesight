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

    public final ReplaySubject<Boolean> isLoading = ReplaySubject.create(1);
    public final ReplaySubject<Report> forecast = ReplaySubject.create(1);

    @Inject public ForecastPresenter(ForecastApi api,
                                     LocationPresenter location,
                                     PreferencesManager preferences) {
        this.api = api;
        this.location = location;
        this.preferences = preferences;

        isLoading.onNext(false);
        reload();
    }

    public void reload() {
        isLoading.onNext(true);
        Observable<Pair<Coordinates, String>> data = Observable.combineLatest(location.coordinates, preferences.unitSystem, Pair::new);
        data.subscribe(params -> {
            Coordinates location = params.first;
            String units = params.second;

            api.forecast(location.latitude, location.longitude, units, getLanguage())
               .doOnNext(unused -> isLoading.onNext(false))
               .subscribe(forecast);
        });
    }

    public String getLanguage() {
        return ForecastApi.DEFAULT_LANGUAGE;
    }
}
