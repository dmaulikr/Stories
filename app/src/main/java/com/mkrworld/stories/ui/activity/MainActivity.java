package com.mkrworld.stories.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.mkrworld.stories.BuildConfig;
import com.mkrworld.stories.R;
import com.mkrworld.stories.controller.AppPermissionController;
import com.mkrworld.stories.ui.fragment.FragmentHome;
import com.mkrworld.stories.utils.Tracer;


public class MainActivity extends AppCompatActivity implements AppPermissionController.OnAppPermissionControllerListener {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        Tracer.debug(TAG, "onCreateOptionsMenu: ");
        getMenuInflater().inflate(R.menu.menu_story, menu);
        return true;
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
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_fragment_container, new FragmentHome()).commit();
    }
}
