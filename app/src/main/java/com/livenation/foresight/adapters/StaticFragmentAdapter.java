package com.livenation.foresight.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class StaticFragmentAdapter extends FragmentPagerAdapter {
    private final Class<?>[] fragments;

    public StaticFragmentAdapter(FragmentManager fm, Class<?>[] fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public Fragment getItem(int i) {
        try {
            return (Fragment) fragments[i].newInstance();
        } catch (InstantiationException|IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
