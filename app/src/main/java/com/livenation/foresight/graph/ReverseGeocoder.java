package com.livenation.foresight.graph;

import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;

import com.livenation.foresight.formatters.AddressFormatter;
import com.livenation.foresight.graph.presenters.LocationPresenter;
import com.livenation.foresight.service.model.Coordinates;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.subjects.ReplaySubject;

@Singleton public class ReverseGeocoder {
    private final Geocoder geocoder;

    public final ReplaySubject<String> name = ReplaySubject.create(1);

    @Inject public ReverseGeocoder(@NonNull Geocoder geocoder,
                                   @NonNull LocationPresenter locationPresenter) {
        this.geocoder = geocoder;
        locationPresenter.coordinates.subscribe(this::geocodeLocation);
    }

    public void geocodeLocation(Coordinates location) {
        new Thread(() -> {
            try {
                List<Address> addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1);
                if (addresses == null || addresses.isEmpty()) {
                    name.onNext("Current Location");
                } else {
                    name.onNext(AddressFormatter.format(addresses.get(0)));
                }
            } catch (IOException e) {
                name.onNext("Current Location");
            }
        }).start();
    }
}
