package com.livenation.foresight.formatters;

import android.content.Context;
import android.support.annotation.NonNull;

import com.livenation.foresight.R;
import com.livenation.foresight.service.ForecastApi;

public class Units {
    public static String getSpeedAbbreviation(@NonNull Context context, String unitSystem) {
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

    public static String getDistanceAbbreviation(@NonNull Context context, String unitSystem) {
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
}
