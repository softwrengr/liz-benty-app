package com.square.apple.pdf_app.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.square.apple.pdf_app.R;

/**
 * Created by eapple on 14/08/2018.
 */

public class AlertUtils {

    public static AlertDialog createProgressDialog(Activity activity) {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.progress_dialog, null);

        dialogBuilder.setView(dialogView);
        ProgressBar pd = dialogView.findViewById(R.id.indeterminateBar);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialoge_box);
        alertDialog.getWindow().setAttributes(lp);
        pd.setVisibility(View.VISIBLE);
        return alertDialog;

    }

    public static AlertDialog progressDialog(Activity activity) {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_progress_layout
                , null);

        dialogBuilder.setView(dialogView);
        ProgressBar pd = dialogView.findViewById(R.id.indeterminateBar);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialoge_box);
        alertDialog.getWindow().setAttributes(lp);
        pd.setVisibility(View.VISIBLE);
        return alertDialog;

    }

    public static Dialog createDialog(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_layout);
        TextView tvOk = dialog.findViewById(R.id.ok);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        return dialog;

    }

}
