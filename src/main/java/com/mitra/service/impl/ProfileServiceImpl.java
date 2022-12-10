package com.mitra.service.impl;

import com.mitra.cloud.CloudStorageProvider;
import com.mitra.db.connection.ConnectionManager;
import com.mitra.db.dao.ProfileDao;
import com.mitra.db.filter.ProfileFilter;
import com.mitra.dto.ProfileDto;
import com.mitra.dto.mapper.DtoMapper;
import com.mitra.entity.Profile;
import com.mitra.exception.DaoException;
import com.mitra.exception.ValidationException;
import com.mitra.service.ProfileService;
import com.mitra.validator.Error;
import com.mitra.validator.ProfileValidator;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class ProfileServiceImpl implements ProfileService {

    private final ProfileDao profileDao;
    private final DtoMapper<ProfileDto, Profile> profileDtoMapper;
    private final CloudStorageProvider cloudStorageProvider;
    private final ProfileValidator profileValidator;

    public ProfileServiceImpl(ProfileDao profileDao, DtoMapper<ProfileDto, Profile> profileDtoMapper,
                              CloudStorageProvider cloudStorageProvider, ProfileValidator profileValidator) {
        this.profileDao = profileDao;
        this.profileDtoMapper = profileDtoMapper;
        this.cloudStorageProvider = cloudStorageProvider;
        this.profileValidator = profileValidator;
    }

    @Override
    public int createProfile(ProfileDto profileDto) throws ValidationException {
        try (Connection connection = ConnectionManager.get()) {
            checkProfileOrThrowException(profileDto);
            Profile profile = profileDtoMapper.mapToEntity(profileDto);
            return profileDao.save(connection, profile);
        } catch (SQLException e) {
            log.error("Profile update failed");
            return 0;
        }
    }

    @Override
    public List<ProfileDto> getAll(ProfileFilter profileFilter, int limit, int offset) {
        try (Connection connection = ConnectionManager.get()) {
            return profileDao.findAll(connection, profileFilter, limit, offset).stream()
                    .map(profileDtoMapper::mapToDto)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            log.error("Getting all profiles failed");
            return Collections.emptyList();
        }
    }

    @Override
    public int getCount(ProfileFilter profileFilter) throws DaoException {
        try (Connection connection = ConnectionManager.get()) {
            return profileDao.getCount(connection, profileFilter);
        } catch (SQLException e) {
            log.error("Getting count of profile matching filter failed");
            return 0;
        }
    }

    @Override
    public Optional<ProfileDto> find(int id) {
        try (Connection connection = ConnectionManager.get()) {
            Optional<Profile> profile = profileDao.find(connection, id);
            return profile.map(profileDtoMapper::mapToDto);
        } catch (SQLException e) {
            log.error("Find profile failed");
            return Optional.empty();
        }
    }

    @Override
    public List<Integer> getAllIDs() {
        try (Connection connection = ConnectionManager.get()) {
            return profileDao.getAllIds(connection);
        } catch (SQLException e) {
            log.error("Getting all profile ids failed");
            return Collections.emptyList();
        }
    }

    @Override
    public void updateProfile(int userId, ProfileDto profileDto, InputStream newPhoto) throws ValidationException {
        try (Connection connection = ConnectionManager.get()) {
            checkProfileOrThrowException(profileDto);

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
            log.error("Profile update failed");
        }
    }

    private void checkProfileOrThrowException(ProfileDto profile) throws ValidationException {
        List<Error> errors = new ArrayList<>();
        profileValidator.checkName(profile.getName()).ifPresent(errors::add);
        profileValidator.checkAge(profile.getAge()).ifPresent(errors::add);
        profileValidator.checkText(profile.getText()).ifPresent(errors::add);

        if (!errors.isEmpty())
            throw new ValidationException(errors);
    }
}
