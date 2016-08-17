package io.github.riyanshkarani011235.inventoryapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by ironstein on 28/07/16.
 */
public class TakePhotoDialogFragment extends DialogFragment {

    DialogListener listener;

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface DialogListener {
        public void onDialogTakePhotoClick(DialogFragment dialog);
        public void onDialogSelectFromGalleryClick(DialogFragment dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String[] inputArray = new String[2];
        inputArray[0] = "Take Photo";
        inputArray[1] = "Select from Gallery";

        builder.setTitle("Choose input method")
                .setItems(inputArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i == 0) {
                            listener.onDialogTakePhotoClick(TakePhotoDialogFragment.this);
                        } else if(i == 1) {
                            listener.onDialogSelectFromGalleryClick(TakePhotoDialogFragment.this);
                        }
                    }
                });
        return builder.create();
    }

    // Override the Fragment.onAttach() method to instantiate the DialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // check if the host activity implements the DialogListener
        try {
            // Instantiate the DialogListener so we can send events to the host
            listener = (DialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
