package java8.util.function;

import java8.lang.FunctionalInterface;

@FunctionalInterface
public interface Predicate<T> {
    boolean test(T t);
}
