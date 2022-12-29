package com.mitra.controller.impl;

import com.mitra.cloud.CloudStorageProviderImpl;
import com.mitra.cloud.GoogleDriveInitializer;
import com.mitra.controller.GetUrl;
import com.mitra.controller.PostUrl;
import com.mitra.controller.impl.get.*;
import com.mitra.controller.impl.get.CreateProfileController;
import com.mitra.controller.impl.get.UpdateProfileProcessor;
import com.mitra.controller.impl.post.*;
import com.mitra.dto.mapper.DtoMapperFactory;
import com.mitra.service.ServiceFactory;

import java.util.HashMap;
import java.util.Map;

public class ControllerFactory {

    private static final Map<String, GetController> getControllers = new HashMap<>();
    private static final Map<String, PostController> postControllers = new HashMap<>();

    private static final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private static final CloudStorageProviderImpl cloudStorageProvider =
            new CloudStorageProviderImpl(GoogleDriveInitializer.getDriveService());
    private static final DtoMapperFactory dtoMapperFactory = DtoMapperFactory.getInstance();


    static {
        initGetControllers();
        initPostControllers();
    }

    private static void initGetControllers() {
        getControllers.put(GetUrl.MY_ACCOUNT.getUrl(),
                new AccountController(serviceFactory.getUserService()));
        getControllers.put(GetUrl.AUTHORIZATION.getUrl(),
                new AuthorizationController());
        getControllers.put(GetUrl.CHAT.getUrl(),
                new ChatController(serviceFactory.getChatService()));
        getControllers.put(GetUrl.CHATS.getUrl(),
                new ChatsController(serviceFactory.getChatService()));
        getControllers.put(GetUrl.CREATE_PROFILE.getUrl(),
                new CreateProfileController(serviceFactory.getLocationService()));
        getControllers.put(GetUrl.FAST_SEARCH.getUrl(),
                new FastSearchController(serviceFactory.getProfileService()));
        getControllers.put(GetUrl.LANDING_PAGE.getUrl(),
                new LandingController());
        getControllers.put(GetUrl.UPDATE_PROFILE.getUrl(),
                new UpdateProfileProcessor(serviceFactory.getProfileService(),
                serviceFactory.getLocationService(), serviceFactory.getInstrumentService(),
                serviceFactory.getSpecialityService()));
        getControllers.put(GetUrl.SEARCH.getUrl(),
                new SearchController(serviceFactory.getLocationService(),
                serviceFactory.getInstrumentService(), serviceFactory.getSpecialityService(),
                serviceFactory.getProfileService(), dtoMapperFactory.getInstrumentDtoMapper(),
                dtoMapperFactory.getSpecialityDtoMapper()));
        getControllers.put(GetUrl.REGISTRATION.getUrl(),
                new RegistrationController(serviceFactory.getUserService()));
        getControllers.put(GetUrl.PROFILE.getUrl(),
                new ProfileController(serviceFactory.getProfileService(),
                serviceFactory.getLikeService(), serviceFactory.getTrackService(), serviceFactory.getChatService()));
        getControllers.put(GetUrl.MY_PROFILE.getUrl(),
                new MyProfileController(serviceFactory.getProfileService(),
                serviceFactory.getTrackService(), serviceFactory.getLikeService()));
        getControllers.put(GetUrl.MUSIC.getUrl(),
                new MusicController(serviceFactory.getTrackService()));
        getControllers.put(GetUrl.LIKES.getUrl(),
                new LikesController(serviceFactory.getLikeService()));
        getControllers.put(GetUrl.AUDIO.getUrl(),
                new AudioController(cloudStorageProvider));
        getControllers.put(GetUrl.IMAGES.getUrl(),
                new ImageController(cloudStorageProvider));
    }

    private static void initPostControllers() {
        postControllers.put(PostUrl.ADD_TRACK.getUrl(),
                new AddTrackController(serviceFactory.getTrackService()));
        postControllers.put(PostUrl.AUTH.getUrl(),
                new AuthorizeUserController(serviceFactory.getUserService()));
        postControllers.put(PostUrl.CHANGE_EMAIL.getUrl(),
                new ChangeEmailController(serviceFactory.getUserService()));
        postControllers.put(PostUrl.CHANGE_PASSWORD.getUrl(),
                new ChangePasswordController(serviceFactory.getUserService()));
        postControllers.put(PostUrl.CREATE_PROFILE.getUrl(),
                new com.mitra.controller.impl.post.CreateProfileController(serviceFactory.getProfileService()));
        postControllers.put(PostUrl.DELETE_TRACK.getUrl(),
                new DeleteTrackController(serviceFactory.getTrackService()));
        postControllers.put(PostUrl.LOGOUT.getUrl(),
                new LogoutController());
        postControllers.put(PostUrl.UPDATE_PROFILE.getUrl(),
                new com.mitra.controller.impl.post.UpdateProfileProcessor(serviceFactory.getProfileService(),
                        serviceFactory.getLocationService(), serviceFactory.getInstrumentService(),
                        serviceFactory.getSpecialityService()));
        postControllers.put(PostUrl.UPDATE_PROFILE_PHOTO.getUrl(),
                new UpdateProfilePhotoController(serviceFactory.getProfileService()));
        postControllers.put(PostUrl.SEND_MESSAGE.getUrl(),
                new SendMessageController(serviceFactory.getChatService(), serviceFactory.getMessageService()));
        postControllers.put(PostUrl.REGISTER.getUrl(),
                new RegisterNewUserController(serviceFactory.getUserService()));
        postControllers.put(PostUrl.PUT_LIKE.getUrl(),
                new PutLikeController(serviceFactory.getLikeService()));
        postControllers.put(PostUrl.RESPONSE_ON_LIKE.getUrl(),
                new ResponseOnLikeController(serviceFactory.getLikeService()));
        postControllers.put(PostUrl.OPEN_CHAT.getUrl(),
                new OpenChatController(serviceFactory.getChatService(), serviceFactory.getLikeService()));
    }

    public GetController findGetController(String url) {
        return getControllers.get(url);
    }

    public PostController findPostController(String url) {
        return postControllers.get(url);
    }

}
