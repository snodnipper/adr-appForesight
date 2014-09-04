package com.livenation.foresight.service;

/**
 * A blob of weather data from the forecast.io API.
 * <p/>
 * All units are in US customary.
 */
public class WeatherData {
    private double apparentTemperature;
    private double apparentTemperatureMin;
    private long apparentTemperatureMinTime;
    private double apparentTemperatureMax;
    private long apparentTemperatureMaxTime;
    private double cloudCover;
    private double dewPoint;
    private double humidity;
    private String icon;
    private double nearestStormBearing;
    private double nearestStormDistance;
    private double ozone;
    private double precipIntensity;
    private double precipProbability;
    private double pressure;
    private String summary;
    private double temperature;
    private double temperatureMin;
    private long temperatureMinTime;
    private double temperatureMax;
    private long temperatureMaxTime;
    private long time;
    private double visibility;
    private double windBearing;
    private double windSpeed;


    public double getApparentTemperature() {
        return apparentTemperature;
    }

    public double getApparentTemperatureMin() {
        return apparentTemperatureMin;
    }

    public long getApparentTemperatureMinTime() {
        return apparentTemperatureMinTime;
    }

    public double getApparentTemperatureMax() {
        return apparentTemperatureMax;
    }

    public long getApparentTemperatureMaxTime() {
        return apparentTemperatureMaxTime;
    }

    public double getCloudCover() {
        return cloudCover;
    }

    public double getDewPoint() {
        return dewPoint;
    }

    public double getHumidity() {
        return humidity;
    }

    public String getIcon() {
        return icon;
    }

    public double getNearestStormBearing() {
        return nearestStormBearing;
    }

    public double getNearestStormDistance() {
        return nearestStormDistance;
    }

    public double getOzone() {
        return ozone;
    }

    public double getPrecipIntensity() {
        return precipIntensity;
    }

    public double getPrecipProbability() {
        return precipProbability;
    }

    public double getPressure() {
        return pressure;
    }

    public String getSummary() {
        return summary;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getTemperatureMin() {
        return temperatureMin;
    }

    public long getTemperatureMinTime() {
        return temperatureMinTime;
    }

    public double getTemperatureMax() {
        return temperatureMax;
    }

    public long getTemperatureMaxTime() {
        return temperatureMaxTime;
    }

    public long getTime() {
        return time;
    }

    public double getVisibility() {
        return visibility;
    }

    public double getWindBearing() {
        return windBearing;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "summary='" + summary + '\'' +
                ", time=" + time +
                ", icon='" + icon + '\'' +
                ", apparentTemperature=" + apparentTemperature +
                ", temperature=" + temperature +
                '}';
    }
}
