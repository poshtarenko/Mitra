package com.mitra.service.impl;

import com.mitra.db.connection.ConnectionManager;
import com.mitra.db.dao.ProfileDao;
import com.mitra.dto.ProfileDto;
import com.mitra.dto.mapper.DtoMapper;
import com.mitra.entity.Profile;
import com.mitra.service.ProfileService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProfileServiceImpl implements ProfileService {

    private final ProfileDao profileDao;
    private final DtoMapper<ProfileDto, Profile> profileDtoMapper;

    public ProfileServiceImpl(ProfileDao profileDao, DtoMapper<ProfileDto, Profile> profileDtoMapper) {
        this.profileDao = profileDao;
        this.profileDtoMapper = profileDtoMapper;
    }

    @Override
    public List<ProfileDto> findAll() {
        try (Connection connection = ConnectionManager.get()) {
            return profileDao.findAll(connection).stream()
                    .map(profileDtoMapper::mapToDto)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            // TODO : log
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<ProfileDto> getByUserId(int userId) {
        try (Connection connection = ConnectionManager.get()) {
            Optional<Profile> profile = profileDao.find(connection, userId);
            return profile.map(profileDtoMapper::mapToDto);
        } catch (SQLException e) {
            // TODO : log
            return Optional.empty();
        }
    }

    @Override
    public boolean updateProfile(int userId, ProfileDto profileDto) {
        try (Connection connection = ConnectionManager.get()) {
            Profile profile = profileDtoMapper.mapToEntity(profileDto);
            profileDao.update(connection, userId, profile);
            return true;
        } catch (SQLException e) {
            // TODO : log
            return false;
        }
    }
}
