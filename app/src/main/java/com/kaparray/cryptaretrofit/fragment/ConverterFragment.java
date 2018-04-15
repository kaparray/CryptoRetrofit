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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.glomadrian.loadingballs.BallView;
import com.kaparray.cryptaretrofit.R;
import com.kaparray.cryptaretrofit.adapters.MyAdapter;
import com.kaparray.cryptaretrofit.adapters.RecyclerItemClickListener;
import com.kaparray.cryptaretrofit.api.CryptoApi;
import com.kaparray.cryptaretrofit.api.CryptoOneApi;
import com.kaparray.cryptaretrofit.data.Data;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConverterFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    EditText mTextWhence;
    EditText mTextWhere;


    Spinner mWhenceSpinner;
    Spinner mWhereSpinner;

    BallView mProgress;

    List<Data> userFromServer;

    String spinnerWhence = "0";
    String spinnerWhere = "0";

    String item;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fr_converter, container, false);

        mProgress = rootView.findViewById(R.id.progressBar3);
        mTextWhence = rootView.findViewById(R.id.et_whenceCrypto);
        mTextWhere = rootView.findViewById(R.id.et_whereCrypto);
        mWhereSpinner = rootView.findViewById(R.id.spinner_where);
        mWhenceSpinner = rootView.findViewById(R.id.spinner_whence);


        mWhenceSpinner.setOnItemSelectedListener(this);
        mWhereSpinner.setOnItemSelectedListener(this);

        new MyTask().execute();



        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item



        Spinner spinner = (Spinner) parent;
        switch (spinner.getId()) {
            case R.id.spinner_whence:
                spinnerWhence = position + "";
                break;
            case R.id.spinner_where:

                spinnerWhere = position + "";
                item = parent.getItemAtPosition(position).toString();
                break;

            default:
                break;

        }
        // Showing selected spinner item

        Double whence = Double.parseDouble(userFromServer.get(Integer.parseInt(spinnerWhence)).getPriceUsd());
        Double where = Double.parseDouble(userFromServer.get(Integer.parseInt(spinnerWhere)).getPriceUsd());
       // Log.d("0000000", item );
        mTextWhere.setText(((whence * Double.parseDouble(mTextWhence.getText().toString())) / where ) + "");
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
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

            CryptoApi messagesApi = retrofit.create(CryptoApi.class);
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
            mProgress.stop();
            mProgress.setVisibility(View.GONE);

            mWhenceSpinner.setVisibility(View.VISIBLE);
            mWhereSpinner.setVisibility(View.VISIBLE);
            mTextWhence.setVisibility(View.VISIBLE);
            mTextWhere.setVisibility(View.VISIBLE);

            if (userFromServer != null) {
                List<String> crypto = new ArrayList<>();

                for (int i = 0; i < userFromServer.size(); i++) {
                    crypto.add(userFromServer.get(i).getName());
                }


                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, crypto);


                mWhenceSpinner.setAdapter(dataAdapter);
                mWhereSpinner.setAdapter(dataAdapter);


            } else {
                // Set textView in center and set text in error
            }
        }
    }
}