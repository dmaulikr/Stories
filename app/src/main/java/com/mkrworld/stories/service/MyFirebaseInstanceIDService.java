package com.mkrworld.stories.service;

import com.google.firebase.iid.FirebaseInstanceIdService;
import com.mkrworld.stories.controller.FCMController;
import com.mkrworld.stories.utils.Tracer;

/**
 * Created by a1zfkxa3 on 9/21/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        Tracer.debug(TAG, "onTokenRefresh: ");
        new FCMController(this).registerFCM();
    }
}
