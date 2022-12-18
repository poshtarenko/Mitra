package com.mitra.controller.request_processor;

import com.mitra.cloud.CloudStorageProviderImpl;
import com.mitra.cloud.GoogleDriveInitializer;
import com.mitra.controller.AppUrl;
import com.mitra.controller.request_processor.impl.*;
import com.mitra.exception.PageDontExistException;
import com.mitra.service.ServiceFactory;

import java.util.HashMap;
import java.util.Map;

public class RequestProcessorFactory {

    private static final Map<AppUrl, RequestProcessor> requestProcessorsMap = new HashMap<>();

    static {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        CloudStorageProviderImpl cloudStorageProvider =
                new CloudStorageProviderImpl(GoogleDriveInitializer.getDriveService());

        requestProcessorsMap.put(AppUrl.LANDING_PAGE,
                new LandingProcessor());
        requestProcessorsMap.put(AppUrl.AUTHORIZATION,
                new AuthorizationProcessor(serviceFactory.getUserService()));
        requestProcessorsMap.put(AppUrl.REGISTRATION,
                new RegistrationProcessor(serviceFactory.getUserService()));
        requestProcessorsMap.put(AppUrl.LOGOUT,
                new LogoutProcessor());
        requestProcessorsMap.put(AppUrl.CREATE_PROFILE,
                new CreateProfileProcessor(serviceFactory.getLocationService(), serviceFactory.getProfileService()));
        requestProcessorsMap.put(AppUrl.UPDATE_PROFILE,
                new UpdateProfileProcessor(serviceFactory.getProfileService(), serviceFactory.getLocationService(),
                        serviceFactory.getInstrumentService(), serviceFactory.getSpecialityService()));
        requestProcessorsMap.put(AppUrl.MY_PROFILE,
                new MyProfileProcessor(serviceFactory.getProfileService(), serviceFactory.getTrackService()));
        requestProcessorsMap.put(AppUrl.PROFILE,
                new ProfileProcessor(serviceFactory.getProfileService(), serviceFactory.getProfileLikeService(),
                        serviceFactory.getTrackService(), serviceFactory.getChatService()));
        requestProcessorsMap.put(AppUrl.CHAT,
                new ChatProcessor(serviceFactory.getChatService(), serviceFactory.getMessageService()));
        requestProcessorsMap.put(AppUrl.CHATS,
                new ChatsProcessor(serviceFactory.getChatService(), serviceFactory.getProfileLikeService()));
        requestProcessorsMap.put(AppUrl.SEARCH,
                new SearchProcessor(serviceFactory.getLocationService(), serviceFactory.getInstrumentService(),
                        serviceFactory.getSpecialityService(), serviceFactory.getProfileService()));
        requestProcessorsMap.put(AppUrl.SWIPE_SEARCH,
                new SearchBySwipeProcessor(serviceFactory.getProfileService(), serviceFactory.getProfileLikeService()));
        requestProcessorsMap.put(AppUrl.MY_ACCOUNT,
                new MyAccountProcessor(serviceFactory.getUserService()));
        requestProcessorsMap.put(AppUrl.IMAGES,
                new ImageProcessor(cloudStorageProvider));
        requestProcessorsMap.put(AppUrl.LIKES,
                new LikesProcessor(serviceFactory.getProfileLikeService()));
        requestProcessorsMap.put(AppUrl.MUSIC,
                new MusicProcessor(serviceFactory.getTrackService()));
        requestProcessorsMap.put(AppUrl.AUDIO,
                new AudioProcessor(cloudStorageProvider));
    }

    public RequestProcessor getProcessor(AppUrl urlPath) {
        RequestProcessor requestProcessor = requestProcessorsMap.get(urlPath);

        if (requestProcessor == null)
            throw new PageDontExistException("Page " + urlPath.getUrl() + " does not exist");

        return requestProcessor;
    }

}
