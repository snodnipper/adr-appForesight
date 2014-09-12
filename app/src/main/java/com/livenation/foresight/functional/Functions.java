package com.livenation.foresight.functional;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java8.util.function.Function;
import java8.util.function.Predicate;

import java.util.ArrayList;
import java.util.Collection;

public final class Functions {
    public static <I, O> ArrayList<O> mapList(@Nullable Collection<I> collection, @NonNull Function<I, O> mapper) {
        ArrayList<O> accumulator = new ArrayList<>();
        if (collection != null) {
            for (I value : collection) {
                O result = mapper.apply(value);
                if (result == null)
                    continue;

                accumulator.add(result);
            }
        }
        return accumulator;
    }

    public static <T> ArrayList<T> filterList(@Nullable Collection<T> collection, @NonNull Predicate<T> predicate) {
        ArrayList<T> accumulator = new ArrayList<>();
        if (collection != null) {
            for (T value : collection) {
                if (predicate.test(value))
                    accumulator.add(value);
            }
        }
        return accumulator;
    }
}
