package com.livenation.foresight.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.livenation.foresight.ForecastApplication;
import com.livenation.foresight.R;
import com.livenation.foresight.adapters.StaticFragmentAdapter;
import com.livenation.foresight.formatters.IconFormatter;
import com.livenation.foresight.functional.OnErrors;
import com.livenation.foresight.functional.Optional;
import com.livenation.foresight.graph.presenters.ForecastPresenter;
import com.livenation.foresight.service.model.Report;
import com.livenation.foresight.service.model.WeatherData;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;

import static rx.android.observables.AndroidObservable.bindActivity;

public class HomeActivity extends FragmentActivity {
    @Inject ForecastPresenter presenter;
    @InjectView(R.id.activity_home_view) View view;
    @InjectView(R.id.activity_home_loading) ProgressBar loadingIndicator;
    @InjectView(R.id.activity_home_pager) ViewPager viewPager;

    public HomeActivity() {
        ForecastApplication.getInstance().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);

        loadingIndicator.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        viewPager.setAdapter(new StaticFragmentAdapter(getSupportFragmentManager(), new Class<?>[] {
                TodayFragment.class,
                WeekFragment.class,
        }));
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        Observable<Boolean> isLoading = bindActivity(this, presenter.isLoading);
        isLoading.map(is -> is ? View.VISIBLE : View.INVISIBLE)
                 .subscribe(loadingIndicator::setVisibility);

        Observable<Report> forecast = bindActivity(this, presenter.forecast);
        forecast.map(Report::getCurrently)
                .subscribe(this::bindForecast, OnErrors.SILENTLY_IGNORE_THEM);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;

            case R.id.action_reload:
                presenter.reload();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void bindForecast(Optional<WeatherData> data) {
        data.ifPresent(forecast -> {
            int colorResId = IconFormatter.colorResourceForIcon(forecast.getIcon());
            view.setBackgroundResource(colorResId);
        });
    }
}
