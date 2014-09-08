package com.livenation.foresight.util;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.livenation.foresight.ForesightApplication;

import butterknife.ButterKnife;

public class InjectionListFragment extends ListFragment {
    public InjectionListFragment() {
        ForesightApplication.getInstance().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SetContentView contentView = getClass().getAnnotation(SetContentView.class);
        if (contentView == null) {
            return super.onCreateView(inflater, container, savedInstanceState);
        } else {
            View view = inflater.inflate(contentView.value(), container, false);
            ButterKnife.inject(this, view);
            return view;
        }
    }
}
