package com.livenation.foresight.graph;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;

import com.livenation.foresight.service.model.Coordinates;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.subjects.ReplaySubject;

@Singleton
public class LocationPresenter {
    private final LocationManager locationManager;

    public final ReplaySubject<Coordinates> coordinates = ReplaySubject.create(1);

    @Inject
    public LocationPresenter(LocationManager locationManager) {
        this.locationManager = locationManager;

        reload();
    }


    public void reload() {
        String providerName = locationManager.getBestProvider(makeCriteria(), true);
        if (providerName == null) {
            coordinates.onNext(Coordinates.DEFAULT);
            return;
        }

        Location location = locationManager.getLastKnownLocation(providerName);
        if (location != null) {
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
        }
    }

    private Criteria makeCriteria() {
        return new Criteria();
    }
}
