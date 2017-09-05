package com.mkrworld.stories.customs.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.mkrworld.stories.BuildConfig;
import com.mkrworld.stories.R;
import com.mkrworld.stories.utils.Tracer;

public class Promotion {

    private static final String TAG = BuildConfig.BASE_TAG + ".Promotion";

    /**
     * Method to send review
     *
     * @param context
     */
    public static void sendReview(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName()));
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Tracer.error(TAG, "sendReview() " + e.getMessage());
        }
    }

    /**
     * Method to get more apps
     *
     * @param context
     */
    public static void getMoreApps(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:" + context.getResources().getString(R.string.promotion_publisher_name)));
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Tracer.error(TAG, "getMoreApps() " + e.getMessage());
        }
    }

    /**
     * Method top send feedback
     *
     * @param context
     */
    public static void sendFeedback(Context context) {
        try {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{context.getResources().getString(R.string.promotion_publisher_email)});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.promotion_publisher_subject));
            emailIntent.putExtra(Intent.EXTRA_TEXT, "");
            emailIntent.setType("message/rfc822");
            emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(emailIntent);
        } catch (Exception e) {
            e.printStackTrace();
            Tracer.error(TAG, "sendFeedback() " + e.getMessage());
        }
    }

    /**
     * Method to share application
     *
     * @param context
     */
    public static void shareApp(Context context) {
        String message = context.getResources().getString(R.string.promotion_share) + " http://play.google.com/store/apps/details?id=" + context.getPackageName();
        Intent sentIntent = new Intent(Intent.ACTION_SEND);
        sentIntent.setType("text/plain");
        sentIntent.putExtra(Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.promotion_publisher_subject));
        sentIntent.putExtra(Intent.EXTRA_TEXT, message);
        Intent sendIntent = Intent.createChooser(sentIntent, "Share File");
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(sendIntent);
    }
}
