package com.mitra.controller.request_processor;

import com.mitra.controller.AppUrl;
import com.mitra.controller.request_processor.impl.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestProcessorFactoryTest {

    RequestProcessorFactory requestProcessorFactory = new RequestProcessorFactory();

    @Test
    void getProcessor() {
        RequestProcessor requestProcessor;

        requestProcessor = requestProcessorFactory.getProcessor(AppUrl.LANDING_PAGE);
        assertInstanceOf(LandingProcessor.class, requestProcessor);

        requestProcessor = requestProcessorFactory.getProcessor(AppUrl.AUTHORIZATION);
        assertInstanceOf(AuthorizationProcessor.class, requestProcessor);

        requestProcessor = requestProcessorFactory.getProcessor(AppUrl.REGISTRATION);
        assertInstanceOf(RegistrationProcessor.class, requestProcessor);

        requestProcessor = requestProcessorFactory.getProcessor(AppUrl.PROFILE);
        assertInstanceOf(ProfileProcessor.class, requestProcessor);

        requestProcessor = requestProcessorFactory.getProcessor(AppUrl.MY_PROFILE);
        assertInstanceOf(MyProfileProcessor.class, requestProcessor);

        requestProcessor = requestProcessorFactory.getProcessor(AppUrl.CREATE_PROFILE);
        assertInstanceOf(CreateProfileProcessor.class, requestProcessor);

        requestProcessor = requestProcessorFactory.getProcessor(AppUrl.UPDATE_PROFILE);
        assertInstanceOf(UpdateProfileProcessor.class, requestProcessor);

        requestProcessor = requestProcessorFactory.getProcessor(AppUrl.SEARCH);
        assertInstanceOf(SearchProcessor.class, requestProcessor);

        requestProcessor = requestProcessorFactory.getProcessor(AppUrl.SWIPE_SEARCH);
        assertInstanceOf(SearchBySwipeProcessor.class, requestProcessor);

        requestProcessor = requestProcessorFactory.getProcessor(AppUrl.LIKES);
        assertInstanceOf(LikesProcessor.class, requestProcessor);
    }
}