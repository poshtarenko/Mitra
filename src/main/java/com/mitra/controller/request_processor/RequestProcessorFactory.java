package com.mitra.controller.request_processor;

import com.mitra.controller.UrlPath;
import com.mitra.controller.request_processor.impl.AuthorizationProcessor;
import com.mitra.controller.request_processor.impl.LandingProcessor;
import com.mitra.controller.request_processor.impl.RegistrationProcessor;
import com.mitra.exception.PageDontExistException;

import java.util.HashMap;
import java.util.Map;

public class RequestProcessorFactory {

    private static final Map<UrlPath, RequestProcessor> requestProcessorsMap = new HashMap<>();

    static {
        requestProcessorsMap.put(UrlPath.LANDING_PAGE, new LandingProcessor());
        requestProcessorsMap.put(UrlPath.AUTHORIZATION, new AuthorizationProcessor());
        requestProcessorsMap.put(UrlPath.REGISTRATION, new RegistrationProcessor());
    }

    public RequestProcessor getProcessor(UrlPath urlPath) {
        RequestProcessor requestProcessor = requestProcessorsMap.get(urlPath);

        if (requestProcessor == null)
            throw new PageDontExistException("Page " + urlPath.get() + " does not exist");

        return requestProcessor;
    }

}
