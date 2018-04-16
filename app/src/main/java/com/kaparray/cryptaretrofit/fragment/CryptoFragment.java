package com.kaparray.cryptaretrofit.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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


    ImageView imageView;
    List<Data> userFromServer;
    BallView mProgress;
    String id, name;

    CardView cardViewTitle, cardViewData;
    TextView mNameText;
    TextView mPriceUSD;
    TextView mPriceBTC;
    TextView mVolume24h;
    TextView mMarketCap;
    TextView mAvailableSupply;
    TextView mTotalSupply;
    TextView mPercent1h;
    TextView mPercent24h;
    TextView mPercent7d;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fr_crypta, container, false);

        mProgress = rootView.findViewById(R.id.progressBar2);
        imageView = rootView.findViewById(R.id.iv_CryptoIcon);
        cardViewTitle = rootView.findViewById(R.id.cardView);
        cardViewData = rootView.findViewById(R.id.cardView2);
        mNameText = rootView.findViewById(R.id.tv_nameCrypto);
        mPriceUSD  = rootView.findViewById(R.id.tv_priceUSD);
        mPriceBTC = rootView.findViewById(R.id.tv_priceBTC);
        mVolume24h = rootView.findViewById(R.id.tv_Volume24h);
        mMarketCap = rootView.findViewById(R.id.tv_marketCap);
        mAvailableSupply = rootView.findViewById(R.id.tv_AvailableSupply);
        mTotalSupply = rootView.findViewById(R.id.tv_TotalSupply);
        mPercent1h = rootView.findViewById(R.id.tv_Percent1h);
        mPercent24h = rootView.findViewById(R.id.tv_Percent24h);
        mPercent7d = rootView.findViewById(R.id.tv_Percent7d);


        mProgress.setVisibility(View.VISIBLE);


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
            mProgress.setVisibility(View.GONE);
            setIcon(userFromServer.get(0).getSymbol());
            mNameText.setText(userFromServer.get(0).getName()); // Set Icon
            mPriceUSD.setText("Price USD:  " + userFromServer.get(0).getPriceUsd() + "$");
            mPriceBTC.setText("Price BTC:  " + userFromServer.get(0).getPriceBtc() + " BTC");
            mVolume24h.setText("Volume USD behind 24h:  " + userFromServer.get(0).get24hVolumeUsd() + "$");
            mMarketCap.setText("Market cap USD:  " + userFromServer.get(0).getMarketCapUsd() + "$");
            mAvailableSupply.setText("Available supply:  " + userFromServer.get(0).getAvailableSupply() + "$");
            mTotalSupply.setText("Total supply:  " + userFromServer.get(0).getTotalSupply() + "$");
            mPercent1h.setText("Percent change 1h:  " + userFromServer.get(0).getPercentChange1h() + "%");
            mPercent24h.setText("Percent change 24h:  " + userFromServer.get(0).getPercentChange24h() + "%");
            mPercent7d.setText("Percent change 7d:  " + userFromServer.get(0).getPercentChange7d() + "%");


            cardViewTitle.setVisibility(View.VISIBLE);
            cardViewData.setVisibility(View.VISIBLE);

        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Bundle bundle = new Bundle();
        bundle.putString("id", null);
        bundle.putString("name", null);
    }




    void setIcon(String Symbol){
// Set icon for crypto currency
        if(Symbol.equals("ABT")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.abt));
        }else if (Symbol.equals("ACT")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.act));
        } else if (Symbol.equals("ADA")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ada));
        }else if (Symbol.equals("ADX")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.adx));
        }else if (Symbol.equals("AE")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ae));
        }else if (Symbol.equals("AGI")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.agi));
        }else if (Symbol.equals("AGRS")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.agrs));
        }else if (Symbol.equals("AION")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.aion));
        }else if (Symbol.equals("AMB")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.amb));
        }else if (Symbol.equals("AMP")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.amp));
        }else if (Symbol.equals("ANT")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ant));
        }else if (Symbol.equals("APPC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.appc));
        }else if (Symbol.equals("ARDR")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ardr));
        }else if (Symbol.equals("ARK")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ark));
        }else if (Symbol.equals("ARN")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.arn));
        }else if (Symbol.equals("ARY")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ary));
        }else if (Symbol.equals("AST")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ast));
        }else if (Symbol.equals("ATM")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.atm));
        }else if (Symbol.equals("AUTO")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.auto));
        }else if (Symbol.equals("BAT")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.bat));
        }else if (Symbol.equals("BAY")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.bay));
        }else if (Symbol.equals("BCC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.bcc));
        }else if (Symbol.equals("BCD")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.bcd));
        }else if (Symbol.equals("BCH")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.bch));
        }else if (Symbol.equals("BCN")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.bcn));
        }else if (Symbol.equals("BCO")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.bco));
        }else if (Symbol.equals("BCPT")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.bcpt));
        }else if (Symbol.equals("BDL")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.bdl));
        }else if (Symbol.equals("BELA")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.bela));
        }else if (Symbol.equals("BIX")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.bix));
        }else if (Symbol.equals("BLCN")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.blcn));
        }else if (Symbol.equals("BLK")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.blk));
        }else if (Symbol.equals("BLOCK")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.block));
        }else if (Symbol.equals("BLZ")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.blz));
        }else if (Symbol.equals("BNB")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.bnb));
        }else if (Symbol.equals("BNT")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.bnt));
        }else if (Symbol.equals("BNTY")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.bnty));
        }else if (Symbol.equals("BOS")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.bos));
        }else if (Symbol.equals("BPT")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.bpt));
        }else if (Symbol.equals("BQ")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.bq));
        }else if (Symbol.equals("BRD")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.brd));
        }else if (Symbol.equals("BTC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.btc));
        }else if (Symbol.equals("BTCD")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.btcd));
        }else if (Symbol.equals("BTCP")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.btcp));
        }else if (Symbol.equals("BTCZ")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.btcz));
        }else if (Symbol.equals("BTG")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.btg));
        }else if (Symbol.equals("BTM")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.btm));
        }else if (Symbol.equals("BTS")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.bts));
        }else if (Symbol.equals("BTX")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.btx));
        }else if (Symbol.equals("BURST")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.burst));
        }else if (Symbol.equals("CDN")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.cdn));
        }else if (Symbol.equals("CDT")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.cdt));
        }else if (Symbol.equals("CHAT")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.chat));
        }else if (Symbol.equals("CHIPS")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.chips));
        }else if (Symbol.equals("CIX")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.cix));
        }else if (Symbol.equals("CLAM")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.clam));
        }else if (Symbol.equals("CLOAK")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.cloak));
        }else if (Symbol.equals("CMT")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.cmt));
        }else if (Symbol.equals("CND")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.cnd));
        }else if (Symbol.equals("CNX")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.cnx));
        }else if (Symbol.equals("CNY")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.cny));
        }else if (Symbol.equals("COB")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.cob));
        }else if (Symbol.equals("CRED")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.cred));
        }else if (Symbol.equals("CRPT")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.crpt));
        }else if (Symbol.equals("CS")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.cs));
        }else if (Symbol.equals("CTR")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ctr));
        }else if (Symbol.equals("CVC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.cvc));
        }else if (Symbol.equals("DASH")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.dash));
        }else if (Symbol.equals("DAT")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.dat));
        }else if (Symbol.equals("DATA")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.data));
        }else if (Symbol.equals("DBC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.dbc));
        }else if (Symbol.equals("DCN")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.dcn));
        }else if (Symbol.equals("DCR")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.dcr));
        }else if (Symbol.equals("DENT")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.dent));
        }else if (Symbol.equals("DEW")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.dew));
        }else if (Symbol.equals("DGB")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.dgb));
        }else if (Symbol.equals("DGD")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.dgd));
        }else if (Symbol.equals("DLT")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.dlt));
        }else if (Symbol.equals("DNT")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.dnt));
        }else if (Symbol.equals("DOGE")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.doge));
        }else if (Symbol.equals("DRGN")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.drgn));
        }else if (Symbol.equals("DTA")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.dta));
        }else if (Symbol.equals("DTR")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.dtr));
        }else if (Symbol.equals("EBST")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ebst));
        }else if (Symbol.equals("EDG")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.edg));
        }else if (Symbol.equals("EDO")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.edo));
        }else if (Symbol.equals("EDOGE")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.edoge));
        }else if (Symbol.equals("ELF")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.elf));
        }else if (Symbol.equals("ELIX")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.elix));
        }else if (Symbol.equals("ELLA")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ella));
        }else if (Symbol.equals("EMC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.emc));
        }else if (Symbol.equals("EMC2")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.emc2));
        }else if (Symbol.equals("ENG")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.eng));
        }else if (Symbol.equals("ENJ")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.enj));
        }else if (Symbol.equals("EOS")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.eos));
        }else if (Symbol.equals("EQUA")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.equa));
        }else if (Symbol.equals("ETC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.etc));
        }else if (Symbol.equals("ETH")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.eth));
        }else if (Symbol.equals("ETHOS")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ethos));
        }else if (Symbol.equals("ETN")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.etn));
        }else if (Symbol.equals("ETP")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.etp));
        }else if (Symbol.equals("EUR")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.eur));
        }else if (Symbol.equals("EVX")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.evx));
        }else if (Symbol.equals("EXMO")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.exmo));
        }else if (Symbol.equals("EXP")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.exp));
        }else if (Symbol.equals("FAIR")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.fair));
        }else if (Symbol.equals("FCT")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.fct));
        }else if (Symbol.equals("FIL")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.fil));
        }else if (Symbol.equals("FLDC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.fldc));
        }else if (Symbol.equals("FLO")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.flo));
        }else if (Symbol.equals("FSN")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.fsn));
        }else if (Symbol.equals("FTC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ftc));
        }else if (Symbol.equals("FUEL")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.fuel));
        }else if (Symbol.equals("FUN")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.fun));
        }else if (Symbol.equals("GAME")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.game));
        }else if (Symbol.equals("GAS")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.gas));
        }else if (Symbol.equals("GBP")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.gbp));
        }else if (Symbol.equals("GBX")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.gbx));
        }else if (Symbol.equals("GBYTE")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.gbyte));
        }else if (Symbol.equals("GENERIC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.generic));
        }else if (Symbol.equals("GNO")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.gno));
        }else if (Symbol.equals("GNT")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.gnt));
        }else if (Symbol.equals("GRC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.grc));
        }else if (Symbol.equals("GRS")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.grs));
        }else if (Symbol.equals("GTO")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.gto));
        }else if (Symbol.equals("GUP")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.gup));
        }else if (Symbol.equals("GVT")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.gvt));
        }else if (Symbol.equals("GXS")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.gxs));
        }else if (Symbol.equals("HPB")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.hpb));
        }else if (Symbol.equals("HSR")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.hsr));
        }else if (Symbol.equals("HTML")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.html));
        }else if (Symbol.equals("HUC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.huc));
        }else if (Symbol.equals("HUSH")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.hush));
        }else if (Symbol.equals("ICN")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.icn));
        }else if (Symbol.equals("ICX")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.icx));
        }else if (Symbol.equals("IGNIS")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ignis));
        }else if (Symbol.equals("INK")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ink));
        }else if (Symbol.equals("INS")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ins));
        }else if (Symbol.equals("ION")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ion));
        }else if (Symbol.equals("IOP")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.iop));
        }else if (Symbol.equals("IOST")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.iost));
        }else if (Symbol.equals("ITC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.itc));
        }else if (Symbol.equals("JNT")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.jnt));
        }else if (Symbol.equals("JPY")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.jpy));
        }else if (Symbol.equals("KCS")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.kcs));
        }else if (Symbol.equals("KIN")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.kin));
        }else if (Symbol.equals("KMD")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.kmd));
        }else if (Symbol.equals("KNC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.knc));
        }else if (Symbol.equals("KRB")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.krb));
        }else if (Symbol.equals("LBC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.lbc));
        }else if (Symbol.equals("LEND")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.lend));
        }else if (Symbol.equals("LINK")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.link));
        }else if (Symbol.equals("LKK")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.lkk));
        }else if (Symbol.equals("LRC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.lrc));
        }else if (Symbol.equals("LSK")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.lsk));
        }else if (Symbol.equals("LTC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ltc));
        }else if (Symbol.equals("LUN")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.lun));
        }else if (Symbol.equals("MAID")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.maid));
        }else if (Symbol.equals("MANA")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.mana));
        }else if (Symbol.equals("MCAP")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.mcap));
        }else if (Symbol.equals("MCO")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.mco));
        }else if (Symbol.equals("MDA")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.mda));
        }else if (Symbol.equals("MDS")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.mds));
        }else if (Symbol.equals("MED")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.med));
        }else if (Symbol.equals("MIOTA")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.miota));
        }else if (Symbol.equals("MKR")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.mkr));
        }else if (Symbol.equals("MLN")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.mln));
        }else if (Symbol.equals("MNX")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.mnx));
        }else if (Symbol.equals("MOD")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.mod));
        }else if (Symbol.equals("MONA")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.mona));
        }else if (Symbol.equals("MTH")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.mth));
        }else if (Symbol.equals("MTL")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.mtl));
        }else if (Symbol.equals("MUSIC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.music));
        }else if (Symbol.equals("MZC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.mzc));
        }else if (Symbol.equals("NANO")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.nano));
        }else if (Symbol.equals("NAS")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.nas));
        }else if (Symbol.equals("NAV")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.nav));
        }else if (Symbol.equals("NCASH")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ncash));
        }else if (Symbol.equals("NDZ")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ndz));
        }else if (Symbol.equals("NEBL")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.nebl));
        }else if (Symbol.equals("NEO")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.neo));
        }else if (Symbol.equals("NEOS")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.neos));
        }else if (Symbol.equals("NGC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ngc));
        }else if (Symbol.equals("NLC2")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.nlc2));
        }else if (Symbol.equals("NLG")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.nlg));
        }else if (Symbol.equals("NMC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.nmc));
        }else if (Symbol.equals("NULS")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.nuls));
        }else if (Symbol.equals("NXS")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.nxs));
        }else if (Symbol.equals("NXT")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.nxt));
        }else if (Symbol.equals("OAX")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.oax));
        }else if (Symbol.equals("OMG")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.omg));
        }else if (Symbol.equals("OMNI")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.omni));
        }else if (Symbol.equals("ONT")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ont));
        }else if (Symbol.equals("OST")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ost));
        }else if (Symbol.equals("OX")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ox));
        }else if (Symbol.equals("PART")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.part));
        }else if (Symbol.equals("PASL")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.pasl));
        }else if (Symbol.equals("PAY")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.pay));
        }else if (Symbol.equals("PINK")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.pink));
        }else if (Symbol.equals("PIRL")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.pirl));
        }else if (Symbol.equals("PIVX")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.pivx));
        }else if (Symbol.equals("PLR")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.plr));
        }else if (Symbol.equals("POA")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.poa));
        }else if (Symbol.equals("POE")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.poe));
        }else if (Symbol.equals("POLY")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.poly));
        }else if (Symbol.equals("POT")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.pot));
        }else if (Symbol.equals("POWR")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.powr));
        }else if (Symbol.equals("PPC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ppc));
        }else if (Symbol.equals("PPP")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ppp));
        }else if (Symbol.equals("PPT")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ppt));
        }else if (Symbol.equals("PRL")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.prl));
        }else if (Symbol.equals("PURA")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.pura));
        }else if (Symbol.equals("QASH")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.qash));
        }else if (Symbol.equals("QIWI")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.qiwi));
        }else if (Symbol.equals("QLC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.qlc));
        }else if (Symbol.equals("QRL")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.qrl));
        }else if (Symbol.equals("QSP")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.qsp));
        }else if (Symbol.equals("QTUM")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.qtum));
        }else if (Symbol.equals("R")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.r));
        }else if (Symbol.equals("RADS")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.rads));
        }else if (Symbol.equals("RCN")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.rcn));
        }else if (Symbol.equals("RDD")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.rdd));
        }else if (Symbol.equals("RDN")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.rdn));
        }else if (Symbol.equals("REP")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.rep));
        }else if (Symbol.equals("REQ")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.req));
        }else if (Symbol.equals("RHOC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.rhoc));
        }else if (Symbol.equals("RIC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ric));
        }else if (Symbol.equals("RISE")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.rise));
        }else if (Symbol.equals("RLC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.rlc));
        }else if (Symbol.equals("RPX")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.rpx));
        }else if (Symbol.equals("RUB")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.rub));
        }else if (Symbol.equals("RVN")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.rvn));
        }else if (Symbol.equals("SALT")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.salt));
        }else if (Symbol.equals("SAN")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.san));
        }else if (Symbol.equals("SBD")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.sbd));
        }else if (Symbol.equals("SBERANK")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.sberbank));
        }else if (Symbol.equals("SC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.sc));
        }else if (Symbol.equals("SKY")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.sky));
        }else if (Symbol.equals("SLR")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.slr));
        }else if (Symbol.equals("SLS")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.sls));
        }else if (Symbol.equals("SMART")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.smart));
        }else if (Symbol.equals("SNGLS")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.sngls));
        }else if (Symbol.equals("SNM")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.snm));
        }else if (Symbol.equals("SNT")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.snt));
        }else if (Symbol.equals("SPANK")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.spank));
        }else if (Symbol.equals("SPHTX")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.sphtx));
        }else if (Symbol.equals("SRN")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.srn));
        }else if (Symbol.equals("START")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.start));
        }else if (Symbol.equals("STEEM")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.steem));
        }else if (Symbol.equals("STORJ")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.storj));
        }else if (Symbol.equals("STORM")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.storm));
        }else if (Symbol.equals("STRAT")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.strat));
        }else if (Symbol.equals("SUB")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.sub));
        }else if (Symbol.equals("SYS")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.sys));
        }else if (Symbol.equals("TAAS")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.taas));
        }else if (Symbol.equals("TAU")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.tau));
        }else if (Symbol.equals("TEL")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.tel));
        }else if (Symbol.equals("THETA")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.theta));
        }else if (Symbol.equals("TIX")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.tix));
        }else if (Symbol.equals("TKN")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.tkn));
        }else if (Symbol.equals("TNB")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.tnb));
        }else if (Symbol.equals("TNC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.tnc));
        }else if (Symbol.equals("TNT")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.tnt));
        }else if (Symbol.equals("TRIG")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.trig));
        }else if (Symbol.equals("TRX")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.trx));
        }else if (Symbol.equals("TZC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.tzc));
        }else if (Symbol.equals("UBQ")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ubq));
        }else if (Symbol.equals("UNITY")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.unity));
        }else if (Symbol.equals("USD")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.usd));
        }else if (Symbol.equals("USDT")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.usdt));
        }else if (Symbol.equals("UTK")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.utk));
        }else if (Symbol.equals("VEN")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ven));
        }else if (Symbol.equals("VERI")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.veri));
        }else if (Symbol.equals("VIA")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.via));
        }else if (Symbol.equals("VIB")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.vib));
        }else if (Symbol.equals("VIBE")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.vibe));
        }else if (Symbol.equals("VIVO")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.vivo));
        }else if (Symbol.equals("VRC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.vrc));
        }else if (Symbol.equals("VTC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.vtc));
        }else if (Symbol.equals("WABI")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.wabi));
        }else if (Symbol.equals("WAN")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.wan));
        }else if (Symbol.equals("WAVES")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.waves));
        }else if (Symbol.equals("WAX")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.wax));
        }else if (Symbol.equals("WGR")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.wgr));
        }else if (Symbol.equals("WINGS")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.wings));
        }else if (Symbol.equals("WPR")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.wpr));
        }else if (Symbol.equals("WTC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.wtc));
        }else if (Symbol.equals("XAS")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.xas));
        }else if (Symbol.equals("XBC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.xbc));
        }else if (Symbol.equals("XBY")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.xby));
        }else if (Symbol.equals("XCP")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.xcp));
        }else if (Symbol.equals("XDN")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.xdn));
        }else if (Symbol.equals("XEM")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.xem));
        }else if (Symbol.equals("XLM")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.xlm));
        }else if (Symbol.equals("XMG")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.xmg));
        }else if (Symbol.equals("XMR")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.xmr));
        }else if (Symbol.equals("XMY")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.xmy));
        }else if (Symbol.equals("XP")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.xp));
        }else if (Symbol.equals("XPA")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.xpa));
        }else if (Symbol.equals("XPM")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.xpm));
        }else if (Symbol.equals("XRP")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.xrp));
        }else if (Symbol.equals("XTZ")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.xtz));
        }else if (Symbol.equals("XUC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.xuc));
        }else if (Symbol.equals("XVC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.xvc));
        }else if (Symbol.equals("XVG")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.xvg));
        }else if (Symbol.equals("XZC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.xzc));
        }else if (Symbol.equals("YOYOW")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.yoyow));
        }else if (Symbol.equals("ZCL")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.zcl));
        }else if (Symbol.equals("ZEC")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.zec));
        }else if (Symbol.equals("ZEN")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.zen));
        }else if (Symbol.equals("ZIL")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.zil));
        }else if (Symbol.equals("ZRX")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.zrx));
        }else{
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_round));

        }
    }
}
