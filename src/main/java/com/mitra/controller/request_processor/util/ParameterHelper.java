package com.mitra.controller.request_processor.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class ParameterHelper {

    public static boolean parameterNotEmpty(String parameterValue) {
        return parameterValue != null && !parameterValue.equals("");
    }

    public static boolean redirectIfParameterIsEmpty(HttpServletResponse response, String parameterValue, String url) throws IOException {
        if (parameterNotEmpty(parameterValue)) {
            return false;
        }
        response.sendRedirect(url);
        return true;
    }

}
