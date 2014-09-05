package com.livenation.foresight.ui;

import android.app.ListFragment;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.livenation.foresight.ForecastApplication;
import com.livenation.foresight.R;
import com.livenation.foresight.adapters.TodayForecastAdapter;
import com.livenation.foresight.formatters.IconFormatter;
import com.livenation.foresight.formatters.TemperatureFormatter;
import com.livenation.foresight.graph.ForecastPresenter;
import com.livenation.foresight.graph.LocationGeocoder;
import com.livenation.foresight.service.model.WeatherData;
import com.livenation.foresight.service.model.Report;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;

import static rx.android.observables.AndroidObservable.bindFragment;

public class TodayFragment extends ListFragment {
    private View view;
    @InjectView(R.id.fragment_today_loading) ProgressBar loadingIndicator;
    @InjectView(R.id.fragment_forecast_location) TextView location;
    @InjectView(R.id.fragment_forecast_temperature) TextView temperature;
    @InjectView(R.id.fragment_forecast_conditions) TextView conditions;

    @Inject ForecastPresenter presenter;
    @Inject LocationGeocoder geocoder;
    private TodayForecastAdapter todayForecastAdapter;

    public TodayFragment() {
        ForecastApplication.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.todayForecastAdapter = new TodayForecastAdapter(getActivity());
        setListAdapter(todayForecastAdapter);

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_today, container, false);
        ButterKnife.inject(this, view);

        loadingIndicator.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Observable<Boolean> isLoading = bindFragment(this, presenter.isLoading);
        isLoading.map(is -> is ? View.VISIBLE : View.INVISIBLE)
                 .subscribe(loadingIndicator::setVisibility);

        Observable<Report> forecast = bindFragment(this, presenter.forecast);
        forecast.map(Report::getHourly)
                .subscribe(todayForecastAdapter::bindForecast, todayForecastAdapter::handleError);
        forecast.subscribe(this::bindForecast, this::handleError);

        Observable<String> locationName = bindFragment(this, geocoder.name);
        locationName.subscribe(location::setText);
    }


    public void bindForecast(Report report) {
        report.getCurrently().ifPresent(currently -> {
            view.setBackgroundResource(IconFormatter.colorResourceForIcon(currently.getIcon()));
            temperature.setText(TemperatureFormatter.format(getActivity(), currently.getTemperature()));
            conditions.setText(currently.getSummary().orElse(""));

            Drawable conditionIcon = getResources().getDrawable(IconFormatter.imageResourceForIcon(currently.getIcon()));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                temperature.setCompoundDrawablesRelativeWithIntrinsicBounds(conditionIcon, null, null, null);
            else
                temperature.setCompoundDrawablesWithIntrinsicBounds(conditionIcon, null, null, null);
        });
    }

    public void handleError(Throwable error) {
        Log.e(getClass().getSimpleName(), "Could not load forecast", error);
    }
}
