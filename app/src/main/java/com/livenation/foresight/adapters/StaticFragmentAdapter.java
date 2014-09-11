package com.livenation.foresight.adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class StaticFragmentAdapter extends FragmentPagerAdapter {
    private final Tab[] tabs;

    public StaticFragmentAdapter(FragmentManager fm, Tab[] tabs) {
        super(fm);
        this.tabs = tabs;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    @Override
    public Fragment getItem(int position) {
        try {
            return tabs[position].clazz.newInstance();
        } catch (InstantiationException|IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position].title.toUpperCase();
    }

    public static class Tab {
        public final Class<? extends Fragment> clazz;
        public final String title;

        public static Tab from(@NonNull Class<? extends Fragment> clazz, @NonNull String title) {
            return new Tab(clazz, title);
        }

        public Tab(@NonNull Class<? extends Fragment> clazz, @NonNull String title) {
            this.clazz = clazz;
            this.title = title;
        }
    }
}
