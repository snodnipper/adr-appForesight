package com.livenation.foresight.functional;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;

import rx.functions.Func1;

public final class Functions {
    public static <I, O> ArrayList<O> mapList(@Nullable Collection<I> collection, @NonNull Func1<I, O> mapper) {
        ArrayList<O> accumulator = new ArrayList<>();
        if (collection != null) {
            for (I value : collection) {
                O result = mapper.call(value);
                if (result == null)
                    continue;

                accumulator.add(result);
            }
        }
        return accumulator;
    }

    public static <T> ArrayList<T> filterList(@Nullable Collection<T> collection, @NonNull Func1<T, Boolean> predicate) {
        ArrayList<T> accumulator = new ArrayList<>();
        if (collection != null) {
            for (T value : collection) {
                if (predicate.call(value))
                    accumulator.add(value);
            }
        }
        return accumulator;
    }
}
