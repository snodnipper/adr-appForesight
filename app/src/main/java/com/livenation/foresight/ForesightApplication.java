package com.livenation.foresight;

import android.app.Application;

import com.livenation.foresight.graph.ForecastModule;

import dagger.ObjectGraph;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class ForesightApplication extends Application {
    private ObjectGraph objectGraph;

    private static ForesightApplication instance;

    public static ForesightApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault("fonts/PTS55F.ttf", R.attr.fontPath);

        this.objectGraph = ObjectGraph.create(new ForecastModule(getApplicationContext()));

        instance = this;
    }

    public <T> void inject(T target) {
        objectGraph.inject(target);
    }
}
