package com.livenation.foresight.graph.presenters;

import com.livenation.foresight.graph.InjectionInstrumentationTestCase;
import com.livenation.foresight.service.model.Coordinates;
import com.livenation.foresight.service.model.Report;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import rx.Subscription;
import rx.observables.BlockingObservable;

public class ForecastPresenterTests extends InjectionInstrumentationTestCase {
    @Inject LocationPresenter locationPresenter;
    @Inject PreferencesPresenter preferences;

    private ForecastPresenter presenter;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        this.presenter = graph.get(ForecastPresenter.class);
    }


    public void testUpdate() {
        BlockingObservable<Report> reportSignal = presenter.forecast.toBlocking();
        Report report = reportSignal.next().iterator().next();

        assertNotNull(report);
        assertTrue(report.getCurrently().isPresent());
        assertTrue(report.getHourly().isPresent());
        assertTrue(report.getDaily().isPresent());
        assertEquals(report.getLatitude(), Coordinates.DEFAULT.latitude);
        assertEquals(report.getLongitude(), Coordinates.DEFAULT.longitude);
    }

    public void testLocationDependency() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Report> report = new AtomicReference<>();
        Subscription subscription = presenter.forecast.subscribe(r -> {
            report.set(r);
            latch.countDown();
        });
        locationPresenter.update();
        latch.await(2, TimeUnit.SECONDS);
        subscription.unsubscribe();

        assertNotNull(report);
    }

    public void testUnitSystemDependency() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Report> report = new AtomicReference<>();
        Subscription subscription = presenter.forecast.subscribe(r -> {
            report.set(r);
            latch.countDown();
        });
        preferences.update();
        latch.await(2, TimeUnit.SECONDS);
        subscription.unsubscribe();

        assertNotNull(report);
    }
}
