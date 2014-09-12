package com.livenation.foresight.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.livenation.foresight.R;
import com.livenation.foresight.functional.OnErrors;
import java8.util.Optional;
import com.livenation.foresight.graph.presenters.ForecastPresenter;
import com.livenation.foresight.graph.presenters.GeocodePresenter;
import com.livenation.foresight.graph.presenters.PreferencesPresenter;
import com.livenation.foresight.service.model.Report;
import com.livenation.foresight.service.model.WeatherData;
import com.livenation.foresight.util.Formatter;
import com.livenation.foresight.util.InjectLayout;
import com.livenation.foresight.util.InjectionFragment;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observable;

import static com.livenation.foresight.util.Animations.animate;
import static rx.android.observables.AndroidObservable.bindFragment;

@InjectLayout(R.layout.fragment_now)
public class NowFragment extends InjectionFragment {
    @InjectView(R.id.fragment_now_view) View view;
    @InjectView(R.id.fragment_now_progress_bar) ProgressBar loadingIndicator;
    @InjectView(R.id.fragment_now_location) TextView location;
    @InjectView(R.id.fragment_now_temperature) TextView temperature;
    @InjectView(R.id.fragment_now_conditions) TextView conditions;
    @InjectView(R.id.fragment_now_humidity) TextView humidity;
    @InjectView(R.id.fragment_now_wind) TextView wind;

    @Inject ForecastPresenter presenter;
    @Inject GeocodePresenter geocoder;
    @Inject PreferencesPresenter preferences;

    private Formatter formatter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.formatter = new Formatter(getActivity());
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadingIndicator.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        Observable<Boolean> loading = bindFragment(this, presenter.isLoading);
        loading.filter(is -> is)
               .subscribe(this::loadingStarted);

        Observable<Report> forecast = bindFragment(this, presenter.forecast);
        forecast.map(Report::getCurrently)
                .subscribe(this::bindForecast, this::handleError);

        Observable<String> locationName = bindFragment(this, geocoder.name);
        locationName.subscribe(location::setText);
    }


    public void loadingStarted(Boolean ignored) {
        conditions.setText(R.string.loading_placeholder_generic);

        animate(humidity).alpha(0f);
        animate(wind).alpha(0f);

        animate(temperature).alpha(0f)
                            .scaleX(0f)
                            .scaleY(0f);

        animate(loadingIndicator).alpha(1f)
                                 .scaleX(1f)
                                 .scaleY(1f);
    }

    public void bindForecast(Optional<WeatherData> data) {
        data.ifPresent(currently -> {
            temperature.setText(formatter.formatTemperature(currently.getTemperature()));
            conditions.setText(currently.getSummary().orElse(""));
            humidity.setText(getString(R.string.now_humidity_fmt, (long)(currently.getHumidity() * 100)));
            wind.setText(getString(R.string.now_wind_fmt,
                    currently.getWindSpeed(),
                    formatter.getSpeedAbbreviation(preferences.getUnitSystem()),
                    formatter.formatBearing(currently.getWindBearing())));

            Drawable conditionIcon = getResources().getDrawable(formatter.lightDrawableForIcon(currently.getIcon()));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                temperature.setCompoundDrawablesRelativeWithIntrinsicBounds(conditionIcon, null, null, null);
            else
                temperature.setCompoundDrawablesWithIntrinsicBounds(conditionIcon, null, null, null);

            int colorResId = formatter.colorResourceForIcon(currently.getIcon());
            view.setBackgroundResource(colorResId);

            animate(humidity).alpha(1f);
            animate(wind).alpha(1f);

            animate(temperature).alpha(1f)
                                .scaleX(1f)
                                .scaleY(1f);

            animate(loadingIndicator).alpha(0f)
                                     .scaleX(0f)
                                     .scaleY(0f);
        });
    }

    public void handleError(Throwable error) {
        OnErrors.showDialogFrom(getFragmentManager()).call(error);
    }


    @OnClick(R.id.fragment_now_location)
    @SuppressWarnings("UnusedDeclaration")
    public void onLocationClicked(@NonNull View view) {
        startActivity(new Intent(getActivity(), LocationActivity.class));
    }

    @OnClick(R.id.fragment_now_temperature)
    @SuppressWarnings("UnusedDeclaration")
    public void onTemperatureClicked(@NonNull View view) {
        presenter.update();
    }
}
