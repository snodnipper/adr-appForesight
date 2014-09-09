package com.livenation.foresight.ui;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.livenation.foresight.R;
import com.livenation.foresight.adapters.ForecastAdapter;
import com.livenation.foresight.graph.presenters.ForecastPresenter;
import com.livenation.foresight.service.model.Report;
import com.livenation.foresight.util.InjectionFragment;
import com.livenation.foresight.util.SetContentView;

import javax.inject.Inject;

import butterknife.InjectView;
import rx.Observable;

import static rx.android.observables.AndroidObservable.bindFragment;

@SetContentView(R.layout.fragment_week)
public class WeekFragment extends InjectionFragment {
    @Inject ForecastPresenter presenter;
    @InjectView(R.id.fragment_week_recycler_view) RecyclerView recyclerView;

    private ForecastAdapter forecastAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.forecastAdapter = new ForecastAdapter(getActivity(), ForecastAdapter.Mode.DAILY);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(forecastAdapter);

        Observable<Report> forecast = bindFragment(this, presenter.forecast);
        forecast.map(Report::getDaily)
                .subscribe(forecastAdapter::bindForecast, forecastAdapter::handleError);
    }
}
