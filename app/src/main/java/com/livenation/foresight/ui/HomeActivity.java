package com.livenation.foresight.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.livenation.foresight.ForecastApplication;
import com.livenation.foresight.R;
import com.livenation.foresight.formatters.IconFormatter;
import com.livenation.foresight.functional.OnErrors;
import com.livenation.foresight.functional.Optional;
import com.livenation.foresight.graph.ForecastPresenter;
import com.livenation.foresight.service.model.Report;
import com.livenation.foresight.service.model.WeatherData;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;

import static rx.android.observables.AndroidObservable.bindActivity;
import static rx.android.observables.AndroidObservable.bindFragment;


public class HomeActivity extends Activity {
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
        viewPager.setAdapter(new FragmentAdapter(getFragmentManager()));
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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.action_reload) {
            presenter.reload();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void bindForecast(Optional<WeatherData> data) {
        data.ifPresent(forecast -> {
            int colorResId = IconFormatter.colorResourceForIcon(forecast.getIcon());
            view.setBackgroundResource(colorResId);
        });
    }


    private static class FragmentAdapter extends FragmentPagerAdapter {
        private static final Class<?>[] ITEMS = { TodayFragment.class, WeekFragment.class };

        private FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return ITEMS.length;
        }

        @Override
        public Fragment getItem(int i) {
            try {
                return (Fragment) ITEMS[i].newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
