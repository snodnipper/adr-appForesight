package com.livenation.foresight;

import android.app.Application;

import com.livenation.foresight.graph.ForecastModule;

import dagger.ObjectGraph;

public class ForecastApplication extends Application {
    private ObjectGraph objectGraph;

    private static ForecastApplication instance;

    public static ForecastApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        this.objectGraph = ObjectGraph.create(new ForecastModule(getApplicationContext()));

        instance = this;
    }

    public <T> void inject(T target) {
        objectGraph.inject(target);
    }
}
