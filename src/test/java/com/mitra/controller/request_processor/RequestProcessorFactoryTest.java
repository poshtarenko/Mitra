package com.mitra.controller.request_processor;

import com.mitra.controller.UrlPath;
import com.mitra.controller.request_processor.impl.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestProcessorFactoryTest {

    RequestProcessorFactory requestProcessorFactory = new RequestProcessorFactory();

    @Test
    void getProcessor() {
        RequestProcessor requestProcessor;

        requestProcessor = requestProcessorFactory.getProcessor(UrlPath.LANDING_PAGE);
        assertInstanceOf(LandingProcessor.class, requestProcessor);

        requestProcessor = requestProcessorFactory.getProcessor(UrlPath.AUTHORIZATION);
        assertInstanceOf(AuthorizationProcessor.class, requestProcessor);

        requestProcessor = requestProcessorFactory.getProcessor(UrlPath.REGISTRATION);
        assertInstanceOf(RegistrationProcessor.class, requestProcessor);

        requestProcessor = requestProcessorFactory.getProcessor(UrlPath.PROFILE);
        assertInstanceOf(ProfileProcessor.class, requestProcessor);

        requestProcessor = requestProcessorFactory.getProcessor(UrlPath.MY_PROFILE);
        assertInstanceOf(MyProfileProcessor.class, requestProcessor);

        requestProcessor = requestProcessorFactory.getProcessor(UrlPath.CREATE_PROFILE);
        assertInstanceOf(CreateProfileProcessor.class, requestProcessor);

        requestProcessor = requestProcessorFactory.getProcessor(UrlPath.UPDATE_PROFILE);
        assertInstanceOf(UpdateProfileProcessor.class, requestProcessor);

        requestProcessor = requestProcessorFactory.getProcessor(UrlPath.SEARCH);
        assertInstanceOf(SearchProcessor.class, requestProcessor);

        requestProcessor = requestProcessorFactory.getProcessor(UrlPath.SWIPE_SEARCH);
        assertInstanceOf(SearchBySwipeProcessor.class, requestProcessor);

        requestProcessor = requestProcessorFactory.getProcessor(UrlPath.LIKES);
        assertInstanceOf(LikesProcessor.class, requestProcessor);
    }
}