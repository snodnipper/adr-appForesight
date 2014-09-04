package com.livenation.foresight.formatters;

import android.content.Context;
import android.support.annotation.NonNull;

import com.livenation.foresight.R;

public class TemperatureFormatter {
    public final Context context;

    public TemperatureFormatter(@NonNull Context context) {
        this.context = context;
    }

    public String format(double temperature) {
        return context.getString(R.string.temperature_fmt, temperature);
    }
}
