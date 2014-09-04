package com.livenation.foresight.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Report {
    @JsonProperty("currently") private WeatherData currently;
    @JsonProperty("daily") private Forecast daily;
    @JsonProperty("hourly") private Forecast hourly;
    @JsonProperty("latitude") private double latitude;
    @JsonProperty("longitude") private double longitude;
    @JsonProperty("offset") private int offset;
    @JsonProperty("timezone") private String timezone;


    public WeatherData getCurrently() {
        return currently;
    }

    public Forecast getDaily() {
        return daily;
    }

    public Forecast getHourly() {
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

    public String getTimezone() {
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
