package com.livenation.foresight.stubs;

import android.content.Context;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.livenation.foresight.service.ForecastApi;
import com.livenation.foresight.service.model.Coordinates;
import com.livenation.foresight.service.model.Report;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

import static junit.framework.Assert.assertEquals;

public class StubForecastApi implements ForecastApi {
    private final Context context;
    private final ObjectMapper objectMapper;

    public StubForecastApi(@NonNull Context context, @NonNull ObjectMapper objectMapper) {
        this.context = context;
        this.objectMapper = objectMapper;
    }

    @Override
    public Observable<Report> forecast(@Path("latitude") double latitude,
                                       @Path("longitude") double longitude,
                                       @Query("units") String units,
                                       @Query("lang") String languageCode) {
        assertEquals(Coordinates.DEFAULT.latitude, latitude);
        assertEquals(Coordinates.DEFAULT.longitude, longitude);

        return Observable.create((Observable.OnSubscribe<Report>) s -> {
            try {
                InputStream cannedResponse = context.getAssets().open("CannedForecast.json");
                Report report = objectMapper.readValue(cannedResponse, Report.class);
                s.onNext(report);
            } catch (IOException e) {
                s.onError(e);
            }

            s.onCompleted();
        });
    }
}
