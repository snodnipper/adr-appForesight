package com.livenation.foresight.util;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.livenation.foresight.ForesightApplication;

import butterknife.ButterKnife;

public class InjectionFragment extends Fragment {
    public InjectionFragment() {
        ForesightApplication.getInstance().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SetContentView contentView = getClass().getAnnotation(SetContentView.class);
        if (contentView == null)
            throw new IllegalStateException(getClass().getSimpleName() + " does not specify @SetContentView");
        View view = inflater.inflate(contentView.value(), container, false);
        ButterKnife.inject(this, view);
        return view;
    }
}
