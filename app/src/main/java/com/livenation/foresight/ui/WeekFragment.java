package com.livenation.foresight.ui;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;

import com.livenation.foresight.ForecastApplication;
import com.livenation.foresight.adapters.ForecastAdapter;
import com.livenation.foresight.graph.ForecastPresenter;
import com.livenation.foresight.service.model.Report;

import javax.inject.Inject;

import rx.Observable;

import static rx.android.observables.AndroidObservable.bindFragment;

public class WeekFragment extends ListFragment {
    @Inject ForecastPresenter presenter;

    private ForecastAdapter forecastAdapter;

    public WeekFragment() {
        ForecastApplication.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.forecastAdapter = new ForecastAdapter(getActivity(), ForecastAdapter.Mode.DAILY);
        setListAdapter(forecastAdapter);

        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getListView().setDividerHeight(0);
        getListView().setDivider(null);

        Observable<Report> forecast = bindFragment(this, presenter.forecast);
        forecast.map(Report::getDaily)
                .subscribe(forecastAdapter::bindForecast, forecastAdapter::handleError);
    }
}
