package com.piocode.mentos.inter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Al-avatar on 09/08/2017.
 */
public class RatesAdapter extends ArrayAdapter<Rates> {
    Activity context;
    public RatesAdapter(Activity context, ArrayList<Rates> rates) {
        super(context, 0, rates);
        this.context = context;
    }

    private class ViewHolder{
        TextView crnView;
        TextView btcView;
        TextView ethView;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Rates currentRates = getItem(position);
        View itemView = convertView;
        if(itemView == null){
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_rates,parent,false);
            holder = new ViewHolder();
            holder.crnView = (TextView) itemView.findViewById(R.id.txt_currency);
            holder.btcView = (TextView) itemView.findViewById(R.id.txt_btc_rates);
            holder.ethView = (TextView) itemView.findViewById(R.id.txt_eth_rates);
            itemView.setTag(holder);
        }else {
            holder = (ViewHolder) itemView.getTag();
        }

        holder.crnView.setText(currentRates.getMCRN());
        holder.btcView.setText(currentRates.getMBTC().toString());
        holder.ethView.setText(currentRates.getMETH().toString());

        return itemView;
    }


}
