package com.livenation.foresight.formatters;

import android.content.Context;
import android.support.annotation.NonNull;

import com.livenation.foresight.R;

public class TemperatureFormatter {
    public static String format(@NonNull Context context, double temperature) {
        return context.getString(R.string.temperature_fmt, temperature);
    }

    public static String format(@NonNull Context context, double min, double max) {
        return context.getString(R.string.temperature_range_fmt, min, max);
    }
}
