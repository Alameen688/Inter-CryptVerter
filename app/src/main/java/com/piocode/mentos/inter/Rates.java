package com.piocode.mentos.inter;

import java.util.ArrayList;

/**
 * Created by Al-avatar on 09/08/2017.
 */
public class Rates extends ArrayList {
    private String mCRN;
    private String mBTC;
    private String mETH;
    private Double mBTCDouble;
    private Double mETHDouble;


    public Rates(String CRN, String BTC, String ETH, Double BTCDouble, Double ETHDouble) {
        mCRN = CRN;
        mBTC = BTC;
        mETH = ETH;
        mBTCDouble = BTCDouble;
        mETHDouble = ETHDouble;
    }

    public String getMCRN() {
        return mCRN;
    }

    public String getMBTC() {
        return mBTC;
    }

    public String getMETH() {
        return mETH;
    }

    public Double getMBTCDouble() {
        return mBTCDouble;
    }

    public Double getMETHDouble() {
        return mETHDouble;
    }
}