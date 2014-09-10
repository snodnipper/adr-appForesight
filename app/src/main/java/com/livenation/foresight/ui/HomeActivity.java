package com.livenation.foresight.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.livenation.foresight.R;
import com.livenation.foresight.adapters.StaticFragmentAdapter;
import com.livenation.foresight.formatters.IconFormatter;
import com.livenation.foresight.functional.OnErrors;
import com.livenation.foresight.functional.Optional;
import com.livenation.foresight.graph.presenters.ForecastPresenter;
import com.livenation.foresight.service.model.Report;
import com.livenation.foresight.service.model.WeatherData;
import com.livenation.foresight.util.InjectLayout;
import com.livenation.foresight.util.InjectionActivity;

import javax.inject.Inject;

import butterknife.InjectView;
import rx.Observable;

import static rx.android.observables.AndroidObservable.bindActivity;

@InjectLayout(R.layout.activity_home)
public class HomeActivity extends InjectionActivity {
    @Inject ForecastPresenter presenter;
    @InjectView(R.id.activity_home_pager) ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewPager.setAdapter(new StaticFragmentAdapter(getSupportFragmentManager(), new Class<?>[] {
                TodayFragment.class,
                WeekFragment.class,
        }));

        //noinspection ConstantConditions
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayShowHomeEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.reloadIfRecommended();
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
}
