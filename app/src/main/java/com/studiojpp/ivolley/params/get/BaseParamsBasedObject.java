package com.studiojpp.ivolley.params.get;

import com.studiojpp.ivolley.annotations.Param;
import com.studiojpp.ivolley.params.exception.ParameterMissingException;
import com.studiojpp.ivolley.params.exception.ParameterReadException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public abstract class BaseParamsBasedObject implements ParamsBasedObject {

    @Override
    public Map<String, String> getParameters() throws ParameterMissingException, ParameterReadException {
        Map<String, String> parameters = new LinkedHashMap<>();

        try {
            List<Field> fields = new ArrayList<>();
            extractAllFields(super.getClass(), fields);
            for (Field field : fields) {
                Annotation[] annotations = field.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation instanceof Param) {
                        Param param = (Param) annotation;
                        Object data = getValueOfField(field, this);
                        if (data != null) {
                            String value = String.valueOf(data);
                            if (!value.isEmpty()) {
                                parameters.put(param.param(), value);
                            } else if (param.isRequired()) {
                                throw new ParameterMissingException(param.param());
                            }
                        } else if (param.isRequired()) {
                            throw new ParameterMissingException(param.param());
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new ParameterReadException(e);
        }

        return parameters;
    }

    private void extractAllFields(Class clazz, List<Field> fields) {
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        if (!clazz.getSuperclass().equals(Object.class)) {
            extractAllFields(clazz.getSuperclass(), fields);
        }
    }

    private Object getValueOfField(Field field, BaseParamsBasedObject thiz) throws IllegalAccessException {
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        Object data = field.get(thiz);
        field.setAccessible(accessible);
        return data;
    }
}
