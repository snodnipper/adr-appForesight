package com.livenation.foresight.graph.presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import java8.util.Optional;
import com.livenation.foresight.service.ForecastApi;
import com.livenation.foresight.service.model.Coordinates;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.subjects.ReplaySubject;

@Singleton public class PreferencesPresenter implements UpdatablePresenter, SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String KEY_UNIT_SYSTEM = "unit_system";
    private static final String KEY_SAVED_MANUAL_LOCATION = "saved_manual_location";

    private final SharedPreferences sharedPreferences;

    public final ReplaySubject<String> unitSystem = ReplaySubject.create(1);
    public final ReplaySubject<Optional<Coordinates>> savedManualLocation = ReplaySubject.create(1);

    @Inject public PreferencesPresenter(@NonNull Context context) {
        this.sharedPreferences = getSharedPreferencesFrom(context);

        update();

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }


    @Override
    public void update() {
        unitSystem.onNext(getUnitSystem());
        savedManualLocation.onNext(getSavedManualLocation());
    }


    //region Synchronous Accessors

    public String getUnitSystem() {
        return sharedPreferences.getString(KEY_UNIT_SYSTEM, ForecastApi.UNITS_US_CUSTOMARY);
    }

    public void setSavedManualLocation(@NonNull Optional<Coordinates> location) {
        if (location.isPresent()) {
            sharedPreferences.edit()
                             .putString(KEY_SAVED_MANUAL_LOCATION, location.get().toString())
                             .apply();
        } else {
            sharedPreferences.edit()
                             .remove(KEY_SAVED_MANUAL_LOCATION)
                             .apply();
        }
    }

    public @NonNull Optional<Coordinates> getSavedManualLocation() {
        return Coordinates.fromString(sharedPreferences.getString(KEY_SAVED_MANUAL_LOCATION, null));
    }

    //endregion


    //region Internal

    /**
     * Provides an override point for tests.
     */
    protected SharedPreferences getSharedPreferencesFrom(@NonNull Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case KEY_UNIT_SYSTEM:
                unitSystem.onNext(getUnitSystem());
                break;

            case KEY_SAVED_MANUAL_LOCATION:
                savedManualLocation.onNext(getSavedManualLocation());
                break;

            default:
                break;
        }
    }

    //endregion
}
