package com.livenation.foresight.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.livenation.foresight.R;
import com.livenation.foresight.adapters.ForecastAdapter;
import com.livenation.foresight.graph.presenters.ForecastPresenter;
import com.livenation.foresight.service.model.Report;
import com.livenation.foresight.service.model.WeatherData;
import com.livenation.foresight.util.Animations;
import com.livenation.foresight.util.InjectLayout;
import com.livenation.foresight.util.InjectionFragment;

import javax.inject.Inject;

import butterknife.InjectView;
import rx.Observable;

import static rx.android.observables.AndroidObservable.bindFragment;

@InjectLayout(R.layout.fragment_week)
public class WeekFragment extends InjectionFragment implements ForecastAdapter.OnItemClickListener {
    @Inject ForecastPresenter presenter;
    @InjectView(R.id.fragment_week_recycler_view) RecyclerView recyclerView;

    private ForecastAdapter forecastAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.forecastAdapter = new ForecastAdapter(getActivity(), ForecastAdapter.Mode.DAILY);
        forecastAdapter.setOnItemClickListener(this);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(Animations.createItemAnimator());
        recyclerView.setAdapter(forecastAdapter);

        Observable<Boolean> loading = bindFragment(this, presenter.isLoading);
        loading.filter(is -> is)
               .subscribe(unused -> forecastAdapter.clear());

        Observable<Report> forecast = bindFragment(this, presenter.forecast);
        forecast.map(Report::getDaily)
                .subscribe(forecastAdapter::bindForecast, forecastAdapter::handleError);
    }

    @Override
    public void onItemClicked(WeatherData item, int position) {
        DatumDialogFragment dialogFragment = DatumDialogFragment.newInstance(item, ForecastAdapter.Mode.DAILY);
        dialogFragment.show(getFragmentManager(), DatumDialogFragment.TAG);
    }
}
