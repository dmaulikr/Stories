package com.mkrworld.stories.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mkrworld.stories.BuildConfig;
import com.mkrworld.stories.R;
import com.mkrworld.stories.controller.FetchAppConfigController;
import com.mkrworld.stories.customs.controller.AppPermissionController;
import com.mkrworld.stories.ui.fragment.FragmentBase;
import com.mkrworld.stories.ui.fragment.FragmentHome;
import com.mkrworld.stories.ui.fragment.FragmentOfflineHome;
import com.mkrworld.stories.ui.fragment.FragmentRecyclerView;
import com.mkrworld.stories.utils.Tracer;


public class MainActivity extends AppCompatActivity implements AppPermissionController.OnAppPermissionControllerListener, FetchAppConfigController.OnFetchAppConfigControllerListener, FragmentRecyclerView.OnFragmentRecyclerViewListener {
    private static final String TAG = BuildConfig.BASE_TAG + ".MainActivity";
    private AppPermissionController mAppPermissionController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Tracer.debug(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
        mAppPermissionController = new AppPermissionController(this, this);
        mAppPermissionController.initializedAppPermission();
    }

    @Override
    public void onBackPressed() {
        Tracer.debug(TAG, "onBackPressed: ");
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_fragment_container);
            if (fragment != null && fragment instanceof FragmentBase) {
                ((FragmentBase) fragment).onFragmentReloadFromBackStack();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Tracer.debug(TAG, "onRequestPermissionsResult: ");
        mAppPermissionController.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onAppPermissionControllerListenerHaveAllRequiredPermission() {
        Tracer.debug(TAG, "onAppPermissionControllerListenerHaveAllRequiredPermission: ");
        new FetchAppConfigController(this, this).fetchAppConfig();
    }

    @Override
    public void onFetchAppConfigSuccess() {
        Tracer.debug(TAG, "onFetchAppConfigSuccess: ");
        onFragmentRecyclerViewAddFragmentBackStack(new FragmentHome(), FragmentHome.class.getName());
    }

    @Override
    public void onFetchAppConfigFailed(String errorMessage) {
        Tracer.debug(TAG, "onFetchAppConfigFailed: ");
        Tracer.showSnack(findViewById(R.id.activity_main_fragment_container), errorMessage);
        onFragmentRecyclerViewAddFragmentBackStack(new FragmentOfflineHome(), FragmentOfflineHome.class.getName());
    }

    @Override
    public void onFragmentRecyclerViewAddFragmentBackStack(Fragment fragment, String tag) {
        Tracer.debug(TAG, "onFragmentRecyclerViewAddFragmentBackStack: ");
        getSupportFragmentManager().beginTransaction().add(R.id.activity_main_fragment_container, fragment, tag).addToBackStack(tag).commit();
    }
}
