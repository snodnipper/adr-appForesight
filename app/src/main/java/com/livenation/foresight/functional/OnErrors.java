package com.livenation.foresight.functional;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.livenation.foresight.R;

import rx.functions.Action1;

public class OnErrors {
    public static final Action1<Throwable> SILENTLY_IGNORE_THEM = e -> {};
    public static final Action1<Throwable> NOISILY_IGNORE_THEM = e -> Log.e("Foresight-Reactive", "An error has occurred", e);

    public static Action1<Throwable> showDialogFrom(FragmentManager fm) {
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
