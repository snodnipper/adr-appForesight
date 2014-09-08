package com.livenation.foresight.util;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.livenation.foresight.ForecastApplication;

import butterknife.ButterKnife;

public class InjectionActivity extends FragmentActivity {
    public InjectionActivity() {
        ForecastApplication.getInstance().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetContentView contentView = getClass().getAnnotation(SetContentView.class);
        if (contentView == null)
            throw new IllegalStateException(getClass().getSimpleName() + " does not specify @SetContentView");
        setContentView(contentView.value());
        ButterKnife.inject(this);
    }
}
