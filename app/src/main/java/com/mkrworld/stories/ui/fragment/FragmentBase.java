package com.mkrworld.stories.ui.fragment;

import android.support.v4.app.Fragment;

import com.mkrworld.stories.BuildConfig;

/**
 * Created by A1ZFKXA3 on 8/30/2017.
 */

public abstract class FragmentBase extends Fragment {
    private static final String TAG = BuildConfig.BASE_TAG + ".FragmentBase";

    /**
     * Method call when this fragment is reload from back stack
     */
    public abstract void onFragmentReloadFromBackStack();
}
