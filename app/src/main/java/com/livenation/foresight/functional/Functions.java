package com.livenation.foresight.functional;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java8.util.function.Function;
import java8.util.function.Predicate;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Contains common functional operations exported as methods intended to be static imported.
 */
public final class Functions {
    /**
     * Iterates a given Collection and repeated calls a Function on each value,
     * collecting the return value of the Function into a new List.
     * @param collection    The collection to iterate.
     * @param mapper        The function to apply to each value in the collection. Required.
     * @param <I>           The input type contained in the collection.
     * @param <O>           The output type contained in the return value.
     * @return  A new ArrayList instance. Never returns null.
     */
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

    /**
     * Iterates a given collection and repeated calls a Predicate on each value,
     * collecting the values that the Predicate returns true for into a new List.
     * @param collection    The collection to iterate.
     * @param predicate     The predicate to apply to each value in the collection. Required.
     * @param <T>           The type contained in the input and output collections.
     * @return  A new ArrayList instance. Never returns null.
     */
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
