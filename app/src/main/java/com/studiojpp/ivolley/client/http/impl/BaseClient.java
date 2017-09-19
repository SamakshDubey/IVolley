package com.studiojpp.ivolley.client.http.impl;

import com.studiojpp.ivolley.annotations.ApiHost;
import com.studiojpp.ivolley.annotations.Header;
import com.studiojpp.ivolley.annotations.Headers;
import com.studiojpp.ivolley.annotations.Path;
import com.studiojpp.ivolley.client.http.interfaces.IHttpClient;
import com.studiojpp.ivolley.client.network.base.NetworkClient;
import com.studiojpp.ivolley.params.exception.ParameterMissingException;
import com.studiojpp.ivolley.params.exception.ParameterReadException;
import com.studiojpp.ivolley.response.BaseResponse;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BaseClient<T extends BaseResponse> implements IHttpClient<T> {

    protected final NetworkClient client;

    private String path;

    public BaseClient(NetworkClient client) {
        this.client = client;
    }

    @Override
    public String getApiHost() {
        String apiHost = "";
        for (Class clazz : this.getClass().getInterfaces()) {
            if (clazz.isAnnotationPresent(ApiHost.class)) {
                apiHost = ((ApiHost) clazz.getAnnotation(ApiHost.class)).apiHost();
            } else {
                // TODO throw apiHost missing exception
            }
        }
        return apiHost;
    }

    @Override
    public String getPath() {
        if (path == null) {
            for (Class clazz : this.getClass().getInterfaces()) {
                if (clazz.isAnnotationPresent(Path.class)) {
                    path = ((Path) clazz.getAnnotation(Path.class)).path();
                }
            }
        }
        return path;
    }

    @Override
    public Map<String, String> getHeaders() {
        Map<String, String> results = new LinkedHashMap<>();
        for (Class clazz : this.getClass().getInterfaces()) {
            if (clazz.isAnnotationPresent(Headers.class)) {
                Header[] headers = ((Headers) clazz.getAnnotation(Headers.class)).value();
                for (Header header : headers) {
                    results.put(header.key(), header.header());
                }
            }
        }
        return results;
    }

    @Override
    public Map<String, String> getParams() throws ParameterMissingException, ParameterReadException {
        return null;
    }

    @Override
    public byte[] getData() {
        return null;
    }

    protected String getSafeUrlValue(String value) {
        // Only issue is in case if the URL parameter value that I have got is half encoded which would be very weird
        // if happens
        try {
            value = URLDecoder.decode(value, "UTF-8");
        } catch (Exception e) {
            // Do nothing as we are not able to decode it
        }
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (Exception e) {
            return value;
        }
    }



    protected abstract void onSendInProgress();

    protected abstract void onSuccess(T data);

    protected abstract void onFailure(byte[] bytes);

    // TODO implement getHeader here
}
