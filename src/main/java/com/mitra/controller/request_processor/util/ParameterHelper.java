package com.mitra.controller.request_processor.util;

public class ParameterHelper {

    public static boolean parameterNotEmpty(String parameterValue) {
        return parameterValue != null && !parameterValue.equals("");
    }

}
