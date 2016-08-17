package io.github.riyanshkarani011235.inventoryapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

/**
 * Created by ironstein on 29/07/16.
 */
public class OrderDialogFragment extends DialogFragment {

    DialogListener listener;
    static View view;
    static NumberPicker picker;

    public interface DialogListener {
        public void onOkClick(DialogFragment dialog, int orderQuantity);
        public void onCancelClick(DialogFragment dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        view = layoutInflater.inflate(R.layout.order_dialog_fragment_view, null);

        // NumberPicker
        picker = (NumberPicker) view.findViewById(R.id.order_quantity);
        String[] pickerDisplayedValues = new String[1000];
        for(int i=0; i<1000; i++) {
            pickerDisplayedValues[i] = "" + i;
        }
        picker.setDisplayedValues(pickerDisplayedValues);
        picker.setMinValue(0);
        picker.setMaxValue(999);

        builder.setView(view)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        picker.clearFocus();
                        listener.onOkClick(OrderDialogFragment.this, picker.getValue());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onCancelClick(OrderDialogFragment.this);
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (DialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                + " Must implement the DialogListener interface");
        }
    }

}
