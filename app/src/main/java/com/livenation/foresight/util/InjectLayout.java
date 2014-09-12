package com.livenation.foresight.util;

import android.support.annotation.LayoutRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Decorator to be used with injection activities and fragments.
 * Specifies what layout to implicitly load during creation.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface InjectLayout {
    /**
     * A layout resource id.
     */
    @LayoutRes int value();
}
