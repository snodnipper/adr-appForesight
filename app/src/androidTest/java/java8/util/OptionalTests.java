package java8.util;

import android.support.annotation.NonNull;

import junit.framework.TestCase;

import java.util.concurrent.atomic.AtomicBoolean;

public class OptionalTests extends TestCase {

    private static void assertThrows(@NonNull Runnable runnable) {
        try {
            runnable.run();
        } catch (Throwable e) {
            return;
        }

        fail("runnable " + runnable + " did not throw an exception");
    }

    private static void assertNoThrow(@NonNull Runnable runnable) {
        try {
            runnable.run();
        } catch (Throwable e) {
            fail("runnable " + runnable + " throw exception " + e);
        }
    }


    public void testEmpty() {
        Optional<Object> empty = Optional.empty();
        assertFalse(empty.isPresent());
        assertThrows(empty::get);
        assertEquals(Optional.empty(), empty.filter(unused -> true));
        assertEquals(Optional.empty(), empty.map(unused -> new Object()));
        assertEquals(Optional.empty(), empty.flatMap(unused -> Optional.empty()));
        AtomicBoolean called = new AtomicBoolean(false);
        empty.ifPresent(unused -> called.set(true));
        assertFalse(called.get());
    }

    public void testWithValue() {
        Optional<Integer> value = Optional.of(12);
        assertTrue(value.isPresent());
        assertNoThrow(value::get);
        assertEquals(12, (int) value.get());
        assertEquals(Optional.of(12), value.filter(unused -> true));
        assertEquals(Optional.<Integer>empty(), value.filter(unused -> false));
        assertEquals(Optional.of(24), value.map(i -> i * 2));
        assertEquals(Optional.of(24), value.flatMap(i -> Optional.of(i * 2)));
        AtomicBoolean called = new AtomicBoolean(false);
        value.ifPresent(i -> {
            assertEquals(12, (int) i);
            called.set(true);
        });
        assertTrue(called.get());
    }

    public void testOfNullable() {
        Optional<Object> shouldBeEmpty = Optional.ofNullable(null);
        assertFalse(shouldBeEmpty.isPresent());
        assertThrows(shouldBeEmpty::get);

        Optional<Object> shouldHaveValue = Optional.ofNullable(new Object());
        assertTrue(shouldHaveValue.isPresent());
        assertNoThrow(shouldHaveValue::get);
    }


    public void testEquals() {
        assertEquals(Optional.empty(), Optional.empty());
        assertEquals(Optional.of(12), Optional.of(12));
        assertFalse(Optional.empty().equals(Optional.of(12)));
    }

    public void testHashCode() {
        assertEquals(0, Optional.empty().hashCode());
        Object value = new Object();
        assertEquals(value.hashCode(), Optional.of(value).hashCode());
    }

}
