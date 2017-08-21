package com.mkrworld.stories.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Toolbar;

import com.mkrworld.stories.BuildConfig;
import com.mkrworld.stories.R;
import com.mkrworld.stories.ui.fragment.FragmentHome;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = BuildConfig.BASE_TAG + ".MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_fragment_container, new FragmentHome()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }
}
