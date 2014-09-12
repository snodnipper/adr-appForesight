package com.livenation.foresight.graph.presenters;

import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;

import com.livenation.foresight.formatters.AddressFormatter;
import com.livenation.foresight.service.model.Coordinates;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.subjects.ReplaySubject;

import static com.livenation.foresight.functional.Functions.filterList;

@Singleton public class GeocodePresenter {
    private final Geocoder geocoder;

    public final ReplaySubject<String> name = ReplaySubject.create(1);

    @Inject public GeocodePresenter(@NonNull Geocoder geocoder,
                                    @NonNull LocationPresenter locationPresenter) {
        this.geocoder = geocoder;
        locationPresenter.coordinates.subscribe(this::geocodeLocation);
    }

    public void geocodeLocation(@NonNull Coordinates location) {
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

    public @NonNull Observable<List<Address>> runQuery(@NonNull String query) {
        return Observable.create((Observable.OnSubscribe<List<Address>>) s -> {
            try {
                List<Address> addresses = geocoder.getFromLocationName(query, 10);
                if (addresses == null || addresses.isEmpty()) {
                    s.onNext(Collections.<Address>emptyList());
                } else {
                    s.onNext(filterList(addresses, a -> a.hasLatitude() && a.hasLongitude()));
                }
            } catch (IOException e) {
                s.onError(e);
            }

            s.onCompleted();
        });
    }
}
