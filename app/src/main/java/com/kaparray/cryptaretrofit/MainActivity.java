package com.kaparray.cryptaretrofit;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.kaparray.cryptaretrofit.fragment.CryptaFragment;
import com.kaparray.cryptaretrofit.fragment.ListCryptaFragment;

public class MainActivity extends AppCompatActivity {

    private ListCryptaFragment listCryptaFragment = new ListCryptaFragment();
    private CryptaFragment cryptaFragment = new CryptaFragment();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .replace(R.id.container, listCryptaFragment)
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.navigation_dashboard:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .replace(R.id.container, cryptaFragment)
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.navigation_notifications:

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.container, listCryptaFragment)
                .addToBackStack(null)
                .commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
