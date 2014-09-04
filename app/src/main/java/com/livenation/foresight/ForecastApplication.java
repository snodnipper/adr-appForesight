package com.livenation.foresight;

import android.app.Application;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.livenation.foresight.service.ForecastApi;

import retrofit.RestAdapter;
import retrofit.converter.JacksonConverter;

public class ForecastApplication extends Application {
    private ForecastApi forecastApi;

    private static ForecastApplication instance;

    public static ForecastApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ForecastApi.URL)
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setConverter(new JacksonConverter(objectMapper))
                .build();
        forecastApi = adapter.create(ForecastApi.class);

        instance = this;
    }

    public ForecastApi getForecastApi() {
        return forecastApi;
    }
}
