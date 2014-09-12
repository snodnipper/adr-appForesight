package java8.util.function;

import java8.lang.FunctionalInterface;

@FunctionalInterface
public interface Supplier<T> {
    T get();
}
