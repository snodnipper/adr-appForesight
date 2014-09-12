package com.livenation.foresight.adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class StaticFragmentAdapter extends FragmentPagerAdapter {
    private final Item[] items;

    public StaticFragmentAdapter(FragmentManager fm, Item[] items) {
        super(fm);
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Fragment getItem(int position) {
        try {
            return items[position].clazz.newInstance();
        } catch (InstantiationException|IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return items[position].title.toUpperCase();
    }

    public static class Item {
        public final Class<? extends Fragment> clazz;
        public final String title;

        public static Item with(@NonNull Class<? extends Fragment> clazz, @NonNull String title) {
            return new Item(clazz, title);
        }

        public Item(@NonNull Class<? extends Fragment> clazz, @NonNull String title) {
            this.clazz = clazz;
            this.title = title;
        }
    }
}
