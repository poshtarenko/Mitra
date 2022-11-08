package com.mitra.dto.mapper;

import com.mitra.dto.InstrumentDto;
import com.mitra.dto.LocationDto;
import com.mitra.dto.ProfileDto;
import com.mitra.dto.UserDto;
import com.mitra.entity.Instrument;
import com.mitra.entity.Location;
import com.mitra.entity.Profile;
import com.mitra.entity.User;

public class DtoMapperFactory {

    private static final DtoMapperFactory INSTANCE = new DtoMapperFactory();

    private DtoMapper<UserDto, User> userDtoMapper;
    private DtoMapper<ProfileDto, Profile> profileDtoMapper;
    private DtoMapper<LocationDto, Location> locationDtoMapper;
    private final DtoMapper<InstrumentDto, Instrument> instrumentDtoMapper;

    private DtoMapperFactory() {
        userDtoMapper = new UserDtoMapper();
        locationDtoMapper = new LocationDtoMapper();
        instrumentDtoMapper = new InstrumentDtoMapper();
        profileDtoMapper = new ProfileDtoMapper(locationDtoMapper, instrumentDtoMapper);
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
}
