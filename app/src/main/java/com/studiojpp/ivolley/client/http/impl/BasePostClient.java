package com.studiojpp.ivolley.client.http.impl;

import android.content.Context;

import com.android.volley.Request;
import com.studiojpp.ivolley.body.BaseRequestBody;
import com.studiojpp.ivolley.client.http.interfaces.IPostClient;
import com.studiojpp.ivolley.client.network.base.NetworkClient;
import com.studiojpp.ivolley.response.BaseResponse;

public abstract class BasePostClient<DATA_OUT extends BaseResponse,
        DATA_IN extends BaseRequestBody> extends BaseClient<DATA_OUT> implements IPostClient<DATA_OUT, DATA_IN> {

    DATA_IN data;
    protected Context context;

    public BasePostClient(NetworkClient client, Context context) {
        super(client);
        this.context = context;
    }

    @Override
    public void send (DATA_IN data) {
        onSendInProgress();
        this.data = data;
        client.call(this);
    }

    @Override
    public void success (DATA_OUT data) {
        onSuccess(data);
    }

    @Override
    public void failure(byte[] bytes){
        if(bytes != null){
            onFailure(bytes);
        }
    }

    public void setData(DATA_IN data) {
        this.data = data;
    }

    @Override
    public byte[] getData() {
        return data.getBody();
    }

    public int getMethod() {
        return Request.Method.POST;
    }
}
