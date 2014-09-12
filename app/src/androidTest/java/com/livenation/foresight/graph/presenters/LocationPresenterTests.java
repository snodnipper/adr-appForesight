package com.livenation.foresight.graph.presenters;

import com.livenation.foresight.graph.InjectionInstrumentationTestCase;
import com.livenation.foresight.service.model.Coordinates;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import rx.Subscription;

public class LocationPresenterTests extends InjectionInstrumentationTestCase {
    @Inject PreferencesPresenter preferences;
    @Inject LocationPresenter location;


    public void testPreferencesDependency() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Coordinates> coordinates = new AtomicReference<>();
        Subscription subscription = location.coordinates.subscribe(c -> {
            coordinates.set(c);
            latch.countDown();
        });
        preferences.update();
        latch.await(2, TimeUnit.SECONDS);
        subscription.unsubscribe();

        assertNotNull(coordinates.get());
    }
}
