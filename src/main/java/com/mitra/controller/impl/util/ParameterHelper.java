package com.mitra.controller.impl.util;

import com.mitra.exception.EmptyParameterException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;

@Slf4j
public final class ParameterHelper {

    public static boolean parameterNotEmpty(String parameterValue) {
        return parameterValue != null && !parameterValue.equals("");
    }

    public static String getNecessaryParameter(HttpServletRequest request, String parameterName) throws IOException {
        String parameter = request.getParameter(parameterName);
        if (parameterNotEmpty(parameter)) {
            return parameter;
        }
        String errorMsg = String.format("Necessary parameter '%s' is empty. Url : %s",
                parameterName, request.getRequestURI());
        log.warn(errorMsg);
        throw new EmptyParameterException(errorMsg);
    }

    public static int getNecessaryIntParameter(HttpServletRequest request, String parameterName) throws IOException {
        return Integer.parseInt(getNecessaryParameter(request, parameterName));
    }

    public static Part getNecessaryPart(HttpServletRequest request, String partName) throws IOException, ServletException {
        Part part = request.getPart(partName);
        if (part != null && part.getSize() > 0) {
            return part;
        }
        String errorMsg = String.format("Necessary part '%s' is empty. Url : %s",
                part, request.getRequestURI());
        log.warn(errorMsg);
        throw new EmptyParameterException(errorMsg);
    }

}
