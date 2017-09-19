package com.studiojpp.ivolley.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.studiojpp.ivolley.client.http.interfaces.IHttpClient;
import com.studiojpp.ivolley.listeners.DefaultVolleyListener;
import com.studiojpp.ivolley.response.BaseResponse;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class JSONRequest<T extends BaseResponse> extends Request<T> {

    private final IHttpClient<T> client;
    private static final int SOCKET_TIMEOUT = 30000;

    private final DefaultVolleyListener<T> listener;

    public JSONRequest(IHttpClient<T> client, DefaultVolleyListener<T> listener) {
        // TODO put API HOST HERE
        super(client.getMethod(), client.getApiHost() + client.getPath(), listener);
        this.client = client;
        this.listener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = client.getHeaders();
        if (headers == null ) {
            headers = new HashMap<>();
        }
        return headers;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        byte[] data = client.getData();
        if (data != null) {
            return data;
        }

        return super.getBody();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            if (response.headers == null) {
                response = new NetworkResponse(
                        response.statusCode,
                        response.data,
                        Collections.<String, String>emptyMap(), // this is the important line, set an empty but non-null map.
                        response.notModified,
                        response.networkTimeMs);
            }

            String json = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            Gson gson = new Gson();
            T data = gson.fromJson(json, client.getClazz());

            return Response.success(data,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }
    
    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        try {
            return client.getParams();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.getParams();
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

}