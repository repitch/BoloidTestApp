package com.repitch.boloidtestapp.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by repitch on 02.09.15.
 */
public class Price implements Serializable {
    private static final String PRICE_NAME = "price";
    private static final String DESCRIPTION_NAME = "description";

    private int mPrice;
    private String mDescription;

    // From JSON
    public Price(JSONObject jsonPrice) throws JSONException {
        mPrice = jsonPrice.getInt(PRICE_NAME);
        mDescription = jsonPrice.getString(DESCRIPTION_NAME);
    }
}
