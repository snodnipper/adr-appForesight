package com.livenation.foresight.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A blob of weather data from the forecast.io API.
 */
public class WeatherData {
    @JsonProperty("apparentTemperature") private double apparentTemperature;
    @JsonProperty("apparentTemperatureMin") private double apparentTemperatureMin;
    @JsonProperty("apparentTemperatureMinTime") private long apparentTemperatureMinTime;
    @JsonProperty("apparentTemperatureMax") private double apparentTemperatureMax;
    @JsonProperty("apparentTemperatureMaxTime") private long apparentTemperatureMaxTime;
    @JsonProperty("cloudCover") private double cloudCover;
    @JsonProperty("dewPoint") private double dewPoint;
    @JsonProperty("humidity") private double humidity;
    @JsonProperty("icon") private String icon;
    @JsonProperty("nearestStormBearing") private double nearestStormBearing;
    @JsonProperty("nearestStormDistance") private double nearestStormDistance;
    @JsonProperty("ozone") private double ozone;
    @JsonProperty("precipIntensity") private double precipIntensity;
    @JsonProperty("precipProbability") private double precipProbability;
    @JsonProperty("pressure") private double pressure;
    @JsonProperty("summary") private String summary;
    @JsonProperty("temperature") private double temperature;
    @JsonProperty("temperatureMin") private double temperatureMin;
    @JsonProperty("temperatureMinTime") private long temperatureMinTime;
    @JsonProperty("temperatureMax") private double temperatureMax;
    @JsonProperty("temperatureMaxTime") private long temperatureMaxTime;
    @JsonProperty("time") private long time;
    @JsonProperty("visibility") private double visibility;
    @JsonProperty("windBearing") private double windBearing;
    @JsonProperty("windSpeed") private double windSpeed;


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
