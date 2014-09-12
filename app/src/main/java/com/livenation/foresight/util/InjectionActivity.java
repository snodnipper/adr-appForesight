package com.livenation.foresight.util;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.livenation.foresight.ForesightApplication;

import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Calls {@see ForesightApplication#inject} on construction,
 * then inflates the layout specified by @InjectLayout (required),
 * and finally calls {@see ButterKnife#inject}.
 */
public class InjectionActivity extends FragmentActivity {
    public InjectionActivity() {
        ForesightApplication.getInstance().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectLayout contentView = getClass().getAnnotation(InjectLayout.class);
        if (contentView == null)
            throw new IllegalStateException(getClass().getSimpleName() + " does not specify @SetContentView");
        setContentView(contentView.value());
        ButterKnife.inject(this);
    }

    @Override
    protected void attachBaseContext(@NonNull Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }
}
