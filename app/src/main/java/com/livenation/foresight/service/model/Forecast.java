package com.livenation.foresight.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.livenation.foresight.functional.Optional;

import java.util.List;

public class Forecast {
    @JsonDeserialize(contentAs=WeatherData.class)
    @JsonProperty("data") private Optional<List<WeatherData>> data;
    @JsonProperty("icon") private Optional<String> icon;
    @JsonProperty("summary") private Optional<String> summary;


    public Optional<List<WeatherData>> getWeatherData() {
        return data;
    }

    public Optional<String> getIcon() {
        return icon;
    }

    public Optional<String> getSummary() {
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
