package com.livenation.foresight.graph;

import android.content.Context;
import android.location.Geocoder;
import android.location.LocationManager;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.livenation.foresight.functional.OptionalJacksonModule;
import com.livenation.foresight.graph.presenters.ForecastPresenter;
import com.livenation.foresight.ui.DatumDialogFragment;
import com.livenation.foresight.ui.HomeActivity;
import com.livenation.foresight.ui.NowFragment;
import com.livenation.foresight.ui.TodayFragment;
import com.livenation.foresight.service.ForecastApi;
import com.livenation.foresight.ui.WeekFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.converter.JacksonConverter;

@Module(injects = {
        HomeActivity.class,
        NowFragment.class,
        TodayFragment.class,
        WeekFragment.class,
        ForecastPresenter.class,
        DatumDialogFragment.class,
})
@SuppressWarnings("UnusedDeclaration")
public class ForecastModule {
    private final Context applicationContext;

    public ForecastModule(@NonNull Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Provides @Singleton Context provideApplicationContext() {
        return applicationContext;
    }

    @Provides @Singleton LocationManager provideLocationManager() {
        return (LocationManager) applicationContext.getSystemService(Context.LOCATION_SERVICE);
    }

    @Provides @Singleton Geocoder provideGeocoder() {
        return new Geocoder(applicationContext);
    }

    @Provides @Singleton ForecastApi provideForecastApi() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new OptionalJacksonModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ForecastApi.URL)
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setConverter(new JacksonConverter(objectMapper))
                .build();
        return adapter.create(ForecastApi.class);
    }
}
