package com.mkrworld.stories.utils;

import android.content.Context;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.mkrworld.stories.BuildConfig;
import com.mkrworld.stories.R;

/**
 * Created by a1zfkxa3 on 9/8/2017.
 */

public class AdUtils {
    private static final String TAG = BuildConfig.BASE_TAG + ".AdUtils";
    private Context mContext;
    private InterstitialAd mInterstitialAdExtractApp;

    public AdUtils(Context context) {
        Tracer.debug(TAG, "AdUtils: ");
        mContext = context;
    }

    /**
     * Method to show banner Ad
     *
     * @param adView          Google ad View
     * @param adViewContainer AdContainer whose visibility set GONE when unable to fetch ad
     */
    public void showBannerAd(AdView adView, final View adViewContainer) {
        Tracer.debug(TAG, "showBannerAd: ");
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Tracer.debug(TAG, "showBannerAd()");
                if (adViewContainer != null) {
                    adViewContainer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if (adViewContainer != null) {
                    adViewContainer.setVisibility(View.VISIBLE);
                }
            }
        });
        adView.loadAd(getAdRequest());
    }

    /**
     * Method to show the Interstitial ad
     */
    public void showInterstitialAd() {
        Tracer.debug(TAG, "showInterstitialAd()");
        try {
            if (mInterstitialAdExtractApp == null) {
                initInterstitialAdd();
            }
            if (mInterstitialAdExtractApp.isLoaded()) {
                mInterstitialAdExtractApp.show();
                initInterstitialAdd();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Tracer.error(TAG, "showInterstitialAd() " + e.getMessage());
        }
    }

    /**
     * Method to init the interstitial Ad
     */
    public void initInterstitialAdd() {
        Tracer.debug(TAG, "initInterstitialAdd()");
        mInterstitialAdExtractApp = new InterstitialAd(mContext);
        mInterstitialAdExtractApp.setAdUnitId(mContext.getString(R.string.interstitial_ad_id));
        mInterstitialAdExtractApp.loadAd(getAdRequest());
    }

    /**
     * Method to get the request for the ad
     *
     * @return
     */
    private AdRequest getAdRequest() {
        Tracer.debug(TAG, "getAdRequest()");
        return new AdRequest.Builder().build();
    }
}
