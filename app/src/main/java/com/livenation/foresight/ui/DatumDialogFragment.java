package com.livenation.foresight.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.livenation.foresight.ForesightApplication;
import com.livenation.foresight.R;
import com.livenation.foresight.formatters.BearingFormatter;
import com.livenation.foresight.formatters.IconFormatter;
import com.livenation.foresight.formatters.TemperatureFormatter;
import com.livenation.foresight.formatters.Units;
import com.livenation.foresight.graph.PreferencesManager;
import com.livenation.foresight.service.model.WeatherData;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.livenation.foresight.adapters.ForecastAdapter.Mode;

public class DatumDialogFragment extends DialogFragment {
    public static final String TAG = DatumDialogFragment.class.getSimpleName();

    private static final String ARG_WEATHER_DATA = "com.livenation.foresight.ui.DatumDialogFragment.ARG_WEATHER_DATA";
    private static final String ARG_MODE = "com.livenation.foresight.ui.DatumDialogFragment.ARG_MODE";

    @Inject PreferencesManager preferences;

    @InjectView(R.id.fragment_dialog_datum_view) View view;
    @InjectView(R.id.fragment_dialog_datum_temperature) TextView temperature;
    @InjectView(R.id.fragment_dialog_datum_conditions) TextView conditions;
    @InjectView(R.id.fragment_dialog_datum_humidity) TextView humidity;
    @InjectView(R.id.fragment_dialog_datum_wind) TextView wind;
    @InjectView(R.id.fragment_dialog_datum_precipitation) TextView precipitation;

    public static DatumDialogFragment newInstance(@NonNull WeatherData weatherData, @NonNull Mode mode) {
        DatumDialogFragment dialogFragment = new DatumDialogFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_WEATHER_DATA, weatherData);
        arguments.putSerializable(ARG_MODE, mode);
        dialogFragment.setArguments(arguments);
        return dialogFragment;
    }

    public DatumDialogFragment() {
        ForesightApplication.getInstance().inject(this);
    }

    @Override
    public @NonNull Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_dialog_datum, null);
        ButterKnife.inject(this, view);
        bindData(getWeatherData());
        dialog.setContentView(view);

        return dialog;
    }

    public WeatherData getWeatherData() {
        return (WeatherData) getArguments().getSerializable(ARG_WEATHER_DATA);
    }

    public Mode getMode() {
        return (Mode) getArguments().getSerializable(ARG_MODE);
    }

    public void bindData(@NonNull WeatherData currently) {
        switch (getMode()) {
            case DAILY:
                temperature.setText(TemperatureFormatter.format(getActivity(), currently.getTemperatureMin(), currently.getTemperatureMax()));
                break;

            case HOURLY:
                temperature.setText(TemperatureFormatter.format(getActivity(), currently.getApparentTemperature()));
                break;
        }

        conditions.setText(currently.getSummary().orElse(""));
        humidity.setText(getString(R.string.now_humidity_fmt, (long)(currently.getHumidity() * 100)));
        precipitation.setText(getString(R.string.now_precipitation_probability_fmt, (long)(currently.getPrecipitationProbability() * 100)));
        wind.setText(getString(R.string.now_wind_fmt,
                currently.getWindSpeed(),
                Units.getSpeedAbbreviation(getActivity(), preferences.getUnitSystem()),
                BearingFormatter.format(getActivity(), currently.getWindBearing())));

        Drawable conditionIcon = getResources().getDrawable(IconFormatter.imageResourceForIcon(currently.getIcon()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            temperature.setCompoundDrawablesRelativeWithIntrinsicBounds(conditionIcon, null, null, null);
        else
            temperature.setCompoundDrawablesWithIntrinsicBounds(conditionIcon, null, null, null);

        int colorResId = IconFormatter.colorResourceForIcon(currently.getIcon());
        view.setBackgroundResource(colorResId);
    }
}
