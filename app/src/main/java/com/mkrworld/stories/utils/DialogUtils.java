package com.mkrworld.stories.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mkrworld.stories.BuildConfig;
import com.mkrworld.stories.R;

/**
 * Created by a1zfkxa3 on 9/29/2017.
 */

public class DialogUtils {

    private static final String TAG = BuildConfig.BASE_TAG + ".DialogUtils";
    private static Dialog mCurrentDialog;

    /**
     * Method to create Ok-Cancel Dialog. But Not show the Dialog
     *
     * @param activity
     * @param isCancelable
     * @param titleText
     * @param messageText
     * @param okText
     * @param cancelText
     * @param okOnClickListener
     * @param cancelOnClickListener
     * @return
     */
    public static Dialog showDialogOkCancelDialog(final Activity activity, boolean isCancelable, String titleText, String messageText, String okText, String cancelText, final DialogInterface.OnClickListener okOnClickListener, final DialogInterface.OnClickListener cancelOnClickListener) {
        Tracer.debug(TAG, "showDialogOkCancelDialog: ");
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(activity, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(activity);
        }
        builder.setCancelable(isCancelable);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle(titleText);
        builder.setMessage(messageText);
        builder.setPositiveButton(okText,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (okOnClickListener != null) {
                            okOnClickListener.onClick(dialog, which);
                        } else {
                            dialog.dismiss();
                        }
                    }
                });
        builder.setNegativeButton(cancelText,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (cancelOnClickListener != null) {
                            cancelOnClickListener.onClick(dialog, which);
                        } else {
                            dialog.dismiss();
                        }
                    }
                });
        return mCurrentDialog = builder.create();
    }

    /**
     * Method top show font size dialog, But not shown
     *
     * @param activity
     * @param maxIndex
     * @param currentIndex
     * @param trashHold
     * @param titleText
     * @param okText
     * @param cancelText
     * @param okOnClickListener
     * @param cancelOnClickListener
     * @return
     */
    public static Dialog showFontSize(Activity activity, int maxIndex, int currentIndex, final int trashHold, String titleText, String message, String okText, String cancelText, final DialogInterface.OnClickListener okOnClickListener, final DialogInterface.OnClickListener cancelOnClickListener) {
        final AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(activity, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(activity);
        }
        builder.setTitle(titleText);
        builder.setMessage(message);
        View parentView = activity.getLayoutInflater().inflate(R.layout.dialog_seek, null);
        builder.setView(parentView);
        final SeekBar seek = (SeekBar) parentView.findViewById(R.id.dialog_seek_seekBar);
        seek.setMax(maxIndex);
        final TextView text = (TextView) parentView.findViewById(R.id.dialog_seek_textView);
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                text.setText("" + (progress + trashHold) + " %");
            }
        });
        seek.setProgress(currentIndex);
        builder.setPositiveButton(okText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (okOnClickListener != null) {
                    okOnClickListener.onClick(dialog, seek.getProgress() + trashHold);
                } else {
                    dialog.dismiss();
                }
            }
        });
        builder.setNegativeButton(cancelText, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (cancelOnClickListener != null) {
                    cancelOnClickListener.onClick(dialog, which);
                } else {
                    dialog.dismiss();
                }
            }
        });
        return mCurrentDialog = builder.create();
    }

    /**
     * Method to dismiss the current dialog
     */
    public static void dismissCurrentDialog() {
        Tracer.debug(TAG, "dismissCurrentDialog: ");
        if (isCurrentDialogShown()) {
            mCurrentDialog.dismiss();
        }
        mCurrentDialog = null;
    }

    /**
     * Method to check weather current dialog is shown or not
     *
     * @return TRUE if shown, else FALSE
     */
    public static boolean isCurrentDialogShown() {
        Tracer.debug(TAG, "isCurrentDialogShown: ");
        return mCurrentDialog != null && mCurrentDialog.isShowing();
    }

}
