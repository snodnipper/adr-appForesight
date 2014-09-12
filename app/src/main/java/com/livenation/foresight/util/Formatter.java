package com.livenation.foresight.util;

import android.content.Context;
import android.location.Address;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.livenation.foresight.R;
import com.livenation.foresight.service.ForecastApi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import java8.util.Optional;

/**
 * Encapsulates all of the formatting functions used throughout the Foresight application.
 * A Context is provided upon construction to provide localized values where possible.
 */
public class Formatter {
    private final Context context;

    private final SimpleDateFormat dayDateFormatter;
    private final SimpleDateFormat timeDateFormatter;

    /**
     * Constructs the formatter.
     * @param context   Converted to an application context if it is not one already.
     */
    public Formatter(@NonNull Context context) {
        this.context = context.getApplicationContext();

        this.dayDateFormatter = new SimpleDateFormat(context.getString(R.string.day_date_format));
        this.timeDateFormatter = new SimpleDateFormat(context.getString(R.string.time_date_format));
    }


    //region Units

    /**
     * Returns the speed abbreviation suffix for a given ForecastApi unit system.
     */
    public String getSpeedAbbreviation(String unitSystem) {
        switch (unitSystem) {
            default:
            case ForecastApi.UNITS_US_CUSTOMARY:
            case ForecastApi.UNITS_UNITED_KINGDOM:
                return context.getString(R.string.unit_speed_mph);

            case ForecastApi.UNITS_SI:
                return context.getString(R.string.unit_speed_m_s);

            case ForecastApi.UNITS_CANADIAN:
                return context.getString(R.string.unit_speed_km_h);
        }
    }

    /**
     * Returns the distance abbreviation suffix for a given ForecastApi unit system.
     */
    public String getDistanceAbbreviation(String unitSystem) {
        switch (unitSystem) {
            default:
            case ForecastApi.UNITS_US_CUSTOMARY:
                return context.getString(R.string.units_distance_miles);

            case ForecastApi.UNITS_SI:
            case ForecastApi.UNITS_CANADIAN:
            case ForecastApi.UNITS_UNITED_KINGDOM:
                return context.getString(R.string.units_distance_km);
        }
    }

    //endregion


    //region Addresses

    public @NonNull String getPlaceholderAddress() {
        return context.getString(R.string.address_placeholder);
    }

    public @NonNull String formatAddress(@NonNull Address address) {
        String locality = address.getLocality();
        if (!TextUtils.isEmpty(locality))
            return locality;

        String adminArea = address.getAdminArea();
        if (!TextUtils.isEmpty(adminArea))
            return adminArea;

        String country = address.getCountryName();
        if (!TextUtils.isEmpty(country))
            return country;

        return getPlaceholderAddress();
    }

    //endregion
    
    
    //region Units

    public @NonNull String formatBearing(long bearing) {
        if (bearing == 0.0)
            return context.getString(R.string.compass_bearing_n);
        else if (bearing > 0 && bearing < 90)
            return context.getString(R.string.compass_bearing_ne);
        else if (bearing == 90)
            return context.getString(R.string.compass_bearing_e);
        else if (bearing > 90 && bearing < 180)
            return context.getString(R.string.compass_bearing_se);
        else if (bearing == 180)
            return context.getString(R.string.compass_bearing_s);
        else if (bearing > 180 && bearing < 270)
            return context.getString(R.string.compass_bearing_sw);
        else if (bearing == 270)
            return context.getString(R.string.compass_bearing_w);
        else if (bearing > 270 && bearing < 360)
            return context.getString(R.string.compass_bearing_nw);
        else
            return context.getString(R.string.compass_bearing_unknown);
    }

    public String formatTemperature(double temperature) {
        return context.getString(R.string.temperature_fmt, temperature);
    }

    public String formatTemperatureRange(double min, double max) {
        return context.getString(R.string.temperature_range_fmt, min, max);
    }
    
    //endregion
    
    
    //region Dates

    public String formatDay(@NonNull Date time) {
        dayDateFormatter.setTimeZone(TimeZone.getDefault());
        return dayDateFormatter.format(time);
    }

    public String formatDay(long time) {
        return formatDay(new Date(time * 1000));
    }

    public String formatTime(@NonNull Date time) {
        timeDateFormatter.setTimeZone(TimeZone.getDefault());
        return timeDateFormatter.format(time);
    }

    public String formatTime(long time) {
        return formatTime(new Date(time * 1000));
    }
    
    //endregion
    
    
    //region Icons

    public @DrawableRes @ColorRes int colorResourceForIcon(Optional<String> icon) {
        switch (icon.orElse("")) {
            case "clear-day":
                return R.color.condition_neutral;
            case "clear-night":
                return R.color.condition_neutral_night;
            case "rain":
                return R.color.condition_precipitating;
            case "snow":
                return R.color.condition_precipitating;
            case "sleet":
                return R.color.condition_precipitating;
            case "wind":
                return R.color.condition_neutral;
            case "fog":
                return R.color.condition_precipitating;
            case "cloudy":
                return R.color.condition_cloudy;
            case "partly-cloudy-day":
                return R.color.condition_partly_cloud;
            case "partly-cloudy-night":
                return R.color.condition_partly_cloud_night;
            default:
                return R.color.condition_neutral;
        }
    }

    public @DrawableRes int lightDrawableForIcon(Optional<String> icon) {
        switch (icon.orElse("")) {
            case "clear-day":
                return R.drawable.clear_day;
            case "clear-night":
                return R.drawable.clear_night;
            case "rain":
                return R.drawable.rain;
            case "snow":
                return R.drawable.snow;
            case "sleet":
                return R.drawable.sleet;
            case "wind":
                return R.drawable.windy;
            case "fog":
                return R.drawable.fog;
            case "partly-cloudy-day":
            case "partly-cloudy-night":
            case "cloudy":
                return R.drawable.cloudy;
            default:
                return R.drawable.clear_day;
        }
    }
    
    //endregion
}
