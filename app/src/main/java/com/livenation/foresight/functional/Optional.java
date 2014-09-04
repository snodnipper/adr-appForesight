package com.livenation.foresight.functional;

import android.support.annotation.Nullable;

import com.livenation.foresight.functional.types.Consumer;
import com.livenation.foresight.functional.types.Function;
import com.livenation.foresight.functional.types.Predicate;
import com.livenation.foresight.functional.types.Supplier;

import java.util.NoSuchElementException;

@SuppressWarnings("UnusedDeclaration")
public final class Optional<T> {
    private final T value;

    //region Creation

    public static <T> Optional<T> empty() {
        return new Optional<>(null);
    }

    public static <T> Optional<T> of(T value) {
        if (value == null)
            throw new NullPointerException();

        return new Optional<>(value);
    }

    public static <T> Optional<T> ofNullable(T value) {
        return new Optional<>(value);
    }

    private Optional(@Nullable T value) {
        this.value = value;
    }

    //endregion


    //region Identity

    public boolean isPresent() {
        return (value != null);
    }

    public T get() {
        if (!isPresent())
            throw new NoSuchElementException();

        return value;
    }

    @Override
    public int hashCode() {
        if (isPresent())
            return get().hashCode();
        else
            return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Optional) {
            @SuppressWarnings("unchecked")
            Optional<T> other = (Optional<T>) o;
            if (isPresent() && other.isPresent()) {
                return get().equals(other.get());
            }
        }

        return false;
    }

    @Override
    public String toString() {
        if (isPresent()) {
            return "{Optional value=" + get().toString() + "}";
        } else {
            return "{Optional empty}";
        }
    }

    //endregion


    //region Operations

    public Optional<T> filter(Predicate<? super T> predicate) {
        if (isPresent() && predicate.test(get()))
            return this;
        else
            return empty();
    }

    public <U> Optional<U> flatMap(Function<? super T, Optional<U>> mapper) {
        if (isPresent())
            return mapper.apply(get());
        else
            return empty();
    }

    public void ifPresent(Consumer<? super T> consumer) {
        if (isPresent())
            consumer.accept(get());
    }

    public <U> Optional<U> map(Function<? super T, ? extends U> mapper) {
        if (isPresent()) {
            return ofNullable(mapper.apply(get()));
        } else {
            return empty();
        }
    }

    public T orElse(T other) {
        if (isPresent())
            return get();
        else
            return other;
    }

    public T orElseGet(Supplier<? extends T> other) {
        if (isPresent())
            return get();
        else
            return other.get();
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (isPresent())
            return get();
        else
            throw exceptionSupplier.get();
    }

    //endregion
}
