package com.livenation.foresight.formatters;

import android.content.Context;
import android.support.annotation.NonNull;

import com.livenation.foresight.R;

public class TemperatureFormatter {
    public static String format(@NonNull Context context, double temperature) {
        return context.getString(R.string.temperature_fmt, temperature);
    }
}
