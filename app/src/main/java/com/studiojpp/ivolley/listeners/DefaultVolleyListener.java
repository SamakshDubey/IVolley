package com.studiojpp.ivolley.listeners;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.studiojpp.ivolley.client.http.interfaces.IHttpClient;
import com.studiojpp.ivolley.response.BaseResponse;

public class DefaultVolleyListener<T extends BaseResponse>
        implements Response.Listener<T>, Response.ErrorListener  {

    IHttpClient<T> client;

    public DefaultVolleyListener(IHttpClient<T> client) {
        this.client = client;
    }

    @Override
    public void onResponse (T response) {
        client.success(response);
    }

    @Override
    public void onErrorResponse (VolleyError error) {

        if(error != null && error.networkResponse != null) {
            client.failure(error.networkResponse.data);
        } else  {
            client.failure(null);
        }
    }
}
