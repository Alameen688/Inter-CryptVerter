package com.piocode.mentos.inter;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity{
    private static final String URL_DATA ="https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH&tsyms=" +
            "AFN,DZD,NGN,USD,EUR,INR,GBP,EGP,JPY,GBP,AUD,CAD,CHF,XOF,CNY,KES,GHS,UGX,ZAR,XAF,NZD,MYR,RUB,BND,GEL";
    LinearLayout mainView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<Rates> rates;
    RatesAdapter ratesAdapter;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ListView) findViewById(R.id.list);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Rates rates = ratesAdapter.getItem(position);
                String mCRN = rates.getMCRN();
                Double mBTCVal = rates.getMBTCDouble();
                Double mETHVal = rates.getMETHDouble();

                Intent intent = new Intent(MainActivity.this,ConverterActivity.class);
                intent.putExtra("crnName", mCRN);
                intent.putExtra("btcVal", mBTCVal);
                intent.putExtra("ethVal", mETHVal);
                startActivity(intent);
            }
        });


        mainView = (LinearLayout) findViewById(R.id.activity_main);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.layout_swipe_refresh);

        rates = new ArrayList<>();

        loadListViewData();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rates.clear();
                loadListViewData();
            }
        });
        ratesAdapter = new RatesAdapter(this, rates);
        listview.setAdapter(ratesAdapter);
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }


    public void loadListViewData(){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_DATA, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject btc_rates = response.getJSONObject("BTC".trim());
                    JSONObject eth_rates = response.getJSONObject("ETH".trim());

                    Iterator<?> keysBTC = btc_rates.keys();
                    Iterator<?> keysETH = eth_rates.keys();

                    while (keysBTC.hasNext() && keysETH.hasNext()) {
                        String keyBTC = (String) keysBTC.next();
                        String keyETH = (String) keysETH.next();


                        Double btc_v = btc_rates.getDouble(keyBTC);
                        Double eth_v = eth_rates.getDouble(keyETH);

                        String btc_format = String.format("%1$,.2f", btc_v);
                        String eth_format = String.format("%1$,.2f", eth_v);

                        Log.d("price data",  "BTC "+btc_rates.getDouble(keyBTC)+", ETH "+eth_rates.getDouble(keyETH));
                        ratesAdapter.add(new Rates(keyBTC, keyBTC+" "+btc_format, keyETH+" "+eth_format, btc_v, eth_v));
                    }

                    mSwipeRefreshLayout.setRefreshing(false);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(mainView, "Refresh Failed! Swipe to refresh again", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }});

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

}
