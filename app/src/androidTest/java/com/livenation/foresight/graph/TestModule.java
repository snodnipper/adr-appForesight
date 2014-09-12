package com.livenation.foresight.graph;

import android.content.Context;
import android.location.LocationManager;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.livenation.foresight.functional.OptionalJacksonModule;
import com.livenation.foresight.graph.presenters.ForecastPresenter;
import com.livenation.foresight.graph.presenters.ForecastPresenterTests;
import com.livenation.foresight.graph.presenters.LocationPresenter;
import com.livenation.foresight.graph.presenters.LocationPresenterTests;
import com.livenation.foresight.graph.presenters.PreferencesPresenter;
import com.livenation.foresight.stubs.StubForecastApi;
import com.livenation.foresight.stubs.StubLocationPresenter;
import com.livenation.foresight.service.ForecastApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(injects = {
    ForecastPresenter.class,
    ForecastPresenterTests.class,
    LocationPresenterTests.class,
})
@SuppressWarnings("UnusedDeclaration")
public class TestModule {
    private final Context applicationContext;

    public TestModule(@NonNull Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Provides @Singleton Context provideApplicationContext() {
        return applicationContext;
    }

    @Provides @Singleton LocationPresenter provideLocationPresenter(@NonNull PreferencesPresenter preferences) {
        return new StubLocationPresenter(preferences);
    }

    @Provides @Singleton ObjectMapper provideObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new OptionalJacksonModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    @Provides @Singleton ForecastApi provideForecastApi(@NonNull ObjectMapper objectMapper) {
        return new StubForecastApi(applicationContext, objectMapper);
    }
}
