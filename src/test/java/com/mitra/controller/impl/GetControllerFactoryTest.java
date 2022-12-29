/*
package com.mitra.controller.impl;

import com.mitra.controller.GetUrl;
import com.mitra.controller.impl.get.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetControllerFactoryTest {

    ControllerFactory controllerFactory = new ControllerFactory();

    @Test
    void getProcessor() {
        GetController getController;

        getController = controllerFactory.getProcessor(GetUrl.LANDING_PAGE);
        assertInstanceOf(LandingController.class, getController);

        getController = controllerFactory.getProcessor(GetUrl.AUTHORIZATION);
        assertInstanceOf(AuthorizationController.class, getController);

        getController = controllerFactory.getProcessor(GetUrl.REGISTRATION);
        assertInstanceOf(RegistrationController.class, getController);

        getController = controllerFactory.getProcessor(GetUrl.PROFILE);
        assertInstanceOf(ProfileController.class, getController);

        getController = controllerFactory.getProcessor(GetUrl.MY_PROFILE);
        assertInstanceOf(MyProfileController.class, getController);

        getController = controllerFactory.getProcessor(GetUrl.CREATE_PROFILE);
        assertInstanceOf(CreateProfileController.class, getController);

        getController = controllerFactory.getProcessor(GetUrl.UPDATE_PROFILE);
        assertInstanceOf(UpdateProfileProcessor.class, getController);

        getController = controllerFactory.getProcessor(GetUrl.SEARCH);
        assertInstanceOf(SearchController.class, getController);

        getController = controllerFactory.getProcessor(GetUrl.FAST_SEARCH);
        assertInstanceOf(FastSearchController.class, getController);

        getController = controllerFactory.getProcessor(GetUrl.LIKES);
        assertInstanceOf(LikesController.class, getController);
    }
}*/
