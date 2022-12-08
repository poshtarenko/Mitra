package com.mitra.controller.request_processor;

import com.mitra.cloud.CloudStorageProviderImpl;
import com.mitra.cloud.GoogleDriveInitializer;
import com.mitra.controller.UrlPath;
import com.mitra.controller.request_processor.impl.*;
import com.mitra.exception.PageDontExistException;
import com.mitra.service.ServiceFactory;

import java.util.HashMap;
import java.util.Map;

public class RequestProcessorFactory {

    private static final Map<UrlPath, RequestProcessor> requestProcessorsMap = new HashMap<>();

    static {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        CloudStorageProviderImpl cloudStorageProvider =
                new CloudStorageProviderImpl(GoogleDriveInitializer.getDriveService());

        requestProcessorsMap.put(UrlPath.LANDING_PAGE,
                new LandingProcessor());
        requestProcessorsMap.put(UrlPath.AUTHORIZATION,
                new AuthorizationProcessor(serviceFactory.getUserService()));
        requestProcessorsMap.put(UrlPath.REGISTRATION,
                new RegistrationProcessor(serviceFactory.getUserService()));
        requestProcessorsMap.put(UrlPath.LOGOUT,
                new LogoutProcessor());
        requestProcessorsMap.put(UrlPath.CREATE_PROFILE,
                new CreateProfileProcessor(serviceFactory.getLocationService(), serviceFactory.getProfileService()));
        requestProcessorsMap.put(UrlPath.UPDATE_PROFILE,
                new UpdateProfileProcessor(serviceFactory.getProfileService(), serviceFactory.getLocationService(),
                        serviceFactory.getInstrumentService(), serviceFactory.getSpecialityService()));
        requestProcessorsMap.put(UrlPath.MY_PROFILE,
                new MyProfileProcessor(serviceFactory.getProfileService()));
        requestProcessorsMap.put(UrlPath.PROFILE,
                new ProfileProcessor(serviceFactory.getProfileService(), serviceFactory.getProfileLikeService(),
                        serviceFactory.getChatService()));
        requestProcessorsMap.put(UrlPath.CHAT,
                new ChatProcessor(serviceFactory.getChatService(), serviceFactory.getMessageService()));
        requestProcessorsMap.put(UrlPath.CHATS,
                new ChatsProcessor(serviceFactory.getChatService(), serviceFactory.getProfileLikeService()));
        requestProcessorsMap.put(UrlPath.SEARCH,
                new SearchProcessor(serviceFactory.getLocationService(), serviceFactory.getInstrumentService(),
                        serviceFactory.getSpecialityService(), serviceFactory.getProfileService()));
        requestProcessorsMap.put(UrlPath.SWIPE_SEARCH,
                new SearchBySwipeProcessor(serviceFactory.getProfileService(), serviceFactory.getProfileLikeService()));
        requestProcessorsMap.put(UrlPath.IMAGES,
                new ImageProcessor(cloudStorageProvider));
        requestProcessorsMap.put(UrlPath.LIKES,
                new LikesProcessor(serviceFactory.getProfileLikeService()));
        requestProcessorsMap.put(UrlPath.MUSIC,
                new MusicProcessor(serviceFactory.getTrackService()));
        requestProcessorsMap.put(UrlPath.AUDIO,
                new AudioProcessor(cloudStorageProvider));
    }

    public RequestProcessor getProcessor(UrlPath urlPath) {
        RequestProcessor requestProcessor = requestProcessorsMap.get(urlPath);

        if (requestProcessor == null)
            throw new PageDontExistException("Page " + urlPath.getUrl() + " does not exist");

        return requestProcessor;
    }

}
