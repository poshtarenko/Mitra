package com.mitra.controller.request_processor;

import com.mitra.controller.UrlPath;
import com.mitra.controller.request_processor.impl.*;
import com.mitra.exception.PageDontExistException;

import java.util.HashMap;
import java.util.Map;

public class RequestProcessorFactory {

    private static final Map<UrlPath, RequestProcessor> requestProcessorsMap = new HashMap<>();

    static {
        requestProcessorsMap.put(UrlPath.LANDING_PAGE, new LandingProcessor());
        requestProcessorsMap.put(UrlPath.AUTHORIZATION, new AuthorizationProcessor());
        requestProcessorsMap.put(UrlPath.REGISTRATION, new RegistrationProcessor());
        requestProcessorsMap.put(UrlPath.CREATE_PROFILE, new CreateProfileProcessor());
        requestProcessorsMap.put(UrlPath.SEARCH, new SearchProcessor());
    }

    public RequestProcessor getProcessor(UrlPath urlPath) {
        RequestProcessor requestProcessor = requestProcessorsMap.get(urlPath);

        if (requestProcessor == null)
            throw new PageDontExistException("Page " + urlPath.get() + " does not exist");

        return requestProcessor;
    }

}
