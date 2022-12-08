package com.mitra.controller.request_processor.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class ParameterHelper {

    public static boolean parameterNotEmpty(String parameterValue) {
        return parameterValue != null && !parameterValue.equals("");
    }

    public static void redirectIfParameterIsEmpty(HttpServletResponse response, String parameterValue, String url) throws IOException {
        if (!parameterNotEmpty(parameterValue)) {
            response.sendRedirect(url);
        }
    }

}
