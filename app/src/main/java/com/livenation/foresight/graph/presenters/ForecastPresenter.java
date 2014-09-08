package com.livenation.foresight.graph.presenters;

import android.support.annotation.NonNull;

import com.livenation.foresight.graph.PreferencesManager;
import com.livenation.foresight.service.ForecastApi;
import com.livenation.foresight.service.model.Coordinates;
import com.livenation.foresight.service.model.Report;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.subjects.ReplaySubject;

@Singleton public class ForecastPresenter implements Presenter {
    private final ForecastApi api;
    private final LocationPresenter location;
    private final PreferencesManager preferences;

    public final ReplaySubject<Boolean> isLoading = ReplaySubject.create(1);
    public final ReplaySubject<Report> forecast = ReplaySubject.create(1);

    @Inject public ForecastPresenter(@NonNull ForecastApi api,
                                     @NonNull LocationPresenter location,
                                     @NonNull PreferencesManager preferences) {
        this.api = api;
        this.location = location;
        this.preferences = preferences;

        isLoading.onNext(false);
        reload();
    }

    @Override
    public void reload() {
        isLoading.onNext(true);
        Observable<Params> data = Observable.combineLatest(location.coordinates, preferences.unitSystem, Params::new);
        data.subscribe(p -> api.forecast(p.location.latitude, p.location.longitude, p.units, getLanguage())
                               .doOnNext(unused -> isLoading.onNext(false))
                               .subscribe(forecast));
    }

    public String getLanguage() {
        return ForecastApi.DEFAULT_LANGUAGE;
    }


    private static class Params {
        private final Coordinates location;
        private final String units;

        private Params(Coordinates location, String units) {
            this.location = location;
            this.units = units;
        }
    }
}
