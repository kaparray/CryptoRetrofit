package com.kaparray.cryptaretrofit;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.kaparray.cryptaretrofit.fragment.ConverterFragment;
import com.kaparray.cryptaretrofit.fragment.CryptoFragment;
import com.kaparray.cryptaretrofit.fragment.ListCryptoFragment;

public class MainActivity extends AppCompatActivity {

    private ListCryptoFragment listCryptaFragment = new ListCryptoFragment();
    private CryptoFragment cryptoFragment = new CryptoFragment();
    private ConverterFragment converterFragment = new ConverterFragment();

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
                case R.id.navigation_converter:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .replace(R.id.container, converterFragment)
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.navigation_settings:

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
