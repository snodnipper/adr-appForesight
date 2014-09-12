package com.livenation.foresight.graph.presenters;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.livenation.foresight.service.ForecastApi;
import com.livenation.foresight.service.model.Coordinates;
import com.livenation.foresight.service.model.Report;

import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.subjects.ReplaySubject;

@Singleton public class ForecastPresenter implements Presenter {
    public static final long UPDATE_INTERVAL = 1000 * (60 * 15) /* 15 minutes */;

    private final ForecastApi api;
    private final LocationPresenter location;
    private final PreferencesPresenter preferences;

    public final ReplaySubject<Boolean> isLoading = ReplaySubject.create(1);
    public final ReplaySubject<Report> forecast = ReplaySubject.create(1);

    private long timeOfLastUpdate = 0;

    @Inject public ForecastPresenter(@NonNull ForecastApi api,
                                     @NonNull LocationPresenter location,
                                     @NonNull PreferencesPresenter preferences) {
        this.api = api;
        this.location = location;
        this.preferences = preferences;

        isLoading.onNext(false);
        update();
    }

    @Override
    public void update() {
        isLoading.onNext(true);
        Observable<Params> forRequest = Observable.combineLatest(location.coordinates, preferences.unitSystem, Params::new);
        forRequest.subscribe(p -> api.forecast(p.location.latitude, p.location.longitude, p.units, getLanguage())
                  .doOnNext(unused -> {
                      isLoading.onNext(false);
                      timeOfLastUpdate = System.currentTimeMillis();
                  })
                  .subscribe(forecast::onNext, forecast::onError));
    }

    public String getLanguage() {
        String systemLanguage = Locale.getDefault().getLanguage();
        if (!TextUtils.isEmpty(systemLanguage))
            return systemLanguage;
        else
            return ForecastApi.DEFAULT_LANGUAGE;
    }

    public long getTimeOfLastUpdate() {
        return timeOfLastUpdate;
    }

    public boolean isReloadRecommended() {
        return (System.currentTimeMillis() - getTimeOfLastUpdate() > UPDATE_INTERVAL);
    }

    public void reloadIfRecommended() {
        if (isReloadRecommended())
            update();
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
