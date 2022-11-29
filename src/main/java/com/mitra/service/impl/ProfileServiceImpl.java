package com.mitra.service.impl;

import com.mitra.cloud.CloudStorageProvider;
import com.mitra.db.connection.ConnectionManager;
import com.mitra.db.dao.ProfileDao;
import com.mitra.db.filter.ProfileFilter;
import com.mitra.dto.ProfileDto;
import com.mitra.dto.mapper.DtoMapper;
import com.mitra.entity.Profile;
import com.mitra.exception.DaoException;
import com.mitra.service.ProfileService;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProfileServiceImpl implements ProfileService {

    private final ProfileDao profileDao;
    private final DtoMapper<ProfileDto, Profile> profileDtoMapper;
    private final CloudStorageProvider cloudStorageProvider;

    public ProfileServiceImpl(ProfileDao profileDao, DtoMapper<ProfileDto, Profile> profileDtoMapper, CloudStorageProvider cloudStorageProvider) {
        this.profileDao = profileDao;
        this.profileDtoMapper = profileDtoMapper;
        this.cloudStorageProvider = cloudStorageProvider;
    }

    @Override
    public List<ProfileDto> findAll(ProfileFilter profileFilter, int limit, int offset) {
        try (Connection connection = ConnectionManager.get()) {
            return profileDao.findAll(connection, profileFilter, limit, offset).stream()
                    .map(profileDtoMapper::mapToDto)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            // TODO : log
            return Collections.emptyList();
        }
    }

    @Override
    public int getCount(ProfileFilter profileFilter) throws DaoException {
        try (Connection connection = ConnectionManager.get()) {
            return profileDao.getCount(connection, profileFilter);
        } catch (SQLException e) {
            // TODO : log
            return 0;
        }
    }

    @Override
    public Optional<ProfileDto> find(int id) {
        try (Connection connection = ConnectionManager.get()) {
            Optional<Profile> profile = profileDao.find(connection, id);
            return profile.map(profileDtoMapper::mapToDto);
        } catch (SQLException e) {
            // TODO : log
            return Optional.empty();
        }
    }

    @Override
    public List<Integer> getAllIDs() {
        try (Connection connection = ConnectionManager.get()) {
            return profileDao.getAllIds(connection);
        } catch (SQLException e) {
            // TODO : log
            return Collections.emptyList();
        }
    }

    @Override
    public void updateProfile(int userId, ProfileDto profileDto, InputStream newPhoto) {
        try (Connection connection = ConnectionManager.get()) {
            Profile profile = profileDtoMapper.mapToEntity(profileDto);

            // update profile photoPath in DB, upload new photo on cloud
            String photoPath = profile.getPhotoPath();
            try {
                if (newPhoto != null && newPhoto.available() > 0) {
                    if (photoPath != null && !photoPath.equals(""))
                        cloudStorageProvider.deleteFile(photoPath);
                    String fileID = cloudStorageProvider.setProfilePhoto(profile.getId(), newPhoto);
                    profile.setPhotoPath(fileID);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            profileDao.update(connection, userId, profile);
        } catch (SQLException e) {
            // TODO : log
        }
    }
}
