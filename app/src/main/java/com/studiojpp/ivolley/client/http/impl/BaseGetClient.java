package com.studiojpp.ivolley.client.http.impl;

import android.content.Context;

import com.android.volley.Request;
import com.studiojpp.ivolley.annotations.Path;
import com.studiojpp.ivolley.client.http.interfaces.IGetClient;
import com.studiojpp.ivolley.client.network.base.NetworkClient;
import com.studiojpp.ivolley.params.exception.ParameterMissingException;
import com.studiojpp.ivolley.params.exception.ParameterReadException;
import com.studiojpp.ivolley.params.get.ParamsBasedObject;
import com.studiojpp.ivolley.response.BaseResponse;

import java.util.Map;

public abstract class BaseGetClient<DATA_OUT extends BaseResponse, PARAMS extends ParamsBasedObject>
        extends BaseClient<DATA_OUT> implements IGetClient<DATA_OUT, PARAMS> {

    protected Context context;
    private PARAMS params;
    private String path;

    public BaseGetClient(NetworkClient client, Context context) {
        super(client);
        this.context = context;
    }

    @Override
    public String getPath() {
        if (path == null) {
            for (Class clazz : this.getClass().getInterfaces()) {
                if (clazz.isAnnotationPresent(Path.class)) {
                    path = ((Path) clazz.getAnnotation(Path.class)).path();
                }
            }
            if (path != null && params != null) {
                Map<String, String> paramsMap = null;
                try {
                    paramsMap = this.params.getParameters();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                StringBuilder builder = new StringBuilder(path);
                if (paramsMap != null) {
                    builder.append("?");
                    for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
                        builder.append(entry.getKey() + "=" + getSafeUrlValue(entry.getValue()) + "&");
                    }
                    builder.deleteCharAt(builder.length() - 1);
                }
                this.path = builder.toString();

            }
        }
        return path;
    }

    @Override
    public Map<String, String> getParams() throws ParameterMissingException, ParameterReadException {
        return params.getParameters();
    }

    public int getMethod() {
        return Request.Method.GET;
    }

    @Override
    public void send(PARAMS params) {
        this.params = params;
        onSendInProgress();
        client.call(this);
    }

    @Override
    public void success(DATA_OUT data) {
        onSuccess(data);
    }

    @Override
    public void failure(byte[] bytes) {
        if (bytes != null) {
            onFailure(bytes);
        }
    }

    protected abstract void onSendInProgress();

    protected abstract void onSuccess(DATA_OUT data);

    protected abstract void onFailure(byte[] bytes);
}
