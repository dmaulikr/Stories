package com.mkrworld.stories.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.ads.AdView;
import com.mkrworld.stories.BuildConfig;
import com.mkrworld.stories.R;
import com.mkrworld.stories.controller.FCMController;
import com.mkrworld.stories.controller.FetchAppConfigController;
import com.mkrworld.stories.customs.controller.AppPermissionController;
import com.mkrworld.stories.customs.utils.Promotion;
import com.mkrworld.stories.ui.fragment.FragmentBase;
import com.mkrworld.stories.ui.fragment.FragmentHome;
import com.mkrworld.stories.ui.fragment.FragmentOfflineHome;
import com.mkrworld.stories.ui.fragment.FragmentRecyclerView;
import com.mkrworld.stories.utils.AdUtils;
import com.mkrworld.stories.utils.DialogUtils;
import com.mkrworld.stories.utils.PreferenceDataUtils;
import com.mkrworld.stories.utils.Tracer;
import com.mkrworld.stories.utils.Utils;


public class MainActivity extends AppCompatActivity implements AppPermissionController.OnAppPermissionControllerListener, FetchAppConfigController.OnFetchAppConfigControllerListener, FragmentRecyclerView.OnFragmentRecyclerViewListener {
    private static final String TAG = BuildConfig.BASE_TAG + ".MainActivity";
    private AppPermissionController mAppPermissionController;
    private AdUtils mAdUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Tracer.debug(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
        new FCMController(this).registerFCM();
        mAppPermissionController = new AppPermissionController(this, this);
        mAppPermissionController.initializedAppPermission();
        mAdUtils = new AdUtils(this);
        mAdUtils.initInterstitialAdd();
        mAdUtils.showBannerAd((AdView) findViewById(R.id.activity_main_adView), findViewById(R.id.activity_main_container_adView));
    }

    @Override
    protected void onResume() {
        Tracer.debug(TAG, "onResume: ");
        super.onResume();
        if (PreferenceDataUtils.getLatestVersionCode(this) > Utils.getAppVersionCode(this)) {
            showAppUpgradeDialog();
        }
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
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
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
        if (PreferenceDataUtils.getLatestVersionCode(this) > Utils.getAppVersionCode(this)) {
            showAppUpgradeDialog();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_fragment_container, new FragmentHome(), FragmentHome.class.getName()).addToBackStack(FragmentHome.class.getName()).commit();
            mAdUtils.showInterstitialAd();
        }
    }

    @Override
    public void onFetchAppConfigFailed(String errorMessage) {
        Tracer.debug(TAG, "onFetchAppConfigFailed: ");
        Tracer.showSnack(findViewById(R.id.activity_main_fragment_container), errorMessage);
        FragmentOfflineHome fragmentOfflineHome = new FragmentOfflineHome();
        Bundle bundle = new Bundle();
        bundle.putBoolean(FragmentOfflineHome.EXTRA_IS_NETWORK_FAILED, true);
        fragmentOfflineHome.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_fragment_container, fragmentOfflineHome, FragmentOfflineHome.class.getName()).addToBackStack(FragmentOfflineHome.class.getName()).commit();
    }

    @Override
    public void onFragmentRecyclerViewAddFragmentBackStack(Fragment fragment, String tag) {
        Tracer.debug(TAG, "onFragmentRecyclerViewAddFragmentBackStack: ");
        Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragmentByTag == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.activity_main_fragment_container, fragment, tag).addToBackStack(tag).commit();
        }
        mAdUtils.showInterstitialAd();
    }

    /**
     * Method to show the App Upgrade Dialog
     */
    private void showAppUpgradeDialog() {
        Tracer.debug(TAG, "showAppUpgradeDialog: ");
        try {
            DialogUtils.dismissCurrentDialog();
            String title = getString(R.string.dialog_upgrade_dialog_title);
            String message = getString(R.string.dialog_upgrade_dialog_message);
            String okText = getString(R.string.dialog_upgrade_dialog_positive);
            String cancelText = getString(R.string.dialog_upgrade_dialog_negative);
            DialogUtils.showDialogOkCancelDialog(this, false, title, message, okText, cancelText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Tracer.debug(TAG, "showAppUpgradeDialog().onClick: OK");
                    dialogInterface.dismiss();
                    Promotion.sendReview(MainActivity.this);
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Tracer.debug(TAG, "showAppUpgradeDialog().onClick: CANCEL");
                    dialogInterface.dismiss();
                    finish();
                }
            }).show();
        } catch (Exception e) {
            Tracer.error(TAG, "showAppUpgradeDialog: " + e.getMessage());
        }
    }
}
