package com.mitra.dto.mapper;

import com.mitra.dto.ProfileDto;
import com.mitra.dto.TrackDto;
import com.mitra.entity.Profile;
import com.mitra.entity.Track;
import com.mitra.entity.dummy.DummyProfile;
import com.mitra.entity.impl.TrackImpl;

public class TrackDtoMapper implements DtoMapper<TrackDto, Track> {

    private final DtoMapper<ProfileDto, Profile> profileDtoMapper;

    public TrackDtoMapper(DtoMapper<ProfileDto, Profile> profileDtoMapper) {
        this.profileDtoMapper = profileDtoMapper;
    }

    @Override
    public Track mapToEntity(TrackDto dto) {
        return new TrackImpl(
                dto.getId(),
                dto.getName(),
                dto.getAuthor(),
                dto.getFilePath(),
                new DummyProfile(dto.getOwnerId())
        );
    }

    @Override
    public TrackDto mapToDto(Track entity) {
        return TrackDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .author(entity.getAuthor())
                .filePath(entity.getFilePath())
                .ownerId(entity.getOwner().getId())
                .build();
    }
}
