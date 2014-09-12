package java8.util.function;

import java8.lang.FunctionalInterface;

@FunctionalInterface
public interface Function<T, R> {
    R apply(T t);
}
