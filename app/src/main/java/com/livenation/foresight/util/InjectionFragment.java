package com.livenation.foresight.util;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.livenation.foresight.ForesightApplication;

import butterknife.ButterKnife;

/**
 * Calls {@see ForesightApplication#inject} on construction,
 * then inflates the layout specified by @InjectLayout (required),
 * and finally calls {@see ButterKnife#inject}.
 */
public class InjectionFragment extends Fragment {
    public InjectionFragment() {
        ForesightApplication.getInstance().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        InjectLayout contentView = getClass().getAnnotation(InjectLayout.class);
        if (contentView == null)
            throw new IllegalStateException(getClass().getSimpleName() + " does not specify @SetContentView");
        View view = inflater.inflate(contentView.value(), container, false);
        ButterKnife.inject(this, view);
        return view;
    }
}
