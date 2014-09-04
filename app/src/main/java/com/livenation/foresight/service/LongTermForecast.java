package com.livenation.foresight.service;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

public class LongTermForecast {
    @JsonDeserialize(contentAs=WeatherData.class)
    private List<WeatherData> data;
    private String icon;
    private String summary;


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
