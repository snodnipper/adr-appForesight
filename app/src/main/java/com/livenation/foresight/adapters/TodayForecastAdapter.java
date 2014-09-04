package com.livenation.foresight.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.livenation.foresight.R;
import com.livenation.foresight.formatters.IconFormatter;
import com.livenation.foresight.formatters.TemperatureFormatter;
import com.livenation.foresight.formatters.TimeFormatter;
import com.livenation.foresight.service.model.Forecast;
import com.livenation.foresight.service.model.WeatherData;
import com.livenation.foresight.functional.Optional;

import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.livenation.foresight.functional.Functions.filterList;

public class TodayForecastAdapter extends ArrayAdapter<WeatherData> {
    private final LayoutInflater inflater;

    public TodayForecastAdapter(@NonNull Context context) {
        super(context, R.layout.item_today_weather_data);

        this.inflater = LayoutInflater.from(context);
    }

    public void bindForecast(Optional<Forecast> forecast) {
        clear();
        List<WeatherData> weatherData = forecast.map(Forecast::getWeatherData).orElse(Collections.emptyList());
        long now = System.currentTimeMillis() / 1000;
        addAll(filterList(weatherData, d -> d.getTime() >= now));
    }

    public void handleError(@SuppressWarnings("UnusedParameters") Throwable ignored) {
        clear();
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_today_weather_data, parent, false);
            view.setTag(new ViewHolder(view));
        }

        WeatherData forecast = getItem(position);

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.time.setText(TimeFormatter.format(forecast.getTime()));
        holder.temperature.setText(TemperatureFormatter.format(getContext(), forecast.getApparentTemperature()));
        holder.conditions.setText(forecast.getSummary());
        view.setBackgroundResource(IconFormatter.colorResourceForIcon(forecast.getIcon()));

        return view;
    }


    class ViewHolder {
        @InjectView(R.id.item_weather_data_time) TextView time;
        @InjectView(R.id.item_weather_data_temperature) TextView temperature;
        @InjectView(R.id.item_weather_data_conditions) TextView conditions;

        ViewHolder(@NonNull View view) {
            ButterKnife.inject(this, view);
        }
    }
}
