package com.mitra.dto.mapper;

import com.mitra.dto.*;
import com.mitra.entity.Profile;
import com.mitra.entity.impl.*;

public class DtoMapperFactory {

    private static final DtoMapperFactory INSTANCE = new DtoMapperFactory();

    private DtoMapper<UserDto, UserImpl> userDtoMapper;
    private DtoMapper<ProfileDto, Profile> profileDtoMapper;
    private DtoMapper<LocationDto, LocationImpl> locationDtoMapper;
    private DtoMapper<InstrumentDto, Instrument> instrumentDtoMapper;
    private DtoMapper<SpecialityDto, SpecialityImpl> specialityDtoMapper;
    private DtoMapper<LikeDto, Like> likeDtoMapper;

    private DtoMapperFactory() {
        locationDtoMapper = new LocationDtoMapper();
        instrumentDtoMapper = new InstrumentDtoMapper();
        specialityDtoMapper = new SpecialityDtoMapper();
        profileDtoMapper = new ProfileDtoMapper(locationDtoMapper, instrumentDtoMapper, specialityDtoMapper);
        likeDtoMapper = new LikeDtoMapper(profileDtoMapper);
        userDtoMapper = new UserDtoMapper(profileDtoMapper);
    }

    public static DtoMapperFactory getInstance() {
        return INSTANCE;
    }

    public DtoMapper<UserDto, UserImpl> getUserDtoMapper() {
        return userDtoMapper;
    }

    public DtoMapper<ProfileDto, Profile> getProfileDtoMapper() {
        return profileDtoMapper;
    }

    public DtoMapper<LocationDto, LocationImpl> getLocationDtoMapper() {
        return locationDtoMapper;
    }

    public DtoMapper<InstrumentDto, Instrument> getInstrumentDtoMapper() {
        return instrumentDtoMapper;
    }

    public DtoMapper<SpecialityDto, SpecialityImpl> getSpecialityDtoMapper() {
        return specialityDtoMapper;
    }

    public DtoMapper<LikeDto, Like> getLikeDtoMapper() {
        return likeDtoMapper;
    }
}
