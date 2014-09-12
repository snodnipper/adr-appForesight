package com.livenation.foresight.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java8.util.Optional;

public class Report {
    @JsonProperty("currently") private Optional<WeatherData> currently;
    @JsonProperty("daily") private Optional<Forecast> daily;
    @JsonProperty("hourly") private Optional<Forecast> hourly;
    @JsonProperty("latitude") private double latitude;
    @JsonProperty("longitude") private double longitude;
    @JsonProperty("offset") private int offset;
    @JsonProperty("timezone") private Optional<String> timezone;


    public Optional<WeatherData> getCurrently() {
        return currently;
    }

    public Optional<Forecast> getDaily() {
        return daily;
    }

    public Optional<Forecast> getHourly() {
        return hourly;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getOffset() {
        return offset;
    }

    public Optional<String> getTimezone() {
        return timezone;
    }

    @Override
    public String toString() {
        return "Report{" +
                "currently=" + currently +
                ", daily=" + daily +
                ", hourly=" + hourly +
                ", offset=" + offset +
                ", timezone='" + timezone + '\'' +
                '}';
    }
}
