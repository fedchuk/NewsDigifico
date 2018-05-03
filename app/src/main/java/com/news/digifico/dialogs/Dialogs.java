package com.news.digifico.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.news.digifico.R;

/**
 * Created by Fedchuk Maxim on 2018-05-03.
 */
public class Dialogs {
    private static AlertDialog dialog;

    public static void noInternetConnectionDialog(Context context){
        dialog = new AlertDialog.Builder(context).create();
        if (!dialog.isShowing()){
            dialog.setMessage(context.getString(R.string.no_internet_dialog));
            dialog.setButton(AlertDialog.BUTTON_NEUTRAL, context.getString(R.string.neutral_btn), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
}
