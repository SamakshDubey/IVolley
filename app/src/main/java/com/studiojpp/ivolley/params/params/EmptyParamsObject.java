package com.studiojpp.ivolley.params.params;

import com.studiojpp.ivolley.params.exception.ParameterMissingException;
import com.studiojpp.ivolley.params.exception.ParameterReadException;
import com.studiojpp.ivolley.params.get.BaseParamsBasedObject;

import java.util.Collections;
import java.util.Map;

public class EmptyParamsObject extends BaseParamsBasedObject {

    public static final EmptyParamsObject EMPTY = new EmptyParamsObject();

    private EmptyParamsObject() {
        super();
    }

    @Override
    public Map<String, String> getParameters() throws ParameterMissingException, ParameterReadException {
        return Collections.emptyMap();
    }
}
