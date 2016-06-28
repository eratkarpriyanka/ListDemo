package com.codepath.listdemo.dialogs;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class DeleteItemDialog extends DialogFragment{

    private static final String TITLE = "title";
    private static final String MESSAGE = "message";
    private ConfirmDeleteListener confirmDeleteListener;

    public DeleteItemDialog() {

    }

    public static DeleteItemDialog singleInstance(String strTitle,String strMessage){

        DeleteItemDialog dialogInstance = new DeleteItemDialog();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE,strTitle);
        bundle.putString(MESSAGE,strMessage);
        dialogInstance.setArguments(bundle);
        return dialogInstance;
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {

        Bundle savedInstanceState = getArguments();
        String strTitle = savedInstanceState.getString(TITLE);
        String strMessage = savedInstanceState.getString(MESSAGE);
        Activity activity = getActivity();
        if(activity == null)
            dismiss();

        confirmDeleteListener = (ConfirmDeleteListener)getActivity();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle(strTitle);
        alertDialogBuilder.setMessage(strMessage);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                confirmDeleteListener.onConfirmDelete(true);
                dialog.dismiss();
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                confirmDeleteListener.onConfirmDelete(false);
                dialog.dismiss();
            }
        });

        return alertDialogBuilder.create();

    }

    public interface ConfirmDeleteListener{
        void onConfirmDelete(boolean shouldDelete);
    }
}
