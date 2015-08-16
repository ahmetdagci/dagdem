package com.tr.cay.dagdem.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by EXT0175855 on 8/16/2015.
 */
public class AlertDialogUtil
{

    private Context context;

    public AlertDialogUtil(Context context)
    {
        this.context = context;
    }

    public void showMessage(String messageHeader, String messageBody)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(messageHeader);
        alertDialogBuilder
                .setMessage(messageBody)
                .setCancelable(false)
                .setPositiveButton("Tamam", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
