package com.studiojpp.ivolley.client.http.impl;

import com.android.volley.Request;
import com.studiojpp.ivolley.body.BaseRequestBody;
import com.studiojpp.ivolley.client.http.interfaces.IPostWithParamsClient;
import com.studiojpp.ivolley.client.network.base.NetworkClient;
import com.studiojpp.ivolley.params.exception.ParameterMissingException;
import com.studiojpp.ivolley.params.exception.ParameterReadException;
import com.studiojpp.ivolley.params.get.ParamsBasedObject;
import com.studiojpp.ivolley.response.BaseResponse;

import java.util.Map;

public abstract class BasePostWithParamsClient<DATA_OUT extends BaseResponse,
        DATA_IN extends BaseRequestBody, PARAMS extends ParamsBasedObject> extends BaseClient<DATA_OUT>
        implements IPostWithParamsClient<DATA_OUT, DATA_IN, PARAMS> {

    private PARAMS  params;
    private DATA_IN data;

    public BasePostWithParamsClient(NetworkClient client) {
        super(client);
    }

    @Override
    public int getMethod() {
        return Request.Method.POST;
    }

    @Override
    public void send(PARAMS params, DATA_IN data) {
        this.params = params;
        this.data = data;
        client.call(this);
    }

    @Override
    public byte[] getData() {
        return data.getBody();
    }

    @Override
    public Map<String, String> getParams() throws ParameterMissingException, ParameterReadException {
        return params.getParameters();
    }
}
