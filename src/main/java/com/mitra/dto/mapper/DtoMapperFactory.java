package com.mitra.dto.mapper;

import com.mitra.dto.*;
import com.mitra.entity.*;

public class DtoMapperFactory {

    private static final DtoMapperFactory INSTANCE = new DtoMapperFactory();

    private DtoMapper<UserDto, User> userDtoMapper;
    private DtoMapper<ProfileDto, Profile> profileDtoMapper;
    private DtoMapper<LocationDto, Location> locationDtoMapper;
    private DtoMapper<InstrumentDto, Instrument> instrumentDtoMapper;
    private DtoMapper<SpecialityDto, Speciality> specialityDtoMapper;

    private DtoMapperFactory() {
        userDtoMapper = new UserDtoMapper();
        locationDtoMapper = new LocationDtoMapper();
        instrumentDtoMapper = new InstrumentDtoMapper();
        specialityDtoMapper = new SpecialityDtoMapper();
        profileDtoMapper = new ProfileDtoMapper(locationDtoMapper, instrumentDtoMapper, specialityDtoMapper);
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
}
