package com.livenation.foresight.formatters;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeFormatter {
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("hh:mm a");

    public static String format(@NonNull Date time) {
        FORMATTER.setTimeZone(TimeZone.getDefault());
        return FORMATTER.format(time);
    }

    public static String format(long time) {
        return format(new Date(time * 1000));
    }
}
