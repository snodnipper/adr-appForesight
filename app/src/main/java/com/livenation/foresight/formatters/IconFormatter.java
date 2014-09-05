package com.livenation.foresight.formatters;

import com.livenation.foresight.R;
import com.livenation.foresight.functional.Optional;

public class IconFormatter {
    public static int colorResourceForIcon(Optional<String> icon) {
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

    public static int imageResourceForIcon(Optional<String> icon) {
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
}
