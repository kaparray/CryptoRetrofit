package com.kaparray.cryptaretrofit.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.glomadrian.loadingballs.BallView;
import com.kaparray.cryptaretrofit.adapters.MyAdapter;
import com.kaparray.cryptaretrofit.api.CryptoApi;
import com.kaparray.cryptaretrofit.data.Data;

import com.kaparray.cryptaretrofit.R;
import com.kaparray.cryptaretrofit.adapters.RecyclerItemClickListener;

import java.io.IOException;
import java.util.List;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListCryptoFragment extends Fragment{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    List<Data> userFromServer;

    BallView mProgress;

    CryptoFragment cryptoFragment;

    Toolbar myToolbar;
    View rootView;


    public static boolean hasConnection(final Context context)
    {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        return false;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.setLen:
                final MaterialNumberPicker numberPicker = new MaterialNumberPicker.Builder(getContext())
                        .minValue(1)
                        .maxValue(1000)
                        .defaultValue(1)
                        .backgroundColor(Color.WHITE)
                        .separatorColor(Color.TRANSPARENT)
                        .textColor(Color.BLACK)
                        .textSize(20)
                        .enableFocusability(false)
                        .wrapSelectorWheel(true)
                        .build();

                new AlertDialog.Builder(getContext())
                        .setTitle("number")
                        .setView(numberPicker)
                        .setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new MyTask(numberPicker.getValue()+"").execute();
                            }
                        })
                        .show();
                return true;
            case R.id.settings:
                new MyTask("100").execute();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Set view for fragment
        rootView = inflater.inflate(R.layout.fr_listcryptafragment, container, false);
        // Set menu in fragment
        setHasOptionsMenu(true);


        mProgress = rootView.findViewById(R.id.progressBar1);

        cryptoFragment = new CryptoFragment();

        myToolbar = (Toolbar) rootView.findViewById(R.id.my_toolbar);
        myToolbar.setTitle("List");
        myToolbar.setTitleTextColor(getResources().getColor(R.color.White));
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);


        if(hasConnection(getContext())) {
            new MyTask("100").execute();
        }else{
            Toast.makeText(getActivity(), "NO INTERNET CONECTION", Toast.LENGTH_LONG).show();
        }

        return rootView;
    }


    class MyTask extends AsyncTask<Void, Void, Void> {

        String len;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
            mRecyclerView.setVisibility(View.GONE);
            mProgress.setVisibility(View.VISIBLE);
        }

        public MyTask(String len) {
            this.len = len;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.coinmarketcap.com/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            CryptoApi messagesApi = retrofit.create(CryptoApi.class);
            Call<List<Data>> call = messagesApi.cryptaList("0", len);


            try {
                Response<List<Data>> userResponse = call.execute();
                userFromServer = userResponse.body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (userFromServer != null) {
                mProgress.setVisibility(View.GONE);

                mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
                mRecyclerView.setVisibility(View.VISIBLE);
                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                mRecyclerView.setHasFixedSize(true);

                // use a linear layout manager
                mLayoutManager = new LinearLayoutManager(rootView.getContext());
                mRecyclerView.setLayoutManager(mLayoutManager);

                // specify an adapter (see also next example)
                mAdapter = new MyAdapter(userFromServer, getContext());
                mRecyclerView.setAdapter(mAdapter);

                mRecyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(rootView.getContext(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                // do whatever


                                Bundle bundle = new Bundle();
                                bundle.putString("id", userFromServer.get(position).getId());

                                cryptoFragment.setArguments(bundle);


                                getActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                        .replace(R.id.container, cryptoFragment)
                                        .addToBackStack(null)
                                        .commit();

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                // do whatever
                            }
                        })
                );
            } else {
                // Set textView in center and set text in error
            }
        }



    }
}



