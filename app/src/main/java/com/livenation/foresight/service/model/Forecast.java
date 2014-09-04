package com.livenation.foresight.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

public class Forecast {
    @JsonDeserialize(contentAs=WeatherData.class)
    @JsonProperty("data") private List<WeatherData> data;
    @JsonProperty("icon") private String icon;
    @JsonProperty("summary") private String summary;


    public List<WeatherData> getWeatherData() {
        return data;
    }

    public String getIcon() {
        return icon;
    }

    public String getSummary() {
        return summary;
    }

    @Override
    public String toString() {
        return "LongTermForecast{" +
                "data=" + data +
                ", icon='" + icon + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }
}
