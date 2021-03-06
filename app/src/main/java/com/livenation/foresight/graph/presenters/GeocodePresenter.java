package com.livenation.foresight.graph.presenters;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;

import com.livenation.foresight.service.model.Coordinates;
import com.livenation.foresight.util.Formatter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.subjects.ReplaySubject;

import static com.livenation.foresight.functional.Functions.filterList;

@Singleton public class GeocodePresenter implements Presenter {
    private final Geocoder geocoder;
    private final Formatter formatter;

    public final ReplaySubject<String> name = ReplaySubject.create(1);

    @Inject public GeocodePresenter(@NonNull Geocoder geocoder,
                                    @NonNull LocationPresenter locationPresenter,
                                    @NonNull Context context) {
        this.geocoder = geocoder;
        this.formatter = new Formatter(context);

        locationPresenter.coordinates.subscribe(this::geocodeLocation);
    }

    public void geocodeLocation(@NonNull Coordinates location) {
        new Thread(() -> {
            try {
                List<Address> addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1);
                if (addresses == null || addresses.isEmpty()) {
                    name.onNext(formatter.getPlaceholderAddress());
                } else {
                    name.onNext(formatter.formatAddress(addresses.get(0)));
                }
            } catch (IOException e) {
                name.onNext(formatter.getPlaceholderAddress());
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
