package com.livenation.foresight;

import android.app.Application;

import com.livenation.foresight.graph.ForecastModule;

import dagger.ObjectGraph;

public class ForesightApplication extends Application {
    private ObjectGraph objectGraph;

    private static ForesightApplication instance;

    public static ForesightApplication getInstance() {
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
