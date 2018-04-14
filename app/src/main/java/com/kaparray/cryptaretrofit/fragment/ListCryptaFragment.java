package com.kaparray.cryptaretrofit.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.kaparray.cryptaretrofit.adapters.MyAdapter;
import com.kaparray.cryptaretrofit.api.CryptaApi;
import com.kaparray.cryptaretrofit.data.Data;

import com.kaparray.cryptaretrofit.R;
import com.kaparray.cryptaretrofit.adapters.RecyclerItemClickListener;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListCryptaFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    List<Data> userFromServer;

    ProgressBar mProgress;

    CryptaFragment cryptaFragment;

    View rootView;

    public ListCryptaFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fr_listcryptafragment, container, false);

        mProgress = rootView.findViewById(R.id.progressBar);

        cryptaFragment = new CryptaFragment();


        new MyTask().execute();

        return rootView;
    }


    class MyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress.setVisibility(View.VISIBLE);
        }


        @Override
        protected Void doInBackground(Void... voids) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.coinmarketcap.com/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            CryptaApi messagesApi = retrofit.create(CryptaApi.class);
            Call<List<Data>> call = messagesApi.cryptaList("0", "100");


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

            mProgress.setVisibility(View.GONE);

            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

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
                    new RecyclerItemClickListener(rootView.getContext(), mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                        @Override public void onItemClick(View view, int position) {
                            // do whatever


                            Bundle bundle = new Bundle();
                            bundle.putString("id", userFromServer.get(position).getId());

                            cryptaFragment.setArguments(bundle);


                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                    .replace(R.id.container, cryptaFragment)
                                    .addToBackStack(null)
                                    .commit();

                        }

                        @Override public void onLongItemClick(View view, int position) {
                            // do whatever
                        }
                    })
            );
        }
    }
}



