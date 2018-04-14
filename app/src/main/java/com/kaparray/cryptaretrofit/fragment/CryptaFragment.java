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
import android.widget.TextView;

import com.kaparray.cryptaretrofit.R;
import com.kaparray.cryptaretrofit.adapters.MyAdapter;
import com.kaparray.cryptaretrofit.adapters.RecyclerItemClickListener;
import com.kaparray.cryptaretrofit.api.CryptaApi;
import com.kaparray.cryptaretrofit.api.CryptaOneApi;
import com.kaparray.cryptaretrofit.data.Data;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CryptaFragment extends Fragment{


    List<Data> userFromServer;
    ProgressBar mProgress;
    TextView mText;
    String id, name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fr_crypta, container, false);

        mText = rootView.findViewById(R.id.tv_TextCrypta);
        mProgress = rootView.findViewById(R.id.progressBar2);

        mText.setVisibility(View.GONE);

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

            CryptaOneApi messagesApi = retrofit.create(CryptaOneApi.class);
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
            mProgress.setVisibility(View.GONE);
            mText.setVisibility(View.VISIBLE);
            mText.setText(userFromServer.get(0).getName());

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
