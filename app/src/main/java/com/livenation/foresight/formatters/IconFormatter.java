package com.livenation.foresight.formatters;

import com.livenation.foresight.R;

public class IconFormatter {
    public static int colorResourceForIcon(String icon) {
        switch (icon) {
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
}
