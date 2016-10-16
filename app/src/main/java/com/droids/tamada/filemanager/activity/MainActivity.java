package com.droids.tamada.filemanager.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.droids.tamada.filemanager.fragments.ExternalFragment;
import com.droids.tamada.filemanager.fragments.InternalFragment;
import com.example.satish.filemanager.R;


public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private CustomBackPressListener customBackPressListener;

    public void setCustomBackPressInternalListener(InternalFragment customBackPressListener) {
        this.customBackPressListener = customBackPressListener;
    }

    public void setCustomBackPressExternalListener(ExternalFragment externalFragment) {
        this.customBackPressListener = externalFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //set toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        FragmentDrawer drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        loadFragment(new ExternalFragment(this), getResources().getString(R.string.title_external));
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new InternalFragment(this);
                title = getString(R.string.title_internal);
                break;
            case 1:
                fragment = new ExternalFragment(this);
                title = getString(R.string.title_external);
                break;
            default:
                break;
        }
        if (fragment != null) {
            loadFragment(fragment, title);
        }
    }

    private void loadFragment(Fragment fragment, String title) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.commit();
        // set the toolbar title
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        customBackPressListener.isBackPressed();
    }

    public interface CustomBackPressListener {
        void isBackPressed();
    }
}
