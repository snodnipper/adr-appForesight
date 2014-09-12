package com.livenation.foresight.functional;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.livenation.foresight.R;

import rx.functions.Action1;

/**
 * Provides common error handlers for use with Rx Observable subscriptions.
 */
public final class OnErrors {
    /**
     * Silently consumes errors.
     */
    public static final Action1<Throwable> SILENTLY_IGNORE_THEM = e -> {};

    /**
     * Noisily consumes errors by printing them to the Android system log.
     */
    public static final Action1<Throwable> NOISILY_IGNORE_THEM = e -> Log.e("Foresight-Reactive", "An error has occurred", e);

    /**
     * Returns a new error handler that uses a given fragment manager to display errors in dialog fragments.
     * @param fm    The fragment manager to show the dialogs from.
     * @return  A new error handler.
     */
    public static @NonNull Action1<Throwable> showDialogFrom(@NonNull FragmentManager fm) {
        return e -> {
            DialogFragment dialogFragment = new DialogFragment() {
                @Override
                public void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);

                    setCancelable(true);
                }

                @Override
                public @NonNull Dialog onCreateDialog(Bundle savedInstanceState) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setTitle(getString(R.string.error_dialog_title));
                    builder.setMessage(e.getMessage());
                    builder.setPositiveButton(android.R.string.ok, null);

                    return builder.create();
                }
            };
            dialogFragment.show(fm, "Errors.showDialog.fragment");
        };
    }
}
