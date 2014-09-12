package com.livenation.foresight.ui;

import android.support.annotation.NonNull;
import android.view.View;

import com.livenation.foresight.R;
import java8.util.Optional;
import com.livenation.foresight.graph.presenters.LocationPresenter;
import com.livenation.foresight.graph.presenters.PreferencesPresenter;
import com.livenation.foresight.util.InjectLayout;
import com.livenation.foresight.util.InjectionActivity;

import javax.inject.Inject;

import butterknife.OnClick;

@InjectLayout(R.layout.activity_location)
public class LocationActivity extends InjectionActivity {
    @Inject PreferencesPresenter preferences;
    @Inject LocationPresenter location;

    @OnClick(R.id.activity_location_use_current)
    @SuppressWarnings("UnusedDeclaration")
    public void onUseCurrentLocationClicked(@NonNull View view) {
        // The location manager will be implicitly reloaded
        // by the saved manual location value changing. We
        // explicitly call out that we want an update from
        // the GPS so that if the user pressed this button
        // due to location caching issues, the Right Thing
        // will happen.
        location.setWantsUpdate();
        preferences.setSavedManualLocation(Optional.empty());
    }
}
