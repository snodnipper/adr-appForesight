package java8.util.function;

import java8.lang.FunctionalInterface;

@FunctionalInterface
public interface Consumer<T> {
    void accept(T t);
}
