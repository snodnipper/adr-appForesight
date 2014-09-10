package com.livenation.foresight.formatters;

import android.content.Context;
import android.support.annotation.NonNull;

import com.livenation.foresight.R;

public class BearingFormatter {
    public static @NonNull String format(@NonNull Context context, long bearing) {
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
}
