package com.livenation.foresight.service;

import com.livenation.foresight.service.model.Report;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

public interface ForecastApi {
    public static final String URL = "https://api.forecast.io/";
    public static final String API_KEY = "64f75b487cff3e8283fbf9498d4d7b0c";

    public static final String UNITS_US_CUSTOMARY = "us";
    public static final String UNITS_SI = "si";
    public static final String UNITS_CANADIAN = "ca";
    public static final String UNITS_UNITED_KINGDOM = "uk";

    public static final String DEFAULT_LANGUAGE = "en";

    @GET("/forecast/"+API_KEY+"/{latitude},{longitude}")
    Observable<Report> forecast(@Path("latitude") double latitude,
                                @Path("longitude") double longitude,
                                @Query("units") String units,
                                @Query("lang") String languageCode);
}
