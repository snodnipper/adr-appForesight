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
import com.livenation.foresight.service.LongTermForecast;
import com.livenation.foresight.service.WeatherData;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TodayForecastAdapter extends ArrayAdapter<WeatherData> {
    private final LayoutInflater inflater;
    private final TemperatureFormatter formatter;

    public TodayForecastAdapter(@NonNull Context context, @NonNull TemperatureFormatter formatter) {
        super(context, R.layout.item_today_weather_data);

        this.inflater = LayoutInflater.from(context);
        this.formatter = formatter;
    }

    public void bindForecast(LongTermForecast forecast) {
        clear();
        if (forecast != null && forecast.getWeatherData() != null)
            addAll(forecast.getWeatherData());
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
        holder.temperature.setText(formatter.format(forecast.getApparentTemperature()));
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
