package com.mitra.service;

import com.mitra.cloud.CloudStorageProviderImpl;
import com.mitra.cloud.GoogleDriveInitializer;
import com.mitra.db.dao.DaoFactory;
import com.mitra.dto.mapper.DtoMapperFactory;
import com.mitra.security.EncryptorSHA512;
import com.mitra.service.impl.*;
import com.mitra.validator.ValidatorFactory;

public class ServiceFactory {

    private static final ServiceFactory INSTANCE = new ServiceFactory();

    private final UserService userService;
    private final ProfileService profileService;
    private final TrackService trackService;
    private final LikeService likeService;
    private final LocationService locationService;
    private final InstrumentService instrumentService;
    private final SpecialityService specialityService;
    private final ChatService chatService;
    private final MessageService messageService;

    private ServiceFactory() {
        DaoFactory daoFactory = DaoFactory.getInstance();
        DtoMapperFactory dtoMapperFactory = DtoMapperFactory.getInstance();
        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        CloudStorageProviderImpl cloudStorageProvider = new CloudStorageProviderImpl(GoogleDriveInitializer.getDriveService());

        chatService = new ChatServiceImpl(daoFactory.getChatDao(), daoFactory.getLikeDao(), dtoMapperFactory.getChatDtoMapper());
        messageService = new MessageServiceImpl(daoFactory.getMessageDao(), daoFactory.getChatDao(),
                dtoMapperFactory.getMessageDtoMapper(), validatorFactory.getMessageValidator());
        locationService = new LocationServiceImpl(daoFactory.getLocationDao(), dtoMapperFactory.getLocationDtoMapper());
        instrumentService = new InstrumentServiceImpl(daoFactory.getInstrumentDao(), dtoMapperFactory.getInstrumentDtoMapper());
        specialityService = new SpecialityServiceImpl(daoFactory.getSpecialityDao(), dtoMapperFactory.getSpecialityDtoMapper());
        likeService = new LikeServiceImpl(daoFactory.getLikeDao(), dtoMapperFactory.getLikeDtoMapper());
        userService = new UserServiceImpl(daoFactory.getUserDao(), dtoMapperFactory.getUserDtoMapper(),
                validatorFactory.getCredentialsValidator(), EncryptorSHA512.getInstance());
        trackService = new TrackServiceImpl(dtoMapperFactory.getTrackDtoMapper(), daoFactory.getProfileDao(),
                daoFactory.getTrackDao(), cloudStorageProvider);
        profileService = new ProfileServiceImpl(daoFactory.getProfileDao(), daoFactory.getTrackDao(), dtoMapperFactory.getProfileDtoMapper(),
                cloudStorageProvider, validatorFactory.getProfileValidator());
    }

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    public UserService getUserService() {
        return userService;
    }

    public ProfileService getProfileService() {
        return profileService;
    }

    public LocationService getLocationService() {
        return locationService;
    }

    public InstrumentService getInstrumentService() {
        return instrumentService;
    }

    public SpecialityService getSpecialityService() {
        return specialityService;
    }

    public LikeService getLikeService() {
        return likeService;
    }

    public TrackService getTrackService() {
        return trackService;
    }

    public ChatService getChatService() {
        return chatService;
    }

    public MessageService getMessageService() {
        return messageService;
    }
}
