package com.studiojpp.ivolley.params.get;

import com.studiojpp.ivolley.params.exception.ParameterMissingException;
import com.studiojpp.ivolley.params.exception.ParameterReadException;

import java.util.Map;

public interface ParamsBasedObject {

    /**
     * Get the mapping between the parameters and the values of the fields
     *
     * @return map between the parameter and the value of that parameter
     */
    Map<String, String> getParameters() throws ParameterMissingException, ParameterReadException;

}
