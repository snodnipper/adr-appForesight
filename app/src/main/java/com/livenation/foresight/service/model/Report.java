package com.livenation.foresight.service.model;

public class Report {
    private WeatherData currently;
    private Forecast daily;
    private Forecast hourly;
    private double latitude;
    private double longitude;
    private int offset;
    private String timezone;


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
