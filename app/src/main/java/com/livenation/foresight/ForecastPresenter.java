package com.livenation.foresight;

import com.livenation.foresight.service.ForecastApi;
import com.livenation.foresight.service.Report;

import rx.subjects.ReplaySubject;

public class ForecastPresenter {
    public final ForecastApi api = ForecastApplication.getInstance().getForecastApi();
    public final ReplaySubject<Report> forecast = ReplaySubject.create(1);

    public ForecastPresenter() {
        reload();
    }

    public void reload() {
        api.forecast(getLatitude(), getLongitude()).subscribe(forecast::onNext);
    }

    public double getLatitude() {
        return 37.8267;
    }

    public double getLongitude() {
        return -122.423;
    }
}
