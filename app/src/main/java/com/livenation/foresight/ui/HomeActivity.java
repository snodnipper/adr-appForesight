package com.livenation.foresight.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;

import com.livenation.foresight.R;
import com.livenation.foresight.adapters.StaticFragmentAdapter;
import com.livenation.foresight.graph.presenters.ForecastPresenter;
import com.livenation.foresight.util.InjectLayout;
import com.livenation.foresight.util.InjectionActivity;

import javax.inject.Inject;

import butterknife.InjectView;

import static com.livenation.foresight.adapters.StaticFragmentAdapter.Item;

@InjectLayout(R.layout.activity_home)
public class HomeActivity extends InjectionActivity {
    @Inject ForecastPresenter presenter;
    @InjectView(R.id.activity_home_title_strip) PagerTitleStrip titleStrip;
    @InjectView(R.id.activity_home_pager) ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        titleStrip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        viewPager.setAdapter(new StaticFragmentAdapter(getSupportFragmentManager(), new Item[] {
                Item.with(TodayFragment.class, getString(R.string.fragment_title_today)),
                Item.with(WeekFragment.class, getString(R.string.fragment_title_week)),
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
                presenter.update();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
