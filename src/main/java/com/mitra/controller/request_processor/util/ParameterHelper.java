package com.mitra.controller.request_processor.util;

import com.mitra.exception.EmptyParameterException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public final class ParameterHelper {

    public static boolean parameterNotEmpty(String parameterValue) {
        return parameterValue != null && !parameterValue.equals("");
    }

    public static String getNecessaryParameter(HttpServletRequest req, String parameterName) throws IOException {
        String parameter = req.getParameter(parameterName);
        if (parameterNotEmpty(parameter)) {
            return parameter;
        }
        log.warn("Parameter '{}' is empty, request : {}", parameterName, req);
        throw new EmptyParameterException("Parameter '" + parameter + "' is empty");
    }

}
