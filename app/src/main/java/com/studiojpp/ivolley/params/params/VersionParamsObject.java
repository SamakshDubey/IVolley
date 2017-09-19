package com.studiojpp.ivolley.params.params;

import com.studiojpp.ivolley.params.get.BaseParamsBasedObject;
import com.studiojpp.ivolley.annotations.Param;

public class VersionParamsObject extends BaseParamsBasedObject {

    public static final String VERSION = "version";

    @Param(param = VERSION, isRequired = true)
    private int version;

    public VersionParamsObject(int version) {
        this.version = version;
    }

    public int getVersion() {
        return version;
    }
}
