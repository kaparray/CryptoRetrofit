package com.kaparray.cryptaretrofit.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.glomadrian.loadingballs.BallView;
import com.kaparray.cryptaretrofit.R;
import com.kaparray.cryptaretrofit.api.CryptoOneApi;
import com.kaparray.cryptaretrofit.data.Data;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CryptoFragment extends Fragment{


    List<Data> userFromServer;
    BallView mProgress;
    String id, name;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fr_crypta, container, false);

        mProgress = rootView.findViewById(R.id.progressBar2);


        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
            name = bundle.getString("name");
        }



        new MyTask().execute();

        return rootView;
    }



    class MyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected Void doInBackground(Void... voids) {



            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.coinmarketcap.com/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            CryptoOneApi messagesApi = retrofit.create(CryptoOneApi.class);
            Call<List<Data>> call = messagesApi.cryptaOne(id);


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
            mProgress.stop();
            mProgress.setVisibility(View.GONE);

        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Bundle bundle = new Bundle();
        bundle.putString("id", null);
        bundle.putString("name", null);
    }
}
