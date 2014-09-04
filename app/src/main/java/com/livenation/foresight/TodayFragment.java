package com.livenation.foresight;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.livenation.foresight.adapters.TodayForecastAdapter;
import com.livenation.foresight.formatters.IconFormatter;
import com.livenation.foresight.formatters.TemperatureFormatter;
import com.livenation.foresight.service.WeatherData;
import com.livenation.foresight.service.ForecastApi;
import com.livenation.foresight.service.Report;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.android.observables.AndroidObservable;

import static rx.android.observables.AndroidObservable.bindFragment;

public class TodayFragment extends ListFragment {
    private View view;
    @InjectView(R.id.fragment_forecast_location) TextView location;
    @InjectView(R.id.fragment_forecast_temperature) TextView temperature;
    @InjectView(R.id.fragment_forecast_conditions) TextView conditions;

    private ForecastPresenter presenter;
    private TemperatureFormatter temperatureFormatter;
    private TodayForecastAdapter todayForecastAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.presenter = new ForecastPresenter();
        this.temperatureFormatter = new TemperatureFormatter(getActivity());
        this.todayForecastAdapter = new TodayForecastAdapter(getActivity(), temperatureFormatter);
        setListAdapter(todayForecastAdapter);

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_today, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Observable<Report> forecast = bindFragment(this, presenter.forecast);
        forecast.map(Report::getHourly).subscribe(todayForecastAdapter::bindForecast, todayForecastAdapter::handleError);
        forecast.subscribe(this::bindForecast, this::handleError);
    }


    public void bindForecast(Report report) {
        WeatherData currently = report.getCurrently();
        view.setBackgroundResource(IconFormatter.colorResourceForIcon(currently.getIcon()));
        temperature.setText(temperatureFormatter.format(currently.getTemperature()));
        conditions.setText(currently.getSummary());
    }

    public void handleError(Throwable error) {
        Log.e(getClass().getSimpleName(), "Could not load forecast", error);
    }
}
