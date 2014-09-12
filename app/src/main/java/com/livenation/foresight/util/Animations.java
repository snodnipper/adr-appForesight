package com.livenation.foresight.util;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;

import java8.util.Optional;
import java8.util.function.Consumer;

public class Animations {
    public static final long DURATION_MS = 250;
    public static final TimeInterpolator DEFAULT_INTERPOLATOR = new AccelerateDecelerateInterpolator();

    public static ViewPropertyAnimator animate(@NonNull View view, @NonNull Optional<Consumer<Boolean>> onCompletion) {
        return view.animate()
                   .setDuration(DURATION_MS)
                   .setInterpolator(DEFAULT_INTERPOLATOR)
                   .setListener(new Animator.AnimatorListener() {
                       @Override
                       public void onAnimationStart(Animator animator) {
                       }

                       @Override
                       public void onAnimationEnd(@NonNull Animator animator) {
                           onCompletion.ifPresent(f -> f.accept(true));
                       }

                       @Override
                       public void onAnimationCancel(Animator animator) {
                           onCompletion.ifPresent(f -> f.accept(false));
                       }

                       @Override
                       public void onAnimationRepeat(Animator animator) {
                       }
                   });
    }

    public static ViewPropertyAnimator animate(@NonNull View view) {
        return animate(view, Optional.empty());
    }

    public static RecyclerView.ItemAnimator createItemAnimator() {
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(DURATION_MS);
        animator.setMoveDuration(DURATION_MS);
        animator.setRemoveDuration(DURATION_MS);
        return animator;
    }
}
