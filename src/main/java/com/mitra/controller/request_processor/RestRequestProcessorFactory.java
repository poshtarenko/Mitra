package com.mitra.controller.request_processor;

import com.mitra.controller.RestUrl;
import com.mitra.controller.request_processor.rest.ChatProcessor;
import com.mitra.exception.PageDontExistException;
import com.mitra.service.ServiceFactory;

import java.util.HashMap;
import java.util.Map;

public class RestRequestProcessorFactory {

    private static final Map<RestUrl, RequestProcessor> requestProcessorsMap = new HashMap<>();

    static {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();

        requestProcessorsMap.put(RestUrl.CHAT, new ChatProcessor(
                serviceFactory.getChatService(), serviceFactory.getMessageService()));
    }

    public RequestProcessor getProcessor(RestUrl urlPath) {
        RequestProcessor requestProcessor = requestProcessorsMap.get(urlPath);

        if (requestProcessor == null)
            throw new PageDontExistException("Page " + urlPath.getUrl() + " does not exist");

        return requestProcessor;
    }

}
