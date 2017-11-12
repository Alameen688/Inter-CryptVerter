package com.piocode.mentos.inter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

public class ConverterActivity extends AppCompatActivity {

    TextView tvCrnName;
    ImageView coinImage;
    EditText etAmount;
    TextView etPrice;
    Button btnConvert;

    String crnName;
    Double btcValue;
    Double ethValue;
    Double coinValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        coinImage = (ImageView) findViewById(R.id.img_coin);
        etAmount = (EditText) findViewById(R.id.txt_amount);
        etPrice = (TextView) findViewById(R.id.txt_price);
        tvCrnName = (TextView) findViewById(R.id.txt_crn_name);

        crnName = getIntent().getExtras().getString("crnName");
        btcValue = getIntent().getExtras().getDouble("btcVal");
        ethValue = getIntent().getExtras().getDouble("ethVal");

        String fullCrnName = getCrnFullName(crnName);
        tvCrnName.setText(fullCrnName+" Conversion");


        List<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("BTC");
        spinnerArray.add("ETH");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner sCoins = (Spinner) findViewById(R.id.sp_coins);
        sCoins.setAdapter(adapter);
        sCoins.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = sCoins.getSelectedItem().toString().trim();
                if (selected.equals("ETH")){
                    coinValue = btcValue;
                    coinImage.setImageResource(R.drawable.etherium);
                }else {
                    coinValue = ethValue;
                    coinImage.setImageResource(R.drawable.bitcoin);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                coinImage.setImageResource(R.drawable.bitcoin);
            }
        });



        btnConvert = (Button) findViewById(R.id.btn_convert);
        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double mAmount;
                String check = etAmount.getText().toString().trim();
                if ( check != null && !check.isEmpty()){
                    mAmount = Double.parseDouble(check);
                    doConversion(mAmount);
                }else{
                    Toast.makeText(getApplicationContext(), "You have to enter a number", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public Double doConversion(Double amount){
        Double coin = coinValue;
        Double price = null;

        price = amount/coin;
        String formatPrice = String.format("%1$,.2f", price);
        etPrice.setText(formatPrice.toString());

        return price;
    }

    public String getCrnFullName(String crnName) {
        switch (crnName) {

            case "AFN": return "Afghanistan Afghani";
            case "DZD": return "Algerian Dinar";
            case "NGN": return "Nigerian Naira";
            case "UGX": return "Ugandan Shilling";
            case "ZAR": return "Rand Conversion Screen";
            case "XAF": return "CFA Franc BCEAO";
            case "NZD": return "New Zealand Dollar";
            case "MYR": return "Malaysian Ringgit";
            case "BND": return "Brunei Dollar";
            case "GEL": return "Georgian Lari";
            case "EGP": return "Egyptian Pound";
            case "RUB": return "Russian Ruble";
            case "INR": return "Indian Rupee";
            case "USD": return "US Dollar";
            case "EUR": return "Euro";
            case "JPY": return "Chinese Yen";
            case "KES": return "Kenyan Shilling";
            case "GHS": return "Ghanaian Cede";
            case "GBP": return "Pound Sterling";
            case "AUD": return "Australian Dollar";
            case "XOF": return "West African CFA";
            case "CAD": return "Canadian Dollar";
            case "CHF": return "Swiss Franc";
            case "CNY": return "Yuan";
            default: return "Currency Converter";

        }
    }
}
