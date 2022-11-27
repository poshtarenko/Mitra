package com.mitra.dto.mapper;

import com.mitra.dto.*;
import com.mitra.entity.*;

public class DtoMapperFactory {

    private static final DtoMapperFactory INSTANCE = new DtoMapperFactory();

    private final DtoMapper<UserDto, User> userDtoMapper;
    private final DtoMapper<ProfileDto, Profile> profileDtoMapper;
    private final DtoMapper<TrackDto, Track> trackDtoMapper;
    private final DtoMapper<LocationDto, Location> locationDtoMapper;
    private final DtoMapper<InstrumentDto, Instrument> instrumentDtoMapper;
    private final DtoMapper<SpecialityDto, Speciality> specialityDtoMapper;
    private final DtoMapper<LikeDto, Like> likeDtoMapper;

    private DtoMapperFactory() {
        locationDtoMapper = new LocationDtoMapper();
        instrumentDtoMapper = new InstrumentDtoMapper();
        specialityDtoMapper = new SpecialityDtoMapper();
        profileDtoMapper = new ProfileDtoMapper(locationDtoMapper, instrumentDtoMapper, specialityDtoMapper);
        trackDtoMapper = new TrackDtoMapper(profileDtoMapper);
        likeDtoMapper = new LikeDtoMapper(profileDtoMapper);
        userDtoMapper = new UserDtoMapper(profileDtoMapper);
    }

    public static DtoMapperFactory getInstance() {
        return INSTANCE;
    }

    public DtoMapper<UserDto, User> getUserDtoMapper() {
        return userDtoMapper;
    }

    public DtoMapper<ProfileDto, Profile> getProfileDtoMapper() {
        return profileDtoMapper;
    }

    public DtoMapper<LocationDto, Location> getLocationDtoMapper() {
        return locationDtoMapper;
    }

    public DtoMapper<InstrumentDto, Instrument> getInstrumentDtoMapper() {
        return instrumentDtoMapper;
    }

    public DtoMapper<SpecialityDto, Speciality> getSpecialityDtoMapper() {
        return specialityDtoMapper;
    }

    public DtoMapper<LikeDto, Like> getLikeDtoMapper() {
        return likeDtoMapper;
    }

    public DtoMapper<TrackDto, Track> getTrackDtoMapper() {
        return trackDtoMapper;
    }
}
