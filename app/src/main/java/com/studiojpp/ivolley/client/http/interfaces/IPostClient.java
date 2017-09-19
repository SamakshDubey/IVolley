package com.studiojpp.ivolley.client.http.interfaces;

import com.studiojpp.ivolley.body.BaseRequestBody;
import com.studiojpp.ivolley.response.BaseResponse;

public interface IPostClient<DATA_OUT extends BaseResponse, DATA_IN extends BaseRequestBody>
        extends IHttpClient<DATA_OUT> {

    void send(DATA_IN data);

}
