package com.livenation.foresight.util;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.livenation.foresight.ForesightApplication;

import butterknife.ButterKnife;

/**
 * Calls {@see ForesightApplication#inject} on construction,
 * then inflates the layout specified by @InjectLayout (optional),
 * and finally calls {@see ButterKnife#inject}.
 */
public class InjectionListFragment extends ListFragment {
    public InjectionListFragment() {
        ForesightApplication.getInstance().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        InjectLayout contentView = getClass().getAnnotation(InjectLayout.class);
        if (contentView == null) {
            return super.onCreateView(inflater, container, savedInstanceState);
        } else {
            View view = inflater.inflate(contentView.value(), container, false);
            ButterKnife.inject(this, view);
            return view;
        }
    }
}
