package com.studiojpp.ivolley.client.network.impl;

import android.content.Context;

import com.studiojpp.ivolley.client.http.interfaces.IHttpClient;
import com.android.volley.DefaultRetryPolicy;
import com.studiojpp.ivolley.IVolley;
import com.studiojpp.ivolley.client.network.base.NetworkClient;
import com.studiojpp.ivolley.listeners.DefaultVolleyListener;
import com.studiojpp.ivolley.volley.JSONRequest;

public class JSONRequestClient implements NetworkClient {

    private final Context context;

    public JSONRequestClient(Context context){
        this.context = context;
    }

    @Override
    public void call (final IHttpClient client) {
        JSONRequest request =  new JSONRequest(client, new DefaultVolleyListener(client));
        request.setRetryPolicy(new DefaultRetryPolicy(30000,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        IVolley.getInstance(context).addToRequestQueue(request);
    }
}
