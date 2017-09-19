package com.studiojpp.ivolley.client.http.interfaces;

import com.studiojpp.ivolley.params.exception.ParameterMissingException;
import com.studiojpp.ivolley.params.exception.ParameterReadException;
import com.studiojpp.ivolley.response.BaseResponse;

import java.util.Map;

public interface IHttpClient<DATA_OUT extends BaseResponse> {

    void success(DATA_OUT data);

    void failure(byte[] bytes);

    String getApiHost();

    String getPath();

    Map<String, String> getHeaders();

    Map<String, String> getParams() throws ParameterMissingException, ParameterReadException;

    byte[] getData();

    int getMethod();

    Class<DATA_OUT> getClazz();
}
