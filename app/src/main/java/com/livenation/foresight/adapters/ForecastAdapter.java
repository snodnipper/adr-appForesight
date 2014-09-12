package com.livenation.foresight.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.livenation.foresight.R;
import com.livenation.foresight.formatters.DayFormatter;
import com.livenation.foresight.formatters.TemperatureFormatter;
import com.livenation.foresight.formatters.TimeFormatter;
import java8.util.Optional;
import com.livenation.foresight.service.model.Forecast;
import com.livenation.foresight.service.model.WeatherData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.livenation.foresight.functional.Functions.filterList;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> implements View.OnClickListener {
    private final Context context;
    private final LayoutInflater inflater;
    private final Mode mode;
    private final ArrayList<WeatherData> weatherData;

    private OnItemClickListener onItemClickListener;

    public ForecastAdapter(@NonNull Context context, @NonNull Mode mode) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.mode = mode;
        this.weatherData = new ArrayList<>();
    }


    //region Binding

    public void bindForecast(Optional<Forecast> forecast) {
        clear();

        List<WeatherData> weatherData = forecast.flatMap(Forecast::getWeatherData).orElse(Collections.emptyList());
        long now = System.currentTimeMillis() / 1000;
        this.weatherData.addAll(filterList(weatherData, d -> d.getTime() >= now));
        notifyItemRangeInserted(0, weatherData.size());
    }

    public void clear() {
        notifyItemRangeRemoved(0, weatherData.size());
        weatherData.clear();
    }

    public void handleError(@SuppressWarnings("UnusedParameters") Throwable ignored) {
        clear();
    }

    //endregion


    //region Click Support

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(@NonNull View view) {
        if (getOnItemClickListener() != null) {
            int position = (Integer) view.getTag();
            WeatherData item = weatherData.get(position);
            getOnItemClickListener().onItemClicked(item, position);
        }
    }

    //endregion


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view;
        switch (mode) {
            case HOURLY:
                view = inflater.inflate(R.layout.item_hourly_forecast, viewGroup, false);
                break;

            case DAILY:
                view = inflater.inflate(R.layout.item_daily_forecast, viewGroup, false);
                break;

            default:
                throw new IllegalStateException();
        }

        view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        WeatherData forecast = weatherData.get(position);
        switch (mode) {
            case HOURLY:
                holder.time.setText(TimeFormatter.format(forecast.getTime()));
                holder.temperature.setText(TemperatureFormatter.format(context, forecast.getApparentTemperature()));
                break;

            case DAILY:
                holder.temperature.setText(TemperatureFormatter.format(context, forecast.getTemperatureMin(), forecast.getTemperatureMax()));
                holder.time.setText(DayFormatter.format(forecast.getTime()));
                break;
        }
        holder.conditions.setText(forecast.getSummary().orElse(""));
    }

    @Override
    public int getItemCount() {
        return weatherData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.item_weather_data_time) TextView time;
        @InjectView(R.id.item_weather_data_temperature) TextView temperature;
        @InjectView(R.id.item_weather_data_conditions) TextView conditions;

        ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.inject(this, itemView);
        }
    }

    public static enum Mode {
        HOURLY,
        DAILY,
    }

    public interface OnItemClickListener {
        void onItemClicked(WeatherData item, int position);
    }
}
