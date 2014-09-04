package com.livenation.foresight.service;

public class Report {
    private WeatherData currently;
    private LongTermForecast daily;
    private LongTermForecast hourly;
    private double latitude;
    private double longitude;
    private int offset;
    private String timezone;


    public WeatherData getCurrently() {
        return currently;
    }

    public LongTermForecast getDaily() {
        return daily;
    }

    public LongTermForecast getHourly() {
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
