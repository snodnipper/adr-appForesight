package com.livenation.foresight.ui;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.livenation.foresight.R;
import com.livenation.foresight.adapters.ForecastAdapter;
import com.livenation.foresight.formatters.IconFormatter;
import com.livenation.foresight.formatters.TemperatureFormatter;
import com.livenation.foresight.functional.OnErrors;
import com.livenation.foresight.functional.Optional;
import com.livenation.foresight.graph.ReverseGeocoder;
import com.livenation.foresight.graph.presenters.ForecastPresenter;
import com.livenation.foresight.service.model.Report;
import com.livenation.foresight.service.model.WeatherData;
import com.livenation.foresight.util.InjectionFragment;
import com.livenation.foresight.util.SetContentView;

import javax.inject.Inject;

import butterknife.InjectView;
import rx.Observable;

import static rx.android.observables.AndroidObservable.bindFragment;

@SetContentView(R.layout.fragment_today)
public class TodayFragment extends InjectionFragment {
    @InjectView(R.id.fragment_forecast_location) TextView location;
    @InjectView(R.id.fragment_forecast_temperature) TextView temperature;
    @InjectView(R.id.fragment_forecast_conditions) TextView conditions;
    @InjectView(R.id.fragment_today_recycler_view) RecyclerView recyclerView;

    @Inject ForecastPresenter presenter;
    @Inject ReverseGeocoder geocoder;
    private ForecastAdapter forecastAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.forecastAdapter = new ForecastAdapter(getActivity(), ForecastAdapter.Mode.HOURLY);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(forecastAdapter);

        Observable<Report> forecast = bindFragment(this, presenter.forecast);
        forecast.map(Report::getHourly)
                .subscribe(forecastAdapter::bindForecast, forecastAdapter::handleError);
        forecast.map(Report::getCurrently)
                .subscribe(this::bindForecast, OnErrors.showDialogFrom(getFragmentManager()));

        Observable<String> locationName = bindFragment(this, geocoder.name);
        locationName.subscribe(location::setText);
    }


    public void bindForecast(Optional<WeatherData> data) {
        data.ifPresent(currently -> {
            temperature.setText(TemperatureFormatter.format(getActivity(), currently.getTemperature()));
            conditions.setText(currently.getSummary().orElse(""));

            Drawable conditionIcon = getResources().getDrawable(IconFormatter.imageResourceForIcon(currently.getIcon()));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                temperature.setCompoundDrawablesRelativeWithIntrinsicBounds(conditionIcon, null, null, null);
            else
                temperature.setCompoundDrawablesWithIntrinsicBounds(conditionIcon, null, null, null);
        });
    }
}
