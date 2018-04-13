package com.kaparray.cryptaretrofit;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.awt.font.TextAttribute;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    List<Data> userFromServer;

    ProgressBar mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgress = findViewById(R.id.progressBar);

        new MyTask().execute();



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

            mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(mLayoutManager);

            // specify an adapter (see also next example)
            mAdapter = new MyAdapter(userFromServer);
            mRecyclerView.setAdapter(mAdapter);
        }
    }


}



class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Data> userFromServer;


    public MyAdapter(List<Data> userFromServer) {
        this.userFromServer = userFromServer;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.crypta, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setTitleName(userFromServer.get(position).getName());
        holder.setPrice(userFromServer.get(position).getPriceUsd());
    }

    @Override
    public int getItemCount() {
        return userFromServer.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitleName(String title) {
            TextView name = mView.findViewById(R.id.name);
            name.setText(title);
        }

        public void setPrice(String price){
            TextView priceX = mView.findViewById(R.id.price);
            priceX.setText(price);
        }
    }
}


