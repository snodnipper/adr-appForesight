package com.livenation.foresight.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.livenation.foresight.R;

import java8.lang.FunctionalInterface;
import java8.util.Optional;
import com.livenation.foresight.service.model.Forecast;
import com.livenation.foresight.service.model.WeatherData;
import com.livenation.foresight.util.Formatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.livenation.foresight.functional.Functions.filterList;

/**
 * A recycler view adapter that is functionally an ArrayAdapter holding WeatherData instances.
 */
public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> implements View.OnClickListener {
    private final LayoutInflater inflater;
    private final Mode mode;
    private final ArrayList<WeatherData> weatherData;
    private final Formatter formatter;

    private OnItemClickListener onItemClickListener;

    public ForecastAdapter(@NonNull Context context, @NonNull Mode mode) {
        this.inflater = LayoutInflater.from(context);
        this.mode = mode;
        this.formatter = new Formatter(context);
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
        View view = mode.inflate(inflater, viewGroup);

        // Recycler views do not handle item selection,
        // so we assign the adapter as the click listener
        // for each view created, and later set the tag
        // of the view to the position of the view.
        view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        WeatherData forecast = weatherData.get(position);
        switch (mode) {
            case HOURLY:
                holder.time.setText(formatter.formatTime(forecast.getTime()));
                holder.temperature.setText(formatter.formatTemperature(forecast.getApparentTemperature()));
                break;

            case DAILY:
                holder.temperature.setText(formatter.formatTemperatureRange(forecast.getTemperatureMin(), forecast.getTemperatureMax()));
                holder.time.setText(formatter.formatDay(forecast.getTime()));
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
        HOURLY(R.layout.item_hourly_forecast),
        DAILY(R.layout.item_daily_forecast);

        public View inflate(@NonNull LayoutInflater inflater, ViewGroup viewGroup) {
            return inflater.inflate(layoutResId, viewGroup, false);
        }

        private @LayoutRes int layoutResId;
        private Mode(@LayoutRes int layoutResId) {
            this.layoutResId = layoutResId;
        }
    }

    @FunctionalInterface
    public interface OnItemClickListener {
        void onItemClicked(WeatherData item, int position);
    }
}
