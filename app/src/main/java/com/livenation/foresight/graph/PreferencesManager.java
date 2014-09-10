package com.livenation.foresight.graph;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.livenation.foresight.service.ForecastApi;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.subjects.ReplaySubject;

@Singleton public class PreferencesManager implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String KEY_UNIT_SYSTEM = "unit_system";

    private final SharedPreferences sharedPreferences;

    public final ReplaySubject<String> unitSystem = ReplaySubject.create(1);

    @Inject public PreferencesManager(@NonNull Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        unitSystem.onNext(getUnitSystem());
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    public String getUnitSystem() {
        return sharedPreferences.getString(KEY_UNIT_SYSTEM, ForecastApi.UNITS_US_CUSTOMARY);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(KEY_UNIT_SYSTEM))
            unitSystem.onNext(getUnitSystem());
    }
}
