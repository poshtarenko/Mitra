package com.mitra.service;

import com.mitra.dto.ProfileDto;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface ProfileService {

    /**
     * Get all user profiles from DB
     *
     * @return list of profileDTOs
     */
    List<ProfileDto> findAll();

    /**
     * Get all user IDs from DB
     *
     * @return list of IDs
     */
    List<Integer> getAllIDs();

    /**
     * Find profile by ID
     *
     * @param id id of entity
     * @return profile in DB, if ID is relevant
     */
    Optional<ProfileDto> find(int id);

    /**
     * Update profile in DB with values from Profile Dto
     * Update profile photo, if newPhoto param is not null
     *
     * @param userId     id of entity
     * @param profileDto profileDTO
     * @param newPhoto   InputStream with photo
     */
    void updateProfile(int userId, ProfileDto profileDto, InputStream newPhoto);

}
