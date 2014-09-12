package com.livenation.foresight.graph.presenters;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.livenation.foresight.functional.OnErrors;
import com.livenation.foresight.functional.Optional;
import com.livenation.foresight.service.model.Coordinates;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.subjects.ReplaySubject;

@Singleton
public class LocationPresenter implements Presenter {
    private final LocationManager locationManager;
    private final PreferencesPresenter preferences;
    private boolean wantsUpdate = false;

    public final ReplaySubject<Coordinates> coordinates = ReplaySubject.create(1);

    @Inject public LocationPresenter(@NonNull LocationManager locationManager, @NonNull PreferencesPresenter preferences) {
        this.locationManager = locationManager;
        this.preferences = preferences;

        preferences.savedManualLocation.subscribe(unused -> reload(), OnErrors.SILENTLY_IGNORE_THEM);
    }


    private void reloadFromLocationManager() {
        String providerName = locationManager.getBestProvider(makeCriteria(), true);
        if (providerName == null) {
            coordinates.onNext(Coordinates.DEFAULT);
            return;
        }

        Location location = locationManager.getLastKnownLocation(providerName);
        if (location != null && !wantsUpdate) {
            coordinates.onNext(Coordinates.fromLocation(location));
        } else {
            locationManager.requestSingleUpdate(providerName, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    coordinates.onNext(Coordinates.fromLocation(location));
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle bundle) {
                    coordinates.onNext(Coordinates.DEFAULT);
                }

                @Override
                public void onProviderEnabled(String provider) {
                    coordinates.onNext(Coordinates.DEFAULT);
                }

                @Override
                public void onProviderDisabled(String provider) {
                    coordinates.onNext(Coordinates.DEFAULT);
                }
            }, Looper.getMainLooper());

            this.wantsUpdate = false;
        }
    }

    @Override
    public void reload() {
        Optional<Coordinates> manualLocation = preferences.getSavedManualLocation();
        if (manualLocation.isPresent())
            coordinates.onNext(manualLocation.get());
        else
            reloadFromLocationManager();
    }

    public void setWantsUpdate() {
        this.wantsUpdate = true;
    }

    private Criteria makeCriteria() {
        return new Criteria();
    }
}
