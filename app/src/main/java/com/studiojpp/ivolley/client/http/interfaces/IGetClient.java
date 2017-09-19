package com.studiojpp.ivolley.client.http.interfaces;

import com.studiojpp.ivolley.params.get.ParamsBasedObject;
import com.studiojpp.ivolley.response.BaseResponse;

public interface IGetClient<DATA_OUT extends BaseResponse, PARAMS extends ParamsBasedObject>
        extends IHttpClient<DATA_OUT> {

    void send(PARAMS params);

}
