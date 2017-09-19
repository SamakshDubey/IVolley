package com.studiojpp.ivolley.client.http.interfaces;

import com.studiojpp.ivolley.body.BaseRequestBody;
import com.studiojpp.ivolley.params.get.ParamsBasedObject;
import com.studiojpp.ivolley.response.BaseResponse;

public interface IPostWithParamsClient<DATA_OUT extends BaseResponse, DATA_IN extends BaseRequestBody,
        PARAMS extends ParamsBasedObject> extends IHttpClient<DATA_OUT> {

    void send(PARAMS params, DATA_IN data);

}